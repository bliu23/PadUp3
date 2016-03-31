package com.example.brandonliu.padup3;

import java.util.ArrayList;

/**
 * Created by brandonliu on 3/30/16.
 */
public class Category {
    public Category(String category) {
        m_cat= category;
        m_dungeons = new ArrayList<String>();
    }
    private String m_cat;
    private ArrayList<String> m_dungeons;


}
