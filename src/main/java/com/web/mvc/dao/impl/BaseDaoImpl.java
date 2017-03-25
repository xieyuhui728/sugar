package com.web.mvc.dao.impl;

import com.web.mvc.dao.BaseDao;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Created by xieyuhui on 17-3-25.
 */
public class BaseDaoImpl<T>  implements BaseDao<T>{
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    public T selectOne(T t) {
        return sqlSessionTemplate.selectOne("");
    }
}
