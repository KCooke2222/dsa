class Solution:
    def isMatch(self, s: str, p: str) -> bool:
        n, m = len(s), len(p)
        dp = {}

        def dfs(i, j):
            if (i, j) in dp:
                return dp[(i, j)]
            
            if j == m: # reach end of regex we must be done
                return i == n

            first_match = i < n and (s[i] == p[j] or p[j] == '.')
            if j+1 < m and p[j+1] == "*": # * case
                res = dfs(i, j + 2) or (first_match and dfs(i + 1, j)) # skip or use if first match
            else:
                res = first_match and dfs(i + 1, j + 1) # normal if first match


            dp[(i, j)] = res
            return res

        return dfs(0, 0)