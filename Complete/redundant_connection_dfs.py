class Solution:
    def findRedundantConnection(self, edges: List[List[int]]) -> List[int]:
        # n is the number of nodes as well as edges
        n = len(edges)
        adj = {i: [] for i in range(1, n+1)}

        # detect cycle
        visit = {}
        def dfs(node, parent):
            if node in visit:
                return True

            visit[node] = 1

            cycle = False
            for nei in adj[node]:
                if nei == parent:
                    continue
                cycle = cycle or dfs(nei, node)

            return cycle



        for a, b in edges:
            adj[a].append(b)
            adj[b].append(a)
            visit = {}
            if dfs(a, b):
                return [a, b]
                
        return False




            