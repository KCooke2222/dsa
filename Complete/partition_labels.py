class Solution:
    def partitionLabels(self, s: str) -> List[int]:
        last = {}

        # get last char
        for i, c in reversed(list(enumerate(s))):
            if c not in last:
                last[c] = i

        end = 0
        res = []
        cur_length = 0
        for i, c in enumerate(s):
            cur_length += 1
            end = max(end, last[c])

            if end == i:
                res.append(cur_length)
                cur_length = 0

        return res
        

        