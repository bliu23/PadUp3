package com.example.brandonliu.padup3;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brandonliu on 3/30/16.
 */
public class Category implements Parcelable {
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



    protected Category(Parcel in) {
        m_cat = in.readString();
        if (in.readByte() == 0x01) {
            m_dungeons = new ArrayList<String>();
            in.readList(m_dungeons, String.class.getClassLoader());
        } else {
            m_dungeons = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_cat);
        if (m_dungeons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(m_dungeons);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}