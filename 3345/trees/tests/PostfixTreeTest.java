package trees.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import trees.BinaryTree;
import trees.PostfixTree;

public class PostfixTreeTest {

    private List<String> collect(Iterator<String> it) {
        List<String> vals = new ArrayList<>();
        while (it.hasNext()) {
            vals.add(it.next());
        }
        return vals;
    }

    @Test
    void buildsExpectedTraversals_forSampleExpression() {
        PostfixTree postfix = new PostfixTree("5 1 2 + 4 * + 3 -");
        BinaryTree<String> tree = postfix.getRoot();

        assertEquals(List.of("5", "+", "1", "+", "2", "*", "4", "-", "3"), collect(tree.inorderIterator()));
        assertEquals(List.of("-", "+", "5", "*", "+", "1", "2", "4", "3"), collect(tree.preorderIterator()));
        assertEquals(List.of("5", "1", "2", "+", "4", "*", "+", "3", "-"), collect(tree.postorderIterator()));
        assertEquals(List.of("-", "+", "3", "5", "*", "+", "4", "1", "2"), collect(tree.levelorderIterator()));
    }

    @Test
    void supportsDecimalsAndNegatives() {
        PostfixTree postfix = new PostfixTree("-3.5 2 +");
        BinaryTree<String> tree = postfix.getRoot();

        assertEquals(List.of("-3.5", "+", "2"), collect(tree.inorderIterator()));
        assertEquals(List.of("+", "-3.5", "2"), collect(tree.preorderIterator()));
        assertEquals(List.of("-3.5", "2", "+"), collect(tree.postorderIterator()));
    }

    @Test
    void singleOperandBuildsSingleNodeTree() {
        PostfixTree postfix = new PostfixTree("42");
        BinaryTree<String> tree = postfix.getRoot();

        assertEquals(List.of("42"), collect(tree.inorderIterator()));
        assertEquals(List.of("42"), collect(tree.preorderIterator()));
        assertEquals(List.of("42"), collect(tree.postorderIterator()));
        assertEquals(List.of("42"), collect(tree.levelorderIterator()));
    }

    @Test
    void malformedExpressionWithTooFewOperands_throws() {
        assertThrows(java.util.NoSuchElementException.class, () -> new PostfixTree("1 +"));
    }
}
