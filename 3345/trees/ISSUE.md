# Tree Implementation Notes (For Real Assignment Cleanup)

These are not urgent for current practice use, but should be fixed before submitting a final/graded version.

## Main Recommendation
- Remove `size` from `BinaryTree` (or fully maintain it everywhere).
- Remove `getRoot()` exposure from `BinaryTree` (it leaks mutable internals).

## Why

### 1) `size` is currently inconsistent
- `insert` and `remove` in `BinarySearchTree` / `AVLTree` do not update `size`.
- `clear()` does not reset `size`.
- Result: `size()` and `isEmpty()` can be wrong.

If `size` is not required for the assignment, deleting it is simplest.
If required, all mutation paths must maintain it correctly.

### 2) `getRoot()` leaks internal mutable structure
- Returning internal `Node` allows external code to mutate `left/right/data` directly.
- That can silently break BST/AVL invariants.

For assignment-quality code, avoid exposing mutable root nodes.

## Secondary Note
- Null key behavior is unspecified (`insert`, `remove`, `contains` will NPE on null).
- Optional improvement: explicitly reject null with `IllegalArgumentException`.

## Suggested Priority (real assignment)
1. Remove `getRoot()`.
2. Remove `size` fields/methods if not required.
3. If keeping `size`, implement correct updates in all constructors and mutation methods.
4. Add tests for any kept `size` behavior.
