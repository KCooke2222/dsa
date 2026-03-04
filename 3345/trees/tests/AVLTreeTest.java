package trees.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import trees.AVLTree;

public class AVLTreeTest {

    private List<Integer> collect(Iterator<Integer> it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext()) {
            out.add(it.next());
        }
        return out;
    }

    @Test
    void llRotation_onInsert_producesBalancedShape() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);

        assertEquals(List.of(20, 10, 30), collect(avl.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(avl.inorderIterator()));
    }

    @Test
    void rrRotation_onInsert_producesBalancedShape() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);

        assertEquals(List.of(20, 10, 30), collect(avl.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(avl.inorderIterator()));
    }

    @Test
    void lrRotation_onInsert_producesBalancedShape() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(30);
        avl.insert(10);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), collect(avl.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(avl.inorderIterator()));
    }

    @Test
    void rlRotation_onInsert_producesBalancedShape() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(10);
        avl.insert(30);
        avl.insert(20);

        assertEquals(List.of(20, 10, 30), collect(avl.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(avl.inorderIterator()));
    }

    @Test
    void duplicateInsert_isIgnored() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(10);
        avl.insert(10);
        avl.insert(10);

        assertEquals(List.of(10), collect(avl.inorderIterator()));
    }

    @Test
    void contains_findMin_findMax_workAfterMixedInserts() {
        AVLTree<Integer> avl = new AVLTree<>();
        int[] values = { 50, 20, 70, 10, 30, 60, 80, 25, 65 };
        for (int v : values) {
            avl.insert(v);
        }

        assertTrue(avl.contains(25));
        assertTrue(avl.contains(80));
        assertFalse(avl.contains(999));
        assertEquals(10, avl.findMin());
        assertEquals(80, avl.findMax());
        assertEquals(List.of(10, 20, 25, 30, 50, 60, 65, 70, 80), collect(avl.inorderIterator()));
    }

    @Test
    void findMin_findMax_onEmptyTree_throw() {
        AVLTree<Integer> avl = new AVLTree<>();

        assertThrows(IllegalStateException.class, avl::findMin);
        assertThrows(IllegalStateException.class, avl::findMax);
    }

    @Test
    void remove_leaf_oneChild_twoChildren_keepInorderSorted() {
        AVLTree<Integer> avl = new AVLTree<>();
        int[] values = { 40, 20, 60, 10, 30, 50, 70, 25 };
        for (int v : values) {
            avl.insert(v);
        }

        avl.remove(25); // leaf
        avl.remove(30); // one child (after removing 25)
        avl.remove(60); // two children

        assertFalse(avl.contains(25));
        assertFalse(avl.contains(30));
        assertFalse(avl.contains(60));
        assertEquals(List.of(10, 20, 40, 50, 70), collect(avl.inorderIterator()));
    }

    @Test
    void remove_triggersRebalance_case() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(1);
        avl.insert(2);
        avl.insert(3);
        avl.insert(4);

        // Tree before removal is balanced with root 2 and subtree (3 -> 4).
        // Removing 1 makes root right-heavy and should rotate to root 3.
        avl.remove(1);

        assertEquals(List.of(3, 2, 4), collect(avl.levelorderIterator()));
        assertEquals(List.of(2, 3, 4), collect(avl.inorderIterator()));
    }

    @Test
    void remove_missingValue_doesNothing() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);

        avl.remove(999);

        assertEquals(List.of(5, 10, 15), collect(avl.inorderIterator()));
    }

    @Test
    void remove_untilEmptyTree() {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(2);
        avl.insert(1);
        avl.insert(3);

        avl.remove(2);
        avl.remove(1);
        avl.remove(3);

        assertFalse(avl.contains(1));
        assertFalse(avl.contains(2));
        assertFalse(avl.contains(3));
        assertEquals(List.of(), collect(avl.inorderIterator()));
    }
}
