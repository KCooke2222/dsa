package lists;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class MyArrayListTest {

    // Helper: build a list with n integers: 0..n-1
    private MyArrayList<Integer> listOf(int n) {
        MyArrayList<Integer> l = new MyArrayList<>();
        for (int i = 0; i < n; i++) l.add(i);
        return l;
    }

    @Test
    void newList_isEmpty_sizeZero() {
        MyArrayList<Integer> l = new MyArrayList<>();
        assertTrue(l.isEmpty());
        assertEquals(0, l.size());
    }

    @Test
    void get_set_basic() {
        MyArrayList<String> l = new MyArrayList<>();
        l.add("a");
        l.add("b");
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));

        assertEquals("a", l.set(0, "x"));
        assertEquals("x", l.get(0));
    }

    @Test
    void get_outOfBounds_throws() {
        MyArrayList<Integer> l = new MyArrayList<>();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.get(0));

        l.add(1);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.get(1)); // idx==size is invalid
    }

    @Test
    void set_outOfBounds_throws() {
        MyArrayList<Integer> l = new MyArrayList<>();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.set(0, 9));

        l.add(1);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.set(-1, 9));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.set(1, 9)); // idx==size invalid
    }

    @Test
    void add_atIndex_shiftsRight() {
        MyArrayList<Integer> l = listOf(5); // [0,1,2,3,4]
        l.add(2, 99);                      // [0,1,99,2,3,4]
        assertEquals(6, l.size());
        assertEquals(0, l.get(0));
        assertEquals(1, l.get(1));
        assertEquals(99, l.get(2));
        assertEquals(2, l.get(3));
        assertEquals(4, l.get(5));
    }

    @Test
    void remove_atIndex_shiftsLeft() {
        MyArrayList<Integer> l = listOf(5); // [0,1,2,3,4]
        l.remove(1);                        // [0,2,3,4]
        assertEquals(4, l.size());
        assertEquals(0, l.get(0));
        assertEquals(2, l.get(1));
        assertEquals(4, l.get(3));
    }

    @Test
    void remove_outOfBounds_throws() {
        MyArrayList<Integer> l = listOf(3);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.remove(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> l.remove(3)); // idx==size invalid
    }

    @Test
    void clear_resetsSize_and_allowsReuse() {
        MyArrayList<Integer> l = listOf(8);
        l.clear();
        assertEquals(0, l.size());
        assertTrue(l.isEmpty());

        l.add(123);
        assertEquals(1, l.size());
        assertEquals(123, l.get(0));
    }

    @Test
    void contains_works_withSameReference() {
        MyArrayList<String> l = new MyArrayList<>();
        String a = new String("hi");
        String b = new String("hi");

        l.add(a);
        assertTrue(l.contains(a));
        assertFalse(l.contains(b)); // because your code uses == not .equals()
    }

    @Test
    void removeByValue_removesFirstMatchingReference() {
        MyArrayList<String> l = new MyArrayList<>();
        String x1 = new String("x");
        String x2 = new String("x");

        l.add(x1);
        l.add(x2);
        assertTrue(l.remove(x1));
        assertEquals(1, l.size());
        assertSame(x2, l.get(0));
        assertFalse(l.remove(x1));
    }

    @Test
    void iterator_traversesInOrder() {
        MyArrayList<Integer> l = listOf(4);
        Iterator<Integer> it = l.iterator();

        assertTrue(it.hasNext());
        assertEquals(0, it.next());
        assertEquals(1, it.next());
        assertEquals(2, it.next());
        assertEquals(3, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void iterator_remove_removesCurrentElement() {
        MyArrayList<Integer> l = listOf(4); // [0,1,2,3]
        Iterator<Integer> it = l.iterator();

        assertEquals(0, it.next());
        it.remove(); // removes 0
        assertEquals(3, l.size());
        assertEquals(1, l.get(0));

        assertEquals(1, it.next());
        it.remove(); // removes 1
        assertEquals(2, l.size());
        assertEquals(2, l.get(0));
    }

    @Test
    void growsPastDefaultCapacity_byAddingMany() {
        MyArrayList<Integer> l = new MyArrayList<>();
        for (int i = 0; i < 100; i++) l.add(i);
        assertEquals(100, l.size());
        assertEquals(0, l.get(0));
        assertEquals(99, l.get(99));
    }
}
