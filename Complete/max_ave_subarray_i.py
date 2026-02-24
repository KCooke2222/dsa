class Solution(object):
    def findMaxAverage(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: float
        """
        # use l r to check all arrays of size k done

        l, r = 0, 0
        cur_sum = 0
        max_sum = 0

        while (r < len(nums)):
            cur_sum += nums[r]
            if (r - l + 1 < k):
                r += 1
            else:
                max_sum = max(max_sum, cur_sum)
                cur_sum -= nums[l]
                l += 1
                r += 1
                

        return float(max_sum) / k
        