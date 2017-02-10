package com.seostudio.vistar.testproject.models.collections;

import com.seostudio.vistar.testproject.models.AnekdotItem;

import java.util.LinkedList;
import java.util.List;

public class AnekdotItemCollection {
    public static AnekdotItemCollection lastLoaded;

    private List<AnekdotItem> anekdotItems = new LinkedList<AnekdotItem>();

    public List<AnekdotItem> getAnekdotItems() {
        return this.anekdotItems;
    }

    public int getAnekdotsItemsCount() {
        return this.anekdotItems.size();
    }

    public void addAnekdotItem(AnekdotItem anekdotItem) {
        this.anekdotItems.add(anekdotItem);
    }

    public void clear() {
        this.anekdotItems.clear();
    }
}
