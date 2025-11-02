public class Graph<V>
{
    private Object[] vertices;
    private int[][] adj;
    private int count;
    private boolean directed;

    public Graph(boolean directed)
    {
        this.directed = directed;
        this.vertices = new Object[10];
        this.adj = new int[10][10];
        this.count = 0;
    }

    public void addVertex(V v)
    {
        if (contains(v)) return;
        ensureCapacity();
        vertices[count++] = v;
    }

    public void addEdge(V from, V to, int weight)
    {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) throw new IllegalArgumentException("Vertex not found");
        adj[i][j] = weight;
        if (!directed) adj[j][i] = weight;
    }

    public void removeVertex(V v)
    {
        int idx = indexOf(v);
        if (idx == -1) return;
        for (int i = idx; i < count - 1; i++)
            vertices[i] = vertices[i + 1];
        vertices[count - 1] = null;
        for (int i = idx; i < count - 1; i++)
            for (int j = 0; j < count; j++)
                adj[i][j] = adj[i + 1][j];
        for (int j = 0; j < count - 1; j++)
            for (int i = 0; i < count; i++)
                adj[i][j] = adj[i][j + 1];
        for (int i = 0; i < count; i++)
            adj[i][count - 1] = 0;
        for (int j = 0; j < count; j++)
            adj[count - 1][j] = 0;
        count--;
    }

    public void removeEdge(V from, V to)
    {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) return;
        adj[i][j] = 0;
        if (!directed) adj[j][i] = 0;
    }

    public MyList<V> getAdjacent(V v)
    {
        int idx = indexOf(v);
        if (idx == -1) throw new IllegalArgumentException("Vertex not found");
        MyList<V> res = new MyList<>();
        for (int j = 0; j < count; j++)
        {
            if (adj[idx][j] != 0)
                res.add((V) vertices[j]);
        }
        return res;
    }

    public void dfs(V start)
    {
        int s = indexOf(start);
        if (s == -1) throw new IllegalArgumentException("Vertex not found");
        boolean[] visited = new boolean[count];
        dfsIndex(s, visited);
        System.out.println();
    }

    private void dfsIndex(int idx, boolean[] visited)
    {
        if (visited[idx]) return;
        System.out.print(vertices[idx] + " ");
        visited[idx] = true;
        for (int j = 0; j < count; j++)
            if (adj[idx][j] != 0)
                dfsIndex(j, visited);
    }

    public void bfs(V start)
    {
        int s = indexOf(start);
        if (s == -1) throw new IllegalArgumentException("Vertex not found");
        boolean[] visited = new boolean[count];
        MyQueue<Integer> q = new MyQueue<>();
        q.offer(s);
        visited[s] = true;
        while (!q.isEmpty())
        {
            Integer cur = q.poll();
            System.out.print(vertices[cur] + " ");
            for (int j = 0; j < count; j++)
            {
                if (adj[cur][j] != 0 && !visited[j])
                {
                    visited[j] = true;
                    q.offer(j);
                }
            }
        }
        System.out.println();
    }

    private int indexOf(V v)
    {
        for (int i = 0; i < count; i++)
        {
            if (vertices[i] == null ? v == null : vertices[i].equals(v))
                return i;
        }
        return -1;
    }

    private boolean contains(V v)
    {
        return indexOf(v) != -1;
    }

    private void ensureCapacity()
    {
        if (count < vertices.length) return;
        int newCap = vertices.length * 2;
        Object[] nv = new Object[newCap];
        int[][] na = new int[newCap][newCap];
        for (int i = 0; i < vertices.length; i++) nv[i] = vertices[i];
        for (int i = 0; i < vertices.length; i++)
            for (int j = 0; j < vertices.length; j++)
                na[i][j] = adj[i][j];
        vertices = nv;
        adj = na;
    }
}
