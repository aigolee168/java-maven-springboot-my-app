package com.bf.app.util;

import static com.bf.app.util.Collections.findAll;
import static com.bf.app.util.Collections.findFirst;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bf.app.entities.Authority;

public class Trees {

    public static Set<Authority> tree2list(Set<Authority> tree) {
        Set<Authority> list = new HashSet<>();
        tree2list(tree, list);
        return list;
    }

    private static void tree2list(Set<Authority> tree, Set<Authority> list) {
        for (Authority item : tree) {
            list.add(item);
            tree2list(item.getChildren(), list);
        }
    }

    public static Set<Authority> list2tree(Set<Authority> list, long parentId) {
        List<Authority> root = findAll(list, item -> item.getParentId() == parentId);
        Set<Authority> tree = new HashSet<>(root);
        tree.forEach(it -> list2tree(list, it));
        return tree;
    }

    private static void list2tree(Set<Authority> list, Authority tree) {
        List<Authority> children = findAll(list, item -> item.getParentId().equals(tree.getId()));
        tree.getChildren().addAll(children);
        tree.getChildren().forEach(it -> list2tree(list, it));
    }

    public static void trimTree(Set<Authority> tree) {
        tree.removeIf(it -> !it.isMarked());
        tree.forEach(it -> trimTree(it.getChildren()));
    }

    public static Authority findSubTree(Set<Authority> tree, List<String> path) {
        Authority sub = null;
        for (String pathItem : path) {
            sub = findFirst(tree, it -> it.getCode().equals(pathItem));
            if (sub == null)
                return null;
            tree = sub.getChildren();
        }
        return sub;
    }

    @FunctionalInterface
    public static interface TreeIterator {
        void apply(Authority auth, int depth);
    }

    public static void iterate(Authority tree, TreeIterator iterator) {
        iterate(tree, 0, iterator);
    }

    private static void iterate(Authority tree, int depth, TreeIterator iterator) {
        iterator.apply(tree, depth);
        tree.getChildren().forEach(it -> iterate(it, depth + 1, iterator));
    }

    public static void iterate(Set<Authority> tree, TreeIterator iterator) {
        tree.forEach(it -> iterate(it, iterator));
    }

    public static void printTree(Authority tree) {
        printTree(tree, 0);
    }

    private static void printTree(Authority tree, int depth) {
        Executors.repeating(depth, () -> System.out.print('\t'));
        System.out.println(Strings.concat(
                tree.getText(),
                "(",
                tree.getCode(),
                ",",
                tree.getId(),
                ")"));
        tree.getChildren().forEach(it -> printTree(it, depth + 1));
    }

    public static void printTree(Set<Authority> tree) {
        tree.forEach(it -> printTree(it, 0));
    }

}
