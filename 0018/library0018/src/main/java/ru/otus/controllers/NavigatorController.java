package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class NavigatorController {

    public static final String BOOK_PAGE = "bookPage";

    public static final String AUTHOR_PAGE = "authorPage";

    public static final String GENRE_PAGE = "genrePage";

    public static final String COMMENTARY_PAGE = "commentaryPage";


    public static Map<String, Object> getValue(String key, Object value) {
        return new HashMap<>() {{
            put(BOOK_PAGE, false);
            put(AUTHOR_PAGE, false);
            put(GENRE_PAGE, false);
            put(COMMENTARY_PAGE, false);
            put(key, value);
        }};
    }

    @GetMapping(path = {"/", "/bookPage"})
    public String getReaders(Model model) {
        model.addAllAttributes(getValue(BOOK_PAGE, true));
        return BOOK_PAGE;
    }

    @GetMapping("/authorPage")
    public String getCameras(Model model) {
        model.addAllAttributes(getValue(AUTHOR_PAGE, true));
        return AUTHOR_PAGE;
    }


    @GetMapping("/genrePage")
    public String getStatistic(Model model) {
        model.addAllAttributes(getValue(GENRE_PAGE, true));
        return GENRE_PAGE;
    }


    @GetMapping("/commentaryPage")
    public String getTag(Model model) {
        model.addAllAttributes(getValue(COMMENTARY_PAGE, true));
        return COMMENTARY_PAGE;
    }

}
