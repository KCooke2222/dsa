class Solution:
    def networkDelayTime(self, times: List[List[int]], n: int, k: int) -> int:
        # build weighted adj list
        # run dijkstras alg from k
        # return max cost 

        # setup
        adj = defaultdict(list)
        for u, v, w in times:
            adj[u].append((v, w))

        dist = {i: float("inf") for i in range(1, n + 1)}
        dist[k] = 0

        pq = [(0, k)]


        # dijkstra w/ min heap
        while pq:
            d, node = heapq.heappop(pq)

            # skip previous entries
            if d > dist[node]:
                continue

            # update cost of neighbours
            for nei, w in adj[node]:
                if d + w < dist[nei]:
                    dist[nei] = d + w
                    # repeat on lowest cost neighour
                    heapq.heappush(pq, (dist[nei], nei)) 


        min_time = max(dist.values())
        return min_time if min_time != float("inf") else -1
            