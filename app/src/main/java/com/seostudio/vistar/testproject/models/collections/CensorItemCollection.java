package com.seostudio.vistar.testproject.models.collections;

import com.seostudio.vistar.testproject.models.CensorItem;

import java.util.LinkedList;
import java.util.List;


final public class CensorItemCollection {
    public static CensorItemCollection lastLoaded;

    private List<CensorItem> censorItems = new LinkedList<CensorItem>();

    public List<CensorItem> getCensorItems() {
        return this.censorItems;
    }

    public int getCensorItemsCount() {
        return this.censorItems.size();
    }

    public void addCensorItem(CensorItem censorItem) {
        this.censorItems.add(censorItem);
    }

    public void clear() {
        this.censorItems.clear();
    }
}
