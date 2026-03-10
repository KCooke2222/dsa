package trees.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import trees.SplayTree;

public class SplayTreeTest {

    private List<Integer> collect(Iterator<Integer> it) {
        List<Integer> out = new ArrayList<>();
        while (it.hasNext()) {
            out.add(it.next());
        }
        return out;
    }

    // -------------------------------------------------------------------------
    // Zig cases — node is direct child of root
    // -------------------------------------------------------------------------

    @Test
    void zig_right_accessedNodeBecomesRoot() {
        // BST: 10 -> 20 (right)
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(10);
        t.insert(20);

        assertTrue(t.contains(20));

        // 20 becomes root, 10 hangs left
        assertEquals(List.of(20, 10), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20), collect(t.inorderIterator()));
    }

    @Test
    void zig_left_accessedNodeBecomesRoot() {
        // BST: 20, left child 10
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(20);
        t.insert(10);

        assertTrue(t.contains(10));

        // 10 becomes root, 20 hangs right
        assertEquals(List.of(10, 20), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20), collect(t.inorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Zig-zig cases — two rotations same direction
    // -------------------------------------------------------------------------

    @Test
    void zigZig_right_accessedNodeBecomesRoot() {
        // BST (right-skewed): 10 -> 20 -> 30
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(10);
        t.insert(20);
        t.insert(30);

        assertTrue(t.contains(30));

        // after right zig-zig: 30 is root, 20 left of 30, 10 left of 20
        assertEquals(List.of(30, 20, 10), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(t.inorderIterator()));
    }

    @Test
    void zigZig_left_accessedNodeBecomesRoot() {
        // BST (left-skewed): 30 -> 20 -> 10
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(30);
        t.insert(20);
        t.insert(10);

        assertTrue(t.contains(10));

        // after left zig-zig: 10 is root, 20 right, 30 right of 20
        assertEquals(List.of(10, 20, 30), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(t.inorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Zig-zag cases — two rotations opposite direction
    // -------------------------------------------------------------------------

    @Test
    void zigZag_rightLeft_accessedNodeBecomesRoot() {
        // BST: root=10, 10.right=30, 30.left=20
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(10);
        t.insert(30);
        t.insert(20);

        assertTrue(t.contains(20));

        // after right-left zig-zag: 20 is root with 10 left and 30 right
        assertEquals(List.of(20, 10, 30), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(t.inorderIterator()));
    }

    @Test
    void zigZag_leftRight_accessedNodeBecomesRoot() {
        // BST: root=30, 30.left=10, 10.right=20
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(30);
        t.insert(10);
        t.insert(20);

        assertTrue(t.contains(20));

        // after left-right zig-zag: 20 is root with 10 left and 30 right
        assertEquals(List.of(20, 10, 30), collect(t.levelorderIterator()));
        assertEquals(List.of(10, 20, 30), collect(t.inorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Deep splay — multiple zig-zig steps
    // -------------------------------------------------------------------------

    @Test
    void deepSplay_rightSkewed_deepestNodeBecomesRoot() {
        // right-skewed BST: 1->2->3->4->5
        SplayTree<Integer> t = new SplayTree<>();
        for (int i = 1; i <= 5; i++) {
            t.insert(i);
        }

        assertTrue(t.contains(5));

        // root must be 5, inorder must still be sorted
        assertEquals(5, collect(t.levelorderIterator()).get(0));
        assertEquals(List.of(1, 2, 3, 4, 5), collect(t.inorderIterator()));
    }

    @Test
    void deepSplay_leftSkewed_deepestNodeBecomesRoot() {
        // left-skewed BST: 5->4->3->2->1
        SplayTree<Integer> t = new SplayTree<>();
        for (int i = 5; i >= 1; i--) {
            t.insert(i);
        }

        assertTrue(t.contains(1));

        assertEquals(1, collect(t.levelorderIterator()).get(0));
        assertEquals(List.of(1, 2, 3, 4, 5), collect(t.inorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Correctness: missing key must not splay, root unchanged
    // -------------------------------------------------------------------------

    @Test
    void containsMissing_returnsFalse_rootUnchanged() {
        // BST (right-skewed): root=10
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(10);
        t.insert(20);
        t.insert(30);

        assertFalse(t.contains(999));

        // root should still be 10 (nothing splayed)
        assertEquals(List.of(10, 20, 30), collect(t.levelorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Correctness: accessing current root is a no-op
    // -------------------------------------------------------------------------

    @Test
    void containsRoot_noStructureChange() {
        SplayTree<Integer> t = new SplayTree<>();
        t.insert(10);
        t.insert(20);
        t.insert(30);

        // root is 10 (right-skewed BST)
        assertTrue(t.contains(10));

        // root unchanged, structure unchanged
        assertEquals(List.of(10, 20, 30), collect(t.levelorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Correctness: sequential accesses always produce sorted inorder
    // -------------------------------------------------------------------------

    @Test
    void multipleAccesses_inorderAlwaysSorted() {
        SplayTree<Integer> t = new SplayTree<>();
        int[] values = { 50, 20, 70, 10, 30, 60, 80 };
        for (int v : values) {
            t.insert(v);
        }

        List<Integer> sorted = List.of(10, 20, 30, 50, 60, 70, 80);

        t.contains(10);
        assertEquals(sorted, collect(t.inorderIterator()));

        t.contains(80);
        assertEquals(sorted, collect(t.inorderIterator()));

        t.contains(50);
        assertEquals(sorted, collect(t.inorderIterator()));
    }

    // -------------------------------------------------------------------------
    // Correctness: recently accessed node is at root (temporal locality)
    // -------------------------------------------------------------------------

    @Test
    void recentAccessIsAtRoot() {
        SplayTree<Integer> t = new SplayTree<>();
        for (int v : new int[]{ 10, 20, 30, 40, 50 }) {
            t.insert(v);
        }

        t.contains(20);
        assertEquals(20, collect(t.levelorderIterator()).get(0));

        t.contains(50);
        assertEquals(50, collect(t.levelorderIterator()).get(0));

        t.contains(10);
        assertEquals(10, collect(t.levelorderIterator()).get(0));
    }

    // -------------------------------------------------------------------------
    // Correctness: contains returns right values
    // -------------------------------------------------------------------------

    @Test
    void contains_correctTrueFalseResults() {
        SplayTree<Integer> t = new SplayTree<>();
        for (int v : new int[]{ 5, 3, 7, 1, 9 }) {
            t.insert(v);
        }

        assertTrue(t.contains(5));
        assertTrue(t.contains(1));
        assertTrue(t.contains(9));
        assertFalse(t.contains(0));
        assertFalse(t.contains(4));
        assertFalse(t.contains(100));
    }

    // -------------------------------------------------------------------------
    // Correctness: BST remove still works after splay
    // -------------------------------------------------------------------------

    @Test
    void removeAfterSplay_inorderStillSorted() {
        SplayTree<Integer> t = new SplayTree<>();
        for (int v : new int[]{ 10, 5, 15, 3, 7 }) {
            t.insert(v);
        }

        t.contains(5); // splay 5 to root
        t.remove(5);

        assertFalse(t.contains(5));
        assertEquals(List.of(3, 7, 10, 15), collect(t.inorderIterator()));
    }
}
