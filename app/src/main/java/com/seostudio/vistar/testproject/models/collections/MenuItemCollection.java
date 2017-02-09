package com.seostudio.vistar.testproject.models.collections;

import com.seostudio.vistar.testproject.models.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

final public class MenuItemCollection {
    public static MenuItemCollection lastLoaded;

    private List<MenuItem> menuItems = new LinkedList<MenuItem>();

    public ArrayList<String> getArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("По всем категориям");
        for (MenuItem currentMenuItem  : menuItems) {
            list.add(currentMenuItem.getFullName());
        }
        return list;
    }

    public MenuItem getItemByIndex(int index) {
        try {
            return this.menuItems.get( index );
        } catch ( IndexOutOfBoundsException e ) {
            return null;
        }
    }

    public MenuItem getItemById(int id) {
        for (MenuItem currentMenuItem  : this.menuItems) {
            if (currentMenuItem.getId() == id) return currentMenuItem;
        }
        return null;
    }

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
