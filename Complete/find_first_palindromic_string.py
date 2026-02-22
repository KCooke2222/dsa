class Solution(object):
    def firstPalindrome(self, words):
        """
        :type words: List[str]
        :rtype: str
        """
        for word in words:
            palindromic = True
            for i in range(len(word) // 2):
                if word[i] != word[len(word) - 1 - i]:
                    palindromic = False
                    break
            if palindromic:
                return word
        return ""