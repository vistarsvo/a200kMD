package com.seostudio.vistar.testproject.models.collections;

import com.seostudio.vistar.testproject.models.MenuItem;

import java.util.LinkedList;
import java.util.List;

final public class MenuItemCollection {
    public static MenuItemCollection lastLoaded;

    private List<MenuItem> menuItems = new LinkedList<MenuItem>();

    public List<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public int getMenuItemsCount() {
        return this.menuItems.size();
    }

    public void addMenuItem(MenuItem mi) {
        this.menuItems.add(mi);
    }

    public void clear() {
        this.menuItems.clear();
    }
}
