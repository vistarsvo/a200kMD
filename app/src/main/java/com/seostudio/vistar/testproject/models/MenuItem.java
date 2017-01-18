package com.seostudio.vistar.testproject.models;

public class MenuItem {
    private int id;
    private String short_name;
    private String full_name;
    private int active;
    private int sort;
    private int themeid;

    public int getThemeId() {
        return themeid;
    }

    public void setThemeId(int theme_id) {
        this.themeid = theme_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return short_name;
    }

    public void setShortName(String short_name) {
        this.short_name = short_name;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String fullname_name) {
        this.full_name = fullname_name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
