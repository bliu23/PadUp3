package com.example.brandonliu.padup3;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brandonliu on 3/30/16.
 */
public class Category {
    public Category(String category) {
        m_cat= category;
        m_dungeons = new ArrayList<String>();
    }

    public void setCat(String cat) {
        m_cat = cat;
    }

    public void addDungeon(String dungeon) {
        m_dungeons.add(dungeon);
    }

    public String getCat() {
        return m_cat;
    }

    public ArrayList<String> getDungeons() {
        return m_dungeons;
    }

    //for debugging purposes
    public void print() {
        Log.d("testprint", String.valueOf(m_dungeons.size()));
        for(int i = 0; i < m_dungeons.size(); i++) {
            Log.d("testprint", m_dungeons.get(i));
        }
    }

    private String m_cat;
    private ArrayList<String> m_dungeons;


}
