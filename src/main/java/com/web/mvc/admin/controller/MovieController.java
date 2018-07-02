package com.web.mvc.admin.controller;

import com.web.mvc.admin.model.Movie;
import com.web.mvc.admin.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xieyuhui on 17-3-25.
 */
@Controller
@RequestMapping("/")
public class MovieController {

    @Autowired
    @Qualifier("movieService")
    private MovieService movieService;

    @ResponseBody
    @RequestMapping(value = "movie.html",method = RequestMethod.POST)
    public String getMovieAll(ModelMap model) {
        List<Movie> movieAllList =  movieService.selectAll();
        model.addAttribute("movieAllList", movieAllList);
        return "movie";
    }
}
