package com.bf.app.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Collections {

    public static <T> T findFirst(Collection<T> s, Predicate<T> p) {
        for (T it : s) {
            if (p.test(it))
                return it;
        }
        return null;
    }

    public static <T> boolean contain(Collection<T> s, Predicate<T> p) {
        for (T it : s) {
            if (p.test(it))
                return true;
        }
        return false;
    }

    public static <T> List<T> findAll(Collection<T> s, Predicate<T> p) {
        List<T> list = new ArrayList<>();
        for (T it : s) {
            if (p.test(it))
                list.add(it);
        }
        return list;
    }

}
