package trees;

public class BinarySearchTree {
        private boolean contains(T x, Node<T> t) {
        if (t == null) {
            return false;
        }

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            return contains(x, t.right);
        } else if (comparison < 0){
            return contains(x, t.left);
        } else {
            return true;
        }
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
}
