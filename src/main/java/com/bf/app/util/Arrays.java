package com.bf.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Arrays {

    public static <T> T findFirst(T[] s, Predicate<T> p) {
        for (T it : s) {
            if (p.test(it))
                return it;
        }
        return null;
    }

    public static <T> boolean contain(T[] s, Predicate<T> p) {
        for (T it : s) {
            if (p.test(it))
                return true;
        }
        return false;
    }

    public static <T> List<T> findAll(T[] s, Predicate<T> p) {
        List<T> list = new ArrayList<>();
        for (T it : s) {
            if (p.test(it))
                list.add(it);
        }
        return list;
    }

    public static <T> int indexOf(T[] s, T target) {
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals(target))
                return i;
        }
        return -1;
    }

}
