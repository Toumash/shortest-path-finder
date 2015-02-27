package pl.codesharks.pathfinder.util;

import org.apache.commons.collections4.list.TreeList;

import java.util.Collections;

public class SortedList<E extends Comparable<E>> {


    private TreeList<E> list = new TreeList<>();

    public E getFirst() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }

    public void add(E node) {
        list.add(node);
        Collections.sort(list);
    }

    public void remove(E n) {
        list.remove(n);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(E n) {
        return list.contains(n);
    }
}
