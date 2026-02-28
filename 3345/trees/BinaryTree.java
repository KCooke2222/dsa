package trees;
// Simple binary tree for postfix alg (as basic as necc)

import java.util.*;

import lists.MyArrayList;

public class BinaryTree<T extends Comparable<? super T>> implements Iterable<T> { 
    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node(T d) {
            this(d, null, null);
        }

        public Node(T d, Node<T> l, Node<T> r) {
            this.data = d;
            this.left = l;
            this.right = r;
        }
    }

    private int size;
    private Node<T> root;
    
    // create null
    public BinaryTree() {
        this.size = 0;
        this.root = null;
    }

    // create w/ data
    public BinaryTree(T data) {
        this.size = 1;
        this.root = new Node(data);
    }


    public boolean containsNumber(int x) {
        contains(x, root);
    }

    
    // infix print (inorder traversal)
    // need parantheses
    // if node is a leaf: print data
    // else: print ( left data right )

    public Iterator<T> iterator() {
        return inorderIterator();
    }

    public Iterator<T> inorderIterator() {
        return new InorderIterator();
    }

    public Iterator<T> preorderIterator() {
        return new PreorderIterator();
    }

     public Iterator<T> postorderIterator() {
        return new PostorderIterator();
    }

     public Iterator<T> levelorderIterator() {
        return new LevelOrderIterator();
    }


    // Uses stack to perform inorder as iterator cannot be recursive
    private class InorderIterator implements Iterator<T> {
        private final Deque<Node<T>> st = new ArrayDeque<>();
        private Node<T> cur = root;

        public boolean hasNext() {
            return cur != null || !st.isEmpty();
        }
        
        public T next() {
            while (cur != null) {
                st.push(cur);
                cur = cur.left;
            }
            if (st.isEmpty()) throw new NoSuchElementException();

            Node<T> n = st.pop(); // visit the node
            T val = n.data;
            cur = n.right; // l, n, r
            return val;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // n l r
    private class PreorderIterator implements Iterator<T> {
        private final Deque<Node<T>> st = new ArrayDeque<>();
        // no cur needed

        // push root to start out
        public PreorderIterator() {
            if (root != null) {
                st.push(root);
            }
        }

        public boolean hasNext() {
            return !st.isEmpty();
        }
        
        public T next() {
            if (st.isEmpty()) throw new NoSuchElementException();

            Node<T> n = st.pop(); // visit the node
            T val = n.data;

            // reverse order into stack
            if (n.right != null) {
                st.push(n.right);
            }
            if (n.left != null) {
                st.push(n.left);
            }

            return val;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostorderIterator implements Iterator<T> {
        
    }
    private class LevelOrderIterator implements Iterator<T> {

    }
}