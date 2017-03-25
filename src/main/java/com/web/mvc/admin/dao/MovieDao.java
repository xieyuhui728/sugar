package com.web.mvc.admin.dao;

import com.web.mvc.admin.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieyuhui on 17-3-25.
 */
@Repository
public interface MovieDao {
    List<Movie> selectAll();
}
