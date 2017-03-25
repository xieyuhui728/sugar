package com.web.mvc.dao;

import com.web.mvc.admin.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by xieyuhui on 2017/2/16.
 */
@Repository
public interface UserDao {
    User getUser(User user);
}
