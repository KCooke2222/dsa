class Solution(object):
    def judgeCircle(self, moves):
        """
        :type moves: str
        :rtype: bool
        """
        vert = 0
        horiz = 0

        for move in moves:
            if move == "U":
                vert += 1
            elif move == "D":
                vert -= 1
            elif move == "L":
                horiz -= 1
            elif move == "R":
                horiz += 1

        return vert == 0 and horiz == 0
