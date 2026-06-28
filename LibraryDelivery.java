import java.util.*;

public class LibraryDelivery {

    static class Edge {
        int dest, weight;

        Edge(int d, int w) {
            dest = d;
            weight = w;
        }
    }

    static void dijkstra(List<List<Edge>> graph, int src) {

        int V = graph.size();

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[src] = 0;

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {

            int[] cur = pq.poll();
            int u = cur[0];

            for (Edge e : graph.get(u)) {

                if (dist[u] + e.weight < dist[e.dest]) {

                    dist[e.dest] = dist[u] + e.weight;

                    pq.add(new int[]{e.dest, dist[e.dest]});
                }
            }
        }

        System.out.println("Shortest Distances:");
        for (int i = 0; i < V; i++)
            System.out.println("Branch " + i + " = " + dist[i]);
    }

    public static void main(String[] args) {

        int V = 5;

        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        graph.get(0).add(new Edge(1, 4));
        graph.get(0).add(new Edge(2, 2));
        graph.get(1).add(new Edge(3, 5));
        graph.get(2).add(new Edge(3, 1));
        graph.get(3).add(new Edge(4, 3));

        dijkstra(graph, 0);
    }
}