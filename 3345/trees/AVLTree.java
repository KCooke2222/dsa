package trees;

import trees.BinaryTree.Node;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    protected static class AVLNode<T> extends Node<T> {
        int height;

        AVLNode(T data) {
            super(data);
            height = 1;
        }
    }

    public AVLTree() {
        super();
    }

    @Override
    public void remove(T x) {
        root = remove(x, root);
    }

    private Node<T> remove(T x, Node<T> t) {
        if (t == null)
            return t; // not found (do nothing)

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            t.right = remove(x, t.right);
        } else if (comparison < 0) {
            t.left = remove(x, t.left);
        } else {
            // found node
            if (t.right == null && t.left == null) { // leaf
                return null;
            } else if (t.right == null ^ t.left == null) { // single child
                if (t.right == null) {
                    return t.left;
                } else {
                    return t.right;
                }
            } else { // double child
                // inorder predecessor
                Node<T> predecessor = findMax(t.left);
                t.data = predecessor.data;
                t.left = remove(predecessor.data, t.left);
            }
        }
        return balance(t);
    }

    @Override
    public void insert(T x) {
        root = insert(x, root);
    }

    private Node<T> insert(T x, Node<T> t) {
        if (t == null)
            return new AVLNode<>(x);

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            t.right = insert(x, t.right);
        } else if (comparison < 0) {
            t.left = insert(x, t.left);
        }

        return balance(t);
    }

    private Node<T> balance(Node<T> t) {
        if (t == null)
            return t;

        int diff = h(t.left) - h(t.right);
        if (diff > 1) { // left issue
            if (h(t.left.left) >= h(t.left.right)) {
                return rotateLeftChild(t);
            } else {
                return doubleRotateLeftChild(t);
            }
        } else if (diff < -1) { // right issue
            if (h(t.right.right) >= h(t.right.left)) {
                return rotateRightChild(t);
            } else {
                return doubleRotateRightChild(t);
            }
        }

        ((AVLNode<T>) t).height = 1 + Math.max(h(t.left), h(t.right));
        return t; // no balance needed
    }

    private Node<T> rotateLeftChild(Node<T> t) {
        Node<T> child = t.left;
        Node<T> temp = child.right; // child extra side

        child.right = t;
        t.left = temp;

        ((AVLNode<T>) t).height = 1 + Math.max(h(t.left), h(t.right));
        ((AVLNode<T>) child).height = 1 + Math.max(h(child.left), h(child.right));

        return child;
    }

    private Node<T> rotateRightChild(Node<T> t) {
        Node<T> child = t.right;
        Node<T> temp = child.left; // child extra side

        child.left = t;
        t.right = temp;

        ((AVLNode<T>) t).height = 1 + Math.max(h(t.left), h(t.right));
        ((AVLNode<T>) child).height = 1 + Math.max(h(child.left), h(child.right));

        return child;
    }

    private Node<T> doubleRotateLeftChild(Node<T> t) {
        t.left = rotateRightChild(t.left);
        return rotateLeftChild(t);
    }

    private Node<T> doubleRotateRightChild(Node<T> t) {
        t.right = rotateLeftChild(t.right);
        return rotateRightChild(t);
    }

    private int h(Node<T> n) {
        return (n == null) ? 0 : ((AVLNode<T>) n).height;
    }

}
