class Solution(object):
    def prefixCount(self, words, pref):
        """
        :type words: List[str]
        :type pref: str
        :rtype: int
        """
        # O(n) iterate overall all string pref check if match
        contains_pref = 0
        for s in words:
            matching = 0
            for i in range(len(pref)):
                if s[i] != pref[i]:
                    break
                else:
                    matching += 1

            if matching == len(pref):
                contains_pref += 1


        return contains_pref