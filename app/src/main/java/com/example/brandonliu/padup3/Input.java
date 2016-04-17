package com.example.brandonliu.padup3;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brandonliu on 4/15/16.
 */
public class Input implements Parcelable {
    private String category;
    private String dungeonName;
    private String roomId;
    private double timestamp;
    private ArrayList<String> monsters;
    private ArrayList<String> imgs;

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
    public ArrayList<String> getMonsters() { return monsters; }
    public ArrayList<String> getImgs() { return imgs; }
    public double getTimestamp() { return timestamp; }

    //set methods
    public void setRoomId(String id) { roomId = id; }
    public void setMonsters(ArrayList<String> mon) { monsters = mon; }
    public void setImgs(ArrayList<String> im) { imgs = im; }
    public void setTimestamp(double ts) { timestamp = ts; }

    public void print() {
        Log.d("category", category);
        Log.d("dungeon", dungeonName);
        Log.d("roomId", roomId);
        for(int i = 0; i < monsters.size(); i++) {
            Log.d("monsters", monsters.get(i));
            Log.d("imgs", imgs.get(i));
        }
    }

    protected Input(Parcel in) {
        category = in.readString();
        dungeonName = in.readString();
        roomId = in.readString();
        timestamp = in.readDouble();

        if (in.readByte() == 0x01) {
            monsters = new ArrayList<String>();
            in.readList(monsters, String.class.getClassLoader());
        } else {
            monsters = null;
        }
        if (in.readByte() == 0x01) {
            imgs = new ArrayList<String>();
            in.readList(imgs, String.class.getClassLoader());
        } else {
            imgs = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(dungeonName);
        dest.writeString(roomId);
        dest.writeDouble(timestamp);
        if (monsters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(monsters);
        }
        if (imgs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imgs);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Input> CREATOR = new Parcelable.Creator<Input>() {
        @Override
        public Input createFromParcel(Parcel in) {
            return new Input(in);
        }

        @Override
        public Input[] newArray(int size) {
            return new Input[size];
        }
    };
}