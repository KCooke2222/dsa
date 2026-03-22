class Solution(object):
    def intToRoman(self, num):
        """
        :type num: int
        :rtype: str
        """
        # using list of numeral tuples high to low
        # for each find how many go into the number
        # add these to res, subtract from num


        numerals = [            
            ("M", 1000),
            ("CM", 900),
            ("D", 500),
            ("CD", 400),
            ("C", 100),
            ("XC", 90),
            ("L", 50),
            ("XL", 40),
            ("X", 10),
            ("IX", 9),
            ("V", 5),
            ("IV", 4),
            ("I", 1)
        ]

        res = []
        for k, v in numerals:
            count = num // v
            res.append(k * count)
            num -= v * count
            
        return "".join(res)