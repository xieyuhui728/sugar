    /**
     * 明细查询SQL模板
     */
    private static final String DETAIL_QUERY_TEMPLATE = """
            WITH first_designs AS (
                SELECT
                    gc.code AS case_code,
                    gc.created as case_created_time,
                    gd.order_code,
                    MIN(gd.id) AS first_design_id,
                    MIN(gd.send_out) AS first_design_send_time
                FROM gms_case gc
                LEFT JOIN gms_design gd ON gc.code = gd.case_code
                WHERE gd.send_out IS NOT NULL
                    AND gd.status IN ('SENT','CONFIRMED','MODIFICATION','NOT_MODIFICATION')
                    AND (gd.send_out >= :startTime)
                    AND (gd.send_out <= :endTime)
                GROUP BY gc.code, gc.created, gd.order_code
            ),
            last_completed_tasks AS (
                SELECT 
                    fd.case_code,
                    fd.case_created_time,
                    fd.first_design_send_time,
                    fd.order_code,
                    gt.code AS task_code,
                    gt.assignee_id as designer_id,
                    go2.team_id,
                    go2.tags as order_tags,
                    ROW_NUMBER() OVER (
                        PARTITION BY fd.case_code 
                        ORDER BY go2.finished DESC
                    ) AS rn
                FROM first_designs fd
                JOIN gms_order go2 ON go2.code = fd.order_code
                JOIN gms_task gt ON gt.process_instance_id = go2.active_process_instance_id 
                JOIN gms_task_type gtt ON gt.type_id = gtt.id
                WHERE gt.status = 'COMPLETED'
                    AND gtt.id IN (29, 31, 35)
                    AND gt.finished < fd.first_design_send_time
            ),
                    call_metrics AS (
                SELECT
                    lct.case_code,
                    lct.case_created_time,
                    lct.first_design_send_time,
                    lct.order_code,
                    lct.task_code,
                    lct.designer_id,
                    lct.team_id,
                    lct.order_tags,
                    -- 设计前通话统计
                    COUNT(CASE WHEN tocl.begin_time < lct.first_design_send_time THEN tocl.id END) AS pre_called_num,
                    COUNT(CASE WHEN tocl.begin_time < lct.first_design_send_time AND tocl.status = '接通' THEN tocl.id END) AS pre_connected_call_num,
                    SUM(
                        CASE
                            WHEN tocl.begin_time < lct.first_design_send_time AND tocl.status = '接通' AND tocl.duration IS NOT NULL 
                            THEN 
                                (
                                    COALESCE(SUBSTRING_INDEX(tocl.duration, ':', 1), 0) * 3600 +
                                    COALESCE(SUBSTRING_INDEX(SUBSTRING_INDEX(tocl.duration, ':', 2), ':', -1), 0) * 60 +
                                    COALESCE(SUBSTRING_INDEX(tocl.duration, ':', -1), 0)
                                )
                            ELSE 0
                        END
                    ) AS pre_total_duration_sec,
                    -- 设计后通话统计
                    COUNT(CASE WHEN tocl.begin_time >= lct.first_design_send_time THEN tocl.id END) AS post_called_num,
                    COUNT(CASE WHEN tocl.begin_time >= lct.first_design_send_time AND tocl.status = '接通' THEN tocl.id END) AS post_connected_call_num,
                    SUM(
                        CASE
                            WHEN tocl.begin_time >= lct.first_design_send_time AND tocl.status = '接通' AND tocl.duration IS NOT NULL 
                            THEN
                                (
                                    COALESCE(SUBSTRING_INDEX(tocl.duration, ':', 1), 0) * 3600 +
                                    COALESCE(SUBSTRING_INDEX(SUBSTRING_INDEX(tocl.duration, ':', 2), ':', -1), 0) * 60 +
                                    COALESCE(SUBSTRING_INDEX(tocl.duration, ':', -1), 0)
                                )
                            ELSE 0
                        END
                    ) AS post_total_duration_sec
                FROM last_completed_tasks lct
                LEFT JOIN tt_order_call_log tocl ON lct.task_code = tocl.order_no
                WHERE lct.rn = 1
                GROUP BY lct.case_code, lct.case_created_time, lct.first_design_send_time, lct.order_code, lct.task_code, lct.designer_id, lct.team_id, lct.order_tags
            )
            SELECT
                cm.case_code,
                COALESCE(gt.name, '未分组') AS team_name,
                COALESCE(gu.name, '未知') AS user_name,
                COALESCE(gd.name, '未知') AS dentist_name,
                gocsd.dentist_code,
                COALESCE(gh.name, '未知') AS hospital_name,
                COALESCE(gp.name, '未知') AS patient_name,
                gocsd.patient_code,
                DATE_FORMAT(cm.case_created_time, '%Y-%m-%d %H:%i:%s') AS case_created_time,
                DATE_FORMAT(cm.first_design_send_time, '%Y-%m-%d %H:%i:%s') AS first_design_send_time,
                cm.task_code,
                CASE WHEN cm.order_tags LIKE '%ORDER_TAG-PRE_DESIGN_COMMUNICATION%' THEN '是' ELSE '否' END AS is_pre_communication,
                COALESCE(cm.pre_called_num, 0) AS pre_called_num,
                COALESCE(cm.pre_connected_call_num, 0) AS pre_connected_call_num,
                CONCAT(
                    LPAD(FLOOR(COALESCE(cm.pre_total_duration_sec, 0) / 3600), 2, '0'), ':',
                    LPAD(FLOOR((COALESCE(cm.pre_total_duration_sec, 0) % 3600) / 60), 2, '0'), ':',
                    LPAD(COALESCE(cm.pre_total_duration_sec, 0) % 60, 2, '0')
                ) AS pre_total_duration_sec,
                CASE WHEN cm.order_tags LIKE '%ORDER_TAG-POST_DESIGN_COMMUNICATION%' THEN '是' ELSE '否' END AS is_post_communication,
                COALESCE(cm.post_called_num, 0) AS post_called_num,
                COALESCE(cm.post_connected_call_num, 0) AS post_connected_call_num,
                CONCAT(
                    LPAD(FLOOR(COALESCE(cm.post_total_duration_sec, 0) / 3600), 2, '0'), ':',
                    LPAD(FLOOR((COALESCE(cm.post_total_duration_sec, 0) % 3600) / 60), 2, '0'), ':',
                    LPAD(COALESCE(cm.post_total_duration_sec, 0) % 60, 2, '0')
                ) AS post_total_duration_sec
            FROM call_metrics cm
            LEFT JOIN gms_order go_for_stakeholder ON go_for_stakeholder.code = cm.order_code
            LEFT JOIN gms_order_case_stakeholder_detail gocsd ON gocsd.order_id = go_for_stakeholder.id
            LEFT JOIN gms_team gt ON cm.team_id = gt.id
            LEFT JOIN gms_user gu ON cm.designer_id = gu.id
            LEFT JOIN gms_dentist gd ON gocsd.dentist_code = gd.code
            LEFT JOIN gms_hospital gh ON gocsd.hospital_code = gh.code
            LEFT JOIN gms_patient gp ON gocsd.patient_code = gp.code
            WHERE 1 = 1
        """;

    /**
     * 明细查询计数SQL
     */
    private static final String DETAIL_COUNT_QUERY = """
            WITH first_designs AS (
                SELECT
                    gc.code AS case_code,
                    gc.created as case_created_time,
                    gd.order_code,
                    MIN(gd.id) AS first_design_id,
                    MIN(gd.send_out) AS first_design_send_time
                FROM gms_case gc
                LEFT JOIN gms_design gd ON gc.code = gd.case_code
                WHERE gd.send_out IS NOT NULL
                    AND gd.status IN ('SENT','CONFIRMED','MODIFICATION','NOT_MODIFICATION')
                    AND (gd.send_out >= :startTime)
                    AND (gd.send_out <= :endTime)
                GROUP BY gc.code, gc.created, gd.order_code
            ),
            last_completed_tasks AS (
                SELECT 
                    fd.case_code,
                    fd.case_created_time,
                    fd.first_design_send_time,
                    fd.order_code,
                    gt.code AS task_code,
                    gt.assignee_id as designer_id,
                    go2.team_id,
                    go2.tags as order_tags,
                    ROW_NUMBER() OVER (
                        PARTITION BY fd.case_code 
                        ORDER BY go2.finished DESC
                    ) AS rn
                FROM first_designs fd
                JOIN gms_order go2 ON go2.code = fd.order_code
                JOIN gms_task gt ON gt.process_instance_id = go2.active_process_instance_id 
                JOIN gms_task_type gtt ON gt.type_id = gtt.id
                WHERE gt.status = 'COMPLETED'
                    AND gtt.id IN (29, 31, 35)
                    AND gt.finished < fd.first_design_send_time
            ),
            call_metrics AS (
                SELECT
                    lct.case_code,
                    lct.case_created_time,
                    lct.first_design_send_time,
                    lct.order_code,
                    lct.task_code,
                    lct.designer_id,
                    lct.team_id,
                    lct.order_tags
                FROM last_completed_tasks lct
                WHERE lct.rn = 1
            )
            SELECT COUNT(DISTINCT cm.case_code)
            FROM call_metrics cm
            LEFT JOIN gms_order go_for_stakeholder ON go_for_stakeholder.code = cm.order_code
            LEFT JOIN gms_order_case_stakeholder_detail gocsd ON gocsd.order_id = go_for_stakeholder.id
            LEFT JOIN gms_team gt ON cm.team_id = gt.id
            LEFT JOIN gms_user gu ON cm.designer_id = gu.id
            LEFT JOIN gms_dentist gd ON gocsd.dentist_code = gd.code
            LEFT JOIN gms_hospital gh ON gocsd.hospital_code = gh.code
            LEFT JOIN gms_patient gp ON gocsd.patient_code = gp.code
            WHERE 1 = 1
        """;

@Override
public PageImpl<DentistCommunicationDetailReportVM> dentistCommunicationDetailReport(DentistCommunicationReportDetailQueryVM param) {
    log.info("开始执行医生沟通明细报表查询，参数: {}", param);

    try {
        validateDetailQueryParams(param);

        List<Object[]> resultList = executeDetailQuery(param);

        Long totalElements = executeDetailCountQuery(param);

        List<DentistCommunicationDetailReportVM> content = resultList
                .stream()
                .map(this::mapToDetailViewModel)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(param.getPageNumber(), param.getPageSize());

        return new PageImpl<>(content, pageable, totalElements);

    } catch (Exception e) {
        log.warn("执行医生沟通明细报表查询失败，参数: {}", param, e);
        throw new RuntimeException("执行医生沟通明细报表查询失败: " + e.getMessage(), e);
    }
}

@SuppressWarnings("unchecked")
private List<Object[]> executeDetailQuery(DentistCommunicationReportDetailQueryVM param) {
    int page = param.getPageNumber() != null ? param.getPageNumber() : 0;
    int size = param.getPageSize() != null ? param.getPageSize() : 20;
    
    StringBuilder sqlBuilder = new StringBuilder(DETAIL_QUERY_TEMPLATE);
    
    // 添加筛选条件
    if (StringUtils.isNotBlank(param.getCasesCode())) {
        sqlBuilder.append(" AND cm.case_code = :casesCode");
    }
    if (param.getTeamId() != null) {
        sqlBuilder.append(" AND gt.id = :teamId");
    }
    if (param.getAssigneeId() != null) {
        sqlBuilder.append(" AND gu.id = :assigneeId");
    }
    if (StringUtils.isNotBlank(param.getDentistCode())) {
        sqlBuilder.append(" AND gocsd.dentist_code = :dentistCode");
    }
    if (StringUtils.isNotBlank(param.getHospitalCode())) {
        sqlBuilder.append(" AND gocsd.hospital_code = :hospitalCode");
    }
    if (StringUtils.isNotBlank(param.getPatientCode())) {
        sqlBuilder.append(" AND gocsd.patient_code = :patientCode");
    }
    if (param.getPreCommunicationTag() != null) {
        if (param.getPreCommunicationTag()) {
            sqlBuilder.append(" AND cm.order_tags LIKE '%ORDER_TAG-PRE_DESIGN_COMMUNICATION%'");
        } else {
            sqlBuilder.append(" AND (cm.order_tags NOT LIKE '%ORDER_TAG-PRE_DESIGN_COMMUNICATION%' OR cm.order_tags IS NULL)");
        }
    }
    if (param.getPosCommunicationTag() != null) {
        if (param.getPosCommunicationTag()) {
            sqlBuilder.append(" AND cm.order_tags LIKE '%ORDER_TAG-POST_DESIGN_COMMUNICATION%'");
        } else {
            sqlBuilder.append(" AND (cm.order_tags NOT LIKE '%ORDER_TAG-POST_DESIGN_COMMUNICATION%' OR cm.order_tags IS NULL)");
        }
    }
    
    sqlBuilder.append(" ORDER BY cm.first_design_send_time DESC");
    sqlBuilder.append(" LIMIT :limit OFFSET :offset");

    Query query = entityManager.createNativeQuery(sqlBuilder.toString());

    // 设置基础参数
    query.setParameter("startTime", param.getStartTime());
    query.setParameter("endTime", param.getEndTime());

    // 设置筛选参数
    if (StringUtils.isNotBlank(param.getCasesCode())) {
        query.setParameter("casesCode", param.getCasesCode());
    }
    if (param.getTeamId() != null) {
        query.setParameter("teamId", param.getTeamId());
    }
    if (param.getAssigneeId() != null) {
        query.setParameter("assigneeId", param.getAssigneeId());
    }
    if (StringUtils.isNotBlank(param.getDentistCode())) {
        query.setParameter("dentistCode", param.getDentistCode());
    }
    if (StringUtils.isNotBlank(param.getHospitalCode())) {
        query.setParameter("hospitalCode", param.getHospitalCode());
    }
    if (StringUtils.isNotBlank(param.getPatientCode())) {
        query.setParameter("patientCode", param.getPatientCode());
    }
    
    query.setParameter("limit", size);
    query.setParameter("offset", page * size);

    return query.getResultList();
}

private Long executeDetailCountQuery(DentistCommunicationReportDetailQueryVM param) {
    StringBuilder sqlBuilder = new StringBuilder(DETAIL_COUNT_QUERY);
    
    // 添加筛选条件
    if (StringUtils.isNotBlank(param.getCasesCode())) {
        sqlBuilder.append(" AND cm.case_code = :casesCode");
    }
    if (param.getTeamId() != null) {
        sqlBuilder.append(" AND gt.id = :teamId");
    }
    if (param.getAssigneeId() != null) {
        sqlBuilder.append(" AND gu.id = :assigneeId");
    }
    if (StringUtils.isNotBlank(param.getDentistCode())) {
        sqlBuilder.append(" AND gocsd.dentist_code = :dentistCode");
    }
    if (StringUtils.isNotBlank(param.getHospitalCode())) {
        sqlBuilder.append(" AND gocsd.hospital_code = :hospitalCode");
    }
    if (StringUtils.isNotBlank(param.getPatientCode())) {
        sqlBuilder.append(" AND gocsd.patient_code = :patientCode");
    }
    if (param.getPreCommunicationTag() != null) {
        if (param.getPreCommunicationTag()) {
            sqlBuilder.append(" AND cm.order_tags LIKE '%ORDER_TAG-PRE_DESIGN_COMMUNICATION%'");
        } else {
            sqlBuilder.append(" AND (cm.order_tags NOT LIKE '%ORDER_TAG-PRE_DESIGN_COMMUNICATION%' OR cm.order_tags IS NULL)");
        }
    }
    if (param.getPosCommunicationTag() != null) {
        if (param.getPosCommunicationTag()) {
            sqlBuilder.append(" AND cm.order_tags LIKE '%ORDER_TAG-POST_DESIGN_COMMUNICATION%'");
        } else {
            sqlBuilder.append(" AND (cm.order_tags NOT LIKE '%ORDER_TAG-POST_DESIGN_COMMUNICATION%' OR cm.order_tags IS NULL)");
        }
    }
    
    Query query = entityManager.createNativeQuery(sqlBuilder.toString());
    
    // 设置基础参数
    query.setParameter("startTime", param.getStartTime());
    query.setParameter("endTime", param.getEndTime());

    // 设置筛选参数
    if (StringUtils.isNotBlank(param.getCasesCode())) {
        query.setParameter("casesCode", param.getCasesCode());
    }
    if (param.getTeamId() != null) {
        query.setParameter("teamId", param.getTeamId());
    }
    if (param.getAssigneeId() != null) {
        query.setParameter("assigneeId", param.getAssigneeId());
    }
    if (StringUtils.isNotBlank(param.getDentistCode())) {
        query.setParameter("dentistCode", param.getDentistCode());
    }
    if (StringUtils.isNotBlank(param.getHospitalCode())) {
        query.setParameter("hospitalCode", param.getHospitalCode());
    }
    if (StringUtils.isNotBlank(param.getPatientCode())) {
        query.setParameter("patientCode", param.getPatientCode());
    }
    
    Object result = query.getSingleResult();
    return result != null ? ((Number) result).longValue() : 0L;
}

private DentistCommunicationDetailReportVM mapToDetailViewModel(Object[] row) {
    if (row == null) {
        return createEmptyDetailViewModel();
    }

    DentistCommunicationDetailReportVM vm = new DentistCommunicationDetailReportVM();
    
    // 映射查询结果到ViewModel
    vm.setCaseCode(safeToString(row[0]));                       // case_code
    vm.setTeamName(safeToString(row[1]));                       // team_name
    vm.setUserName(safeToString(row[2]));                       // user_name
    vm.setDentistName(safeToString(row[3]));                    // dentist_name
    vm.setDentistCode(safeToString(row[4]));                    // dentist_code
    vm.setHospitalName(safeToString(row[5]));                   // hospital_name
    vm.setPatientName(safeToString(row[6]));                    // patient_name
    vm.setPatientCode(safeToString(row[7]));                    // patient_code
    vm.setCaseCreatedTime(safeToString(row[8]));                // case_created_time
    vm.setFirstDesignSendTime(safeToString(row[9]));            // first_design_send_time
    vm.setTaskCode(safeToString(row[10]));                      // task_code
    vm.setIsPreCommunication(safeToString(row[11]));            // is_pre_communication
    vm.setPreCalledNum(safeToString(row[12]));                  // pre_called_num
    vm.setPreConnectedCallNum(safeToString(row[13]));           // pre_connected_call_num
    vm.setPreTotalDurationSec(safeToString(row[14]));           // pre_total_duration_sec
    vm.setIsPostCommunication(safeToString(row[15]));           // is_post_communication
    vm.setPostCalledNum(safeToString(row[16]));                 // post_called_num
    vm.setPostConnectedCallNum(safeToString(row[17]));          // post_connected_call_num
    vm.setPostTotalDurationSec(safeToString(row[18]));          // post_total_duration_sec

    return vm;
}

private void validateDetailQueryParams(DentistCommunicationReportDetailQueryVM param) {
    if (param == null) {
        throw new IllegalArgumentException("查询参数不能为空");
    }
    if (StringUtils.isBlank(param.getStartTime())) {
        throw new IllegalArgumentException("方案发送日期-开始日期不能为空");
    }
    if (StringUtils.isBlank(param.getEndTime())) {
        throw new IllegalArgumentException("方案发送日期-结束日期不能为空");
    }
    if (param.getPageNumber() == null || param.getPageNumber() < 0) {
        throw new IllegalArgumentException("页码不能为空且不能小于0");
    }
    if (param.getPageSize() == null || param.getPageSize() <= 0 || param.getPageSize() > 1000) {
        throw new IllegalArgumentException("页大小不能为空且必须在1-1000之间");
    }
}

/**
 * 创建空的明细ViewModel对象
 */
private DentistCommunicationDetailReportVM createEmptyDetailViewModel() {
    DentistCommunicationDetailReportVM vm = new DentistCommunicationDetailReportVM();
    vm.setCaseCode("未知");
    vm.setTeamName("未分组");
    vm.setUserName("未知");
    vm.setDentistName("未知");
    vm.setDentistCode("000");
    vm.setHospitalName("未知");
    vm.setPatientName("未知");
    vm.setPatientCode("000");
    vm.setCaseCreatedTime("1970-01-01 00:00:00");
    vm.setFirstDesignSendTime("1970-01-01 00:00:00");
    vm.setTaskCode("未知");
    vm.setIsPreCommunication("否");
    vm.setPreCalledNum("0");
    vm.setPreConnectedCallNum("0");
    vm.setPreTotalDurationSec("00:00:00");
    vm.setIsPostCommunication("否");
    vm.setPostCalledNum("0");
    vm.setPostConnectedCallNum("0");
    vm.setPostTotalDurationSec("00:00:00");
    return vm;
}