package com.example.brandonliu.padup3;

import android.util.Log;

/**
 * Created by brandonliu on 4/15/16.
 */
public class Input {
    private String category;
    private String dungeonName;
    private String roomId;
    private String [] monsters;
    private String [] imgs;

    public Input() {

    }

    public Input(String cat, String dun) {
        category = cat;
        dungeonName = dun;
    }

    //get methods
    public String getCategory() { return category; }
    public String getDungeonName() { return dungeonName; }
    public String getRoomId() { return roomId; }
    public String[] getMonsters() { return monsters; }
    public String[] getImgs() { return imgs; }

    //set methods
    public void setRoomId(String id) { roomId = id; }
    public void setMonsters(String[] mon) { monsters = mon; }
    public void setImgs(String[] im) { imgs = im; }

    public void printInitial() {
        Log.d("category", category);
        Log.d("dungeon", dungeonName);
    }

    public void print() {
        Log.d("category", category);
        Log.d("dungeon", dungeonName);
        Log.d("roomId", roomId);
        for(int i = 0; i < monsters.length; i++) {
            Log.d("monsters", monsters[i]);
            Log.d("imgs", imgs[i]);
        }
    }
}
