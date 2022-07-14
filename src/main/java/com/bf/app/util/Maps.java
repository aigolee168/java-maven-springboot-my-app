package com.bf.app.util;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    
    @SafeVarargs
    public static <K, V> Map<K, V> of(Entry<K, V> ...entries) {
        Map<K, V> map = new HashMap<>();
        for (Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
    
    public static <K, V> Map<K, V> of(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
    
    public static class Entry<K, V> {
        private K key;
        private V value;
        
        public Entry(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public void setKey(K key) {
            this.key = key;
        }
        public V getValue() {
            return value;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

}
