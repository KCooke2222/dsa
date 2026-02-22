// Simple binary tree for postfix alg (as basic as necc)

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

    private Node<T> root;
    
    public BinaryTree() {
        // create null
        // create with l r
    }

    public boolean containsNumber(int x) {
        contains(x, root);
    }

    private boolean contains(T x, Node<T> t) {
        int comparison = x.compareTo(t)
    }

    private Node<T> findMin(Node<T> t) {
        if (t == null)
            return null;
        if (t.right == null)
            return t.right;
        return findMin(t.right);
    }

    private Node<T> findMax(Node<T> t) {
        if (t == null)
            return null;
        if (t.left == null)
            return t.left;
        return findMin(t.left);
    }

    // infix print (inorder traversal)
    // need parantheses
    // if node is a leaf: print data
    // else: print ( left data right )
}