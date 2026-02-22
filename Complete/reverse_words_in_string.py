class Solution(object):
    def reverseWords(self, s):
        """
        :type s: str
        :rtype: str
        """
        
        words = s.split()

        reversed_words = []
        for word in words:
            reversed_words.append(word[::-1])
        
        return " ".join(reversed_words)