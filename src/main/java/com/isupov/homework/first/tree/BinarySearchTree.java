package com.isupov.homework.first.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BinarySearchTree<V> implements Tree<V> {

    private Node<V> rootNode;

    public BinarySearchTree() {
        rootNode = null;
    }

    @Override
    public void put(V value) {
        Objects.requireNonNull(value);
        rootNode = putRecursive(rootNode, value);
    }

    private Node<V> putRecursive(Node<V> current, V value) {
        if (current == null) {
            return new Node<>(value);
        }

        @SuppressWarnings("unchecked")
        Comparable<? super V> v = (Comparable<? super V>) value;
        int cmp = v.compareTo(current.data);
        if (cmp < 0) {
            current.left = putRecursive(current.left, value);
        } else if (cmp > 0) {
            current.right = putRecursive(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    @Override
    public boolean contains(V value) {

        @SuppressWarnings("unchecked")
        Comparable<? super V> v = (Comparable<? super V>) value;
        return containsRecursive(rootNode, v);
    }

    private boolean containsRecursive(Node<V> current, Comparable<? super V> v) {
        if (current == null) {
            return false;
        }
        int cmp = v.compareTo(current.data);
        if (cmp == 0) {
            return true;
        }
        return cmp < 0 ? containsRecursive(current.left, v) : containsRecursive(current.right, v);
    }

    @Override
    public void remove(V value) {
        @SuppressWarnings("unchecked")
        Comparable<? super V> v = (Comparable<? super V>) value;
        rootNode = removeRecursive(rootNode, v);
    }

    private Node<V> removeRecursive(Node<V> current, Comparable<? super V> v) {
        if (current == null) {
            return null;
        }
        int cmp = v.compareTo(current.data);
        if (cmp == 0) {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            V smallestValue = findSmallestValue(current.right);
            current.data = smallestValue;
            current.right = removeRecursive(current.right, (Comparable<? super V>) smallestValue);
            return current;


        }
        if (cmp < 0) {
            current.left = removeRecursive(current.left, v);
            return current;
        }
        current.right = removeRecursive(current.right, v);
        return current;
    }

    private V findSmallestValue(Node<V> current) {
        return current.left == null ? current.data : findSmallestValue(current.left);
    }

    @Override
    public void putAll(Tree<? extends V> tree) {
        tree.values().forEach(this::put);
    }

    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>();
        traverseInOrder(rootNode, values);
        return values;
    }

    private void traverseInOrder(Node<V> current, List<V> values) {
        if (current != null) {
            traverseInOrder(current.left, values);
            values.add(current.data);
            traverseInOrder(current.right, values);
        }
    }

    private class Node<V> {

        private V data;
        private Node<V> left;
        private Node<V> right;

        private Node(V data) {
            this.data = data;

        }
    }
}