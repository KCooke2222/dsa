package trees;

import java.util.Stack;

import trees.BinaryTree.Node;

public class SplayTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    protected static class SplayNode<T> extends Node<T> {
        int height;

        SplayNode(T data) {
            super(data);
            height = 1;
        }
    }

    public SplayTree() {
        super();
    }

    // insert creates SplayNode so rotations can safely cast; remove same as BST
    @Override
    public void insert(T x) {
        root = insert(x, root);
    }

    private Node<T> insert(T x, Node<T> t) {
        if (t == null)
            return new SplayNode<>(x);

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            t.right = insert(x, t.right);
        } else if (comparison < 0) {
            t.left = insert(x, t.left);
        }

        return t;
    }

    // new contains
    @Override
    public boolean contains(T x) {
        return contains(x, root);
    }

    private boolean contains(T x, Node<T> t) {
        if (t == null) {
            return false;
        }

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            return contains(x, t.right);
        } else if (comparison < 0) {
            return contains(x, t.left);
        } else {
            splay(x, root);
            return true;
        }
    }

    // zig zags are inline
    private void splay(T x, Node<T> t) {
        // build stack for parent / grandparent access
        Stack<Node<T>> st = new Stack<>();

        while (t != null) {
            int comparison = x.compareTo(t.data);
            if (comparison > 0) {
                st.push(t);
                t = t.right;
            } else if (comparison < 0) {
                st.push(t);
                t = t.left;
            } else {
                break;
            }
        }

        if (t == null)
            return;

        // zig, zig-zig, zig-zag
        while (t != root) {
            Node<T> p = null, g = null, gg = null;
            if (!st.isEmpty())
                p = st.pop();
            if (!st.isEmpty())
                g = st.pop();
            if (!st.isEmpty())
                gg = st.peek();

            if (p == null) {
                root = t;
                break;
            }

            if (g == null) {
                if (p.left == t) { // left zig
                    t = rotateLeftChild(p);
                } else { // right zig
                    t = rotateRightChild(p);
                }
            } else if (g.left == p) {

                if (p.left == t) { // left zig-zig
                    p = rotateLeftChild(g); // g and p returns p (new root of subtree)
                    t = rotateLeftChild(p); // p and t
                } else { // left zig-zag
                    g.left = rotateRightChild(p); // p and t
                    t = rotateLeftChild(g); // g and t
                }

            } else if (g.right == p) {
                if (p.right == t) { // right zig-zig
                    p = rotateRightChild(g);
                    t = rotateRightChild(p);
                } else { // right zig-zag
                    g.right = rotateLeftChild(p);
                    t = rotateRightChild(g);
                }

            }

            // attach splayed seciton back
            if (gg == null) {
                root = t;
                break;
            } else if (gg.left == g) {
                gg.left = t;
            } else {
                gg.right = t;
            }
        }
    }

    private Node<T> rotateLeftChild(Node<T> t) {
        Node<T> child = t.left;
        Node<T> temp = child.right; // child extra side

        child.right = t;
        t.left = temp;

        ((SplayNode<T>) t).height = 1 + Math.max(h(t.left), h(t.right));
        ((SplayNode<T>) child).height = 1 + Math.max(h(child.left), h(child.right));

        return child;
    }

    private Node<T> rotateRightChild(Node<T> t) {
        Node<T> child = t.right;
        Node<T> temp = child.left; // child extra side

        child.left = t;
        t.right = temp;

        ((SplayNode<T>) t).height = 1 + Math.max(h(t.left), h(t.right));
        ((SplayNode<T>) child).height = 1 + Math.max(h(child.left), h(child.right));

        return child;
    }

    private int h(Node<T> n) {
        return (n == null) ? 0 : ((SplayNode<T>) n).height;
    }
}
