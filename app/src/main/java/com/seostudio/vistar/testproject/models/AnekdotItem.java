package com.seostudio.vistar.testproject.models;

import android.util.Log;

public class AnekdotItem {
    private int id;
    private int theme_id;
    private String text;
    private int num;
    private int favorite_id = 0;
    private String favorite_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        Log.d("Real ID", Integer.toString(id));
        this.id = id;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public String getFavorite_date() {
        return favorite_date;
    }

    public void setFavorite_date(String favorite_date) {
        this.favorite_date = favorite_date;
    }
}
