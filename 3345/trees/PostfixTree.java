package trees;
// postfix -> binary tree

import java.util.*;
// infix output

public class PostfixTree {
    private String expr;
    private final Deque<BinaryTree.Node<String>> st = new ArrayDeque<>();
    private BinaryTree<String> bt;

    public PostfixTree(String expr) {
        this.expr = expr;
        this.bt = buildTree(expr);
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private BinaryTree<String> buildTree(String expr) {
        String[] parts = expr.split(" ");

        for (String part : parts) {
            if (isNumber(part)) {
                st.push(new BinaryTree.Node<String>(part));
            } else {
                BinaryTree.Node<String> right = st.pop();
                BinaryTree.Node<String> left = st.pop();

                BinaryTree.Node<String> op = new BinaryTree.Node<>(part);
                op.left = left;
                op.right = right;

                st.push(op);
            }
        }

        BinaryTree<String> bt = new BinaryTree<String>(st.pop());
        return bt;
    }

    public BinaryTree<String> getRoot() {
        return bt;
    }

    public static void main(String[] args) {
        PostfixTree bt = new PostfixTree("5 1 2 + 4 * + 3 -");
        BinaryTree<String> tree = bt.getRoot();

        System.out.println("Inorder:");
        for (String s : tree) {
            System.out.print(s + " ");
        }

        System.out.println("\nPreorder:");
        Iterator<String> pre = tree.preorderIterator();
        while (pre.hasNext()) {
            System.out.print(pre.next() + " ");
        }

        System.out.println("\nPostorder:");
        Iterator<String> post = tree.postorderIterator();
        while (post.hasNext()) {
            System.out.print(post.next() + " ");
        }

        System.out.println("\nLevel-order:");
        Iterator<String> level = tree.levelorderIterator();
        while (level.hasNext()) {
            System.out.print(level.next() + " ");
        }
        System.out.println();
    }

}
