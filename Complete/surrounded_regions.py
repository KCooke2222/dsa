class Solution:
    def solve(self, board: List[List[str]]) -> None:
        rows, cols = len(board), len(board[0])

        def dfs(node):
            r, c = node

            if board[r][c] == 'X' or board[r][c] == '#':
                return

            if board[r][c] == 'O':
                board[r][c] = '#'
            
            for nr, nc in ((r-1, c), (r+1,c), (r,c-1), (r,c+1)):
                if 0 <= nr < rows and 0 <= nc < cols:
                    dfs((nr,nc))


        # dfs from outside
        for r in range(rows):
            dfs((r, 0))
            dfs((r, cols-1))
        
        for c in range(cols):
            dfs((0, c))
            dfs((rows-1, c))

        # flip Os and #s
        for r in range(rows):
            for c in range(cols):
                if board[r][c] == 'O':
                    board[r][c] = 'X'
                elif board[r][c] == '#':
                    board[r][c] = 'O'

        