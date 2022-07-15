package com.bf.app.util;

import java.util.Collection;
import java.util.Iterator;

public class Strings {

    public static String concat(Object... array) {
        StringBuilder builder = new StringBuilder();
        for (Object string : array) {
            builder.append(string);
        }
        return builder.toString();
    }

    public static String join(String delim, Collection<Object> array) {
        StringBuilder builder = new StringBuilder();
        Iterator<Object> it = array.iterator();
        if (it.hasNext()) {
            builder.append(it.next());
        }
        while (it.hasNext()) {
            builder.append(delim).append(it.next());
        }
        return builder.toString();
    }

    public static String join(String delim, Object... array) {
        StringBuilder builder = new StringBuilder();
        if (array.length > 0) {
            builder.append(array[0]);
        }
        for (int i = 1; i < array.length; i++) {
            builder.append(delim).append(array[i]);
        }
        return builder.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().equals("");
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

}
