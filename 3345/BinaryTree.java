// Simple binary tree for postfix alg

public class BinaryTree<T> implements Iterable<T> {
    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T d) {
            this(d, null, null);
        }

        Node(T d, Node<T> l, Node<T> r) {
            this.data = d;
            this.left = l;
            this.right = r;
        }
    }

    private Node<T> head;
    
    public class BinaryTree() {

    }
}