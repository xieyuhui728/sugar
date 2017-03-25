package com.web.mvc.admin.service.impl;

import com.web.mvc.admin.dao.MovieDao;
import com.web.mvc.admin.model.Movie;
import com.web.mvc.admin.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xieyuhui on 17-3-25.
 */
@Service("movieService")
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> selectAll() {
        return movieDao.selectAll();
    }
}
