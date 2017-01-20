package com.seostudio.vistar.testproject.models.collections;

import com.seostudio.vistar.testproject.models.AnekdotItem;

import java.util.LinkedList;
import java.util.List;

public class AnekdotItemCollection {
    public static AnekdotItemCollection lastLoaded;

    private List<AnekdotItem> aekdotItems = new LinkedList<AnekdotItem>();

    public List<AnekdotItem> getAnekdotItems() {
        return this.aekdotItems;
    }

    public int getAnekdotsItemsCount() {
        return this.aekdotItems.size();
    }

    public void addAnekdotItem(AnekdotItem censorItem) {
        this.aekdotItems.add(censorItem);
    }

    public void clear() {
        this.aekdotItems.clear();
    }
}
