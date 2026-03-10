package trees;

public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {

    public BinarySearchTree() {
        super();
    }

    public BinarySearchTree(T rootValue) {
        super(new Node<>(rootValue)); // one-node tree
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    protected boolean contains(T x, Node<T> t) {
        if (t == null) {
            return false;
        }

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            return contains(x, t.right);
        } else if (comparison < 0) {
            return contains(x, t.left);
        } else {
            return true;
        }
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    private Node<T> insert(T x, Node<T> t) {
        if (t == null)
            return new Node<T>(x);

        int comparison = x.compareTo(t.data);

        if (comparison > 0) {
            t.right = insert(x, t.right);
        } else if (comparison < 0) {
            t.left = insert(x, t.left);
        }

        return t;
    }

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
        return t;
    }

    public T findMax() {
        Node<T> n = findMax(root);
        if (n == null)
            throw new IllegalStateException("empty tree");
        return n.data;
    }

    protected Node<T> findMax(Node<T> t) {
        if (t == null)
            return null;
        if (t.right == null)
            return t;
        return findMax(t.right);
    }

    public T findMin() {
        Node<T> n = findMin(root);
        if (n == null)
            throw new IllegalStateException("empty tree");
        return n.data;
    }

    protected Node<T> findMin(Node<T> t) {
        if (t == null)
            return null;
        if (t.left == null)
            return t;
        return findMin(t.left);
    }
}
