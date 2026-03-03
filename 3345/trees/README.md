# Tree Implementation Outline (BinaryTree → BST → AVL → Splay)

## 0) Goal

- Practicing tree data structures
- Keep `BinaryTree` minimal (structure + traversal only)
- Put all ordering + mutation logic in `BST`
- `AVL` and `Splay` build on BST behavior

---

## 1) BinaryTree<T> (foundation: no ordering assumptions)

### Core

- `protected static class Node<T>`
  - `T data`
  - `Node<T> left, right`
- `protected Node<T> root`

### Basic utilities

- `boolean isEmpty()`
- `void clear()`
- `int size()` (optional; implement recursively or maintain a counter only if you need it)

### Traversal iterators

- `Iterator<T> iterator()` → default to `inorderIterator()` (standard default)
- `Iterator<T> inorderIterator()`
- `Iterator<T> preorderIterator()`
- `Iterator<T> postorderIterator()`
- `Iterator<T> levelOrderIterator()`

### Notes

- `Iterator.remove()` → `UnsupportedOperationException` for all iterators

### Do NOT include in BinaryTree

- `insert(T x)` / `remove(T x)` / `contains(T x)` (these imply ordering)
- rotations / heights / balancing

---

## 2) Expression Tree Build (postfix → BinaryTree)

Put in separate file/class (keeps BT clean).

### ExpressionTreeBuilder

- Input: postfix tokens (strings/chars)
- Uses `Deque<Node<String>>` (stack)

### Algorithm (postorder build from postfix)

- For each token:
  - if operand: push `new Node(token)`
  - if operator:
    - `right = pop()`
    - `left  = pop()`
    - `op = new Node(token)`
    - `op.left = left; op.right = right;`
    - push `op`
- End: `root = pop()`

### Output

- returns a `BinaryTree<String>` (or a dedicated `ExpressionTree` that extends `BinaryTree<String>`)

---

## 3) BST<T extends Comparable<? super T>> (ordered tree)

### Adds ordering

- generic bound: `T extends Comparable<? super T>`
- `protected int compare(T a, T b)` (centralize compare logic)

### Core ops

- `boolean contains(T x)`
- `void insert(T x)`
- `void remove(T x)` (optional depending on assignment)

### Invariants

- left < node < right (based on `compare`)

---

## 4) AVLTree<T ...> extends BST<T> (balanced BST)

### Adds balancing metadata

- Node type includes `height` (often via `AVLNode<T> extends Node<T>`)

### Overrides

- `insert/remove` to:
  - do normal BST op
  - update heights
  - rebalance with rotations

### Helpers

- `height(node)`, `balanceFactor(node)`
- rotations: `rotateLeft`, `rotateRight`, plus double rotations via composition

---

## 5) SplayTree<T ...> extends BST<T> (self-adjusting BST)

### Adds splay step

- After `contains`, `insert`, and sometimes `remove`
  - `splay(accessedNode)` to root

### Rotations

- zig, zig-zig, zig-zag patterns

---

## Recommended file layout

- `BinaryTree.java` (Node + iterators)
- `ExpressionTreeBuilder.java` (postfix build into Node wiring)
- `BST.java`
- `AVLTree.java`
- `SplayTree.java`
