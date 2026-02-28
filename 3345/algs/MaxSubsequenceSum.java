package algs;
// divide and conquer solution O(nlogn)
private static int maxSumRec( int [] a, int l, int r) {
    // base
    if (l == r) {
        return a[l];
    }

    int totalMax = Integer.MIN_VALUE;
    int center = (l + r) / 2;

    // left
    totalMax = Math.max(totalMax, maxSumRec(a, l, center));

    // right
    totalMax = Math.max(totalMax, maxSumRec(a, center + 1, r));

    // across
    int maxLeft = Integer.MIN_VALUE;
    int leftTotal = 0;
    for (int i = center; i >= l; i--) {
        leftTotal += a[i];
        maxLeft = Math.max(maxLeft, leftTotal);
    }

    int maxRight = Integer.MIN_VALUE;
    int rightTotal = 0;
    for (int i = center + 1; i <= r; i++) {
        rightTotal += a[i];
        maxRight = Math.max(maxRight, rightTotal);
    }

    return Math.max(totalMax, maxLeft + maxRight);

}