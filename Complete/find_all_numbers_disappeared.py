class Solution(object):
    def findDisappearedNumbers(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        n = len(nums)
        s = set(range(1, n + 1))
        for num in nums:
            s.discard(num)

        return list(s)