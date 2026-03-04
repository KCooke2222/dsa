package trees.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import trees.BinarySearchTree;

public class BinarySearchTreeTest {

    private List<Integer> collect(Iterator<Integer> it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext())
            out.add(it.next());
        return out;
    }

    @Test
    void contains_isFalseOnEmptyTree() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertFalse(bst.contains(10));
    }

    @Test
    void insert_thenContains_isTrue() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        assertTrue(bst.contains(10));
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(15));
        assertFalse(bst.contains(99));
    }

    @Test
    void findMinMax_onPopulatedTree() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(2);
        bst.insert(12);
        bst.insert(20);

        assertEquals(2, bst.findMin());
        assertEquals(20, bst.findMax());
    }

    @Test
    void findMinMax_onEmptyTree_throws() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertThrows(IllegalStateException.class, bst::findMin);
        assertThrows(IllegalStateException.class, bst::findMax);
    }

    @Test
    void inorderTraversal_isSortedAfterInserts() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(2);
        bst.insert(12);
        bst.insert(20);

        assertEquals(List.of(2, 5, 10, 12, 15, 20),
                collect(bst.inorderIterator()));
    }

    @Test
    void remove_leafNode() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        bst.remove(5);

        assertFalse(bst.contains(5));
        assertEquals(List.of(10, 15), collect(bst.inorderIterator()));
    }

    @Test
    void remove_nodeWithOneChild() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(2);

        bst.remove(5);

        assertFalse(bst.contains(5));
        assertTrue(bst.contains(2));
        assertEquals(List.of(2, 10), collect(bst.inorderIterator()));
    }

    @Test
    void remove_nodeWithTwoChildren() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(2);
        bst.insert(7);

        bst.remove(10);

        assertFalse(bst.contains(10));
        assertEquals(List.of(2, 5, 7, 15), collect(bst.inorderIterator()));
    }

    @Test
    void remove_missingValue_doesNothing() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        bst.remove(999);

        assertEquals(List.of(5, 10, 15), collect(bst.inorderIterator()));
    }
}
