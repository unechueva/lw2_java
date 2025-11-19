import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        if (weight == 0)
            throw new IllegalArgumentException("Вес ребра не может быть 0");
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1)
            throw new IllegalArgumentException("Vertex not found");
        if (adj[i][j] != 0)
            throw new IllegalArgumentException("Edge already exists");
        adj[i][j] = weight;
        if (!directed) adj[j][i] = weight;
    }

    public void updateEdge(V from, V to, int newWeight)
    {
        if (newWeight == 0)
            throw new IllegalArgumentException("Вес ребра не может быть 0");
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1)
            throw new IllegalArgumentException("Vertex not found");
        if (adj[i][j] == 0)
            throw new IllegalArgumentException("Edge does not exist");
        adj[i][j] = newWeight;
        if (!directed) adj[j][i] = newWeight;
    }

    public void removeVertex(V v)
    {
        int idx = indexOf(v);
        if (idx == -1) return;

        int newCount = count - 1;
        int size = Math.max(10, newCount);
        Object[] newVertices = new Object[size];
        int[][] newAdj = new int[size][size];

        int ti = 0;
        for (int i = 0; i < count; i++)
        {
            if (i == idx) continue;
            newVertices[ti++] = vertices[i];
        }

        for (int i = 0, ni = 0; i < count; i++)
        {
            if (i == idx) continue;
            for (int j = 0, nj = 0; j < count; j++)
            {
                if (j == idx) continue;
                newAdj[ni][nj] = adj[i][j];
                nj++;
            }
            ni++;
        }

        vertices = newVertices;
        adj = newAdj;
        count = newCount;
    }

    public void removeEdge(V from, V to)
    {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) return;
        adj[i][j] = 0;
        if (!directed) adj[j][i] = 0;
    }

    @SuppressWarnings("unchecked")
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
        {
            if (adj[idx][j] != 0)
                dfsIndex(j, visited);
        }
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int i = 0; i < count; i++)
        {
            sb.append(String.valueOf(vertices[i])).append(" ");
        }
        sb.append(System.lineSeparator());

        for (int i = 0; i < count; i++)
        {
            sb.append(String.valueOf(vertices[i])).append("   ");
            for (int j = 0; j < count; j++)
            {
                sb.append(adj[i][j]).append("   ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void saveToFile(String path) throws IOException
    {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(path)))
        {
            w.write("directed:" + directed);
            w.newLine();
            w.write("# vertices");
            w.newLine();
            for (int i = 0; i < count; i++)
            {
                w.write(String.valueOf(vertices[i]));
                w.newLine();
            }
            w.write("# edges");
            w.newLine();
            for (int i = 0; i < count; i++)
                for (int j = 0; j < count; j++)
                    if (adj[i][j] != 0)
                        w.write(String.valueOf(vertices[i]) + " " + String.valueOf(vertices[j]) + " " + adj[i][j] + System.lineSeparator());
        }
    }

    public static Graph<String> loadFromFile(String path) throws IOException
    {
        try (BufferedReader r = new BufferedReader(new FileReader(path)))
        {
            String line;
            boolean directedFlag = false;
            MyList<String> verts = new MyList<>();
            MyList<String> edges = new MyList<>();
            boolean stageVertices = false;
            boolean stageEdges = false;
            while ((line = r.readLine()) != null)
            {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("#"))
                {
                    if (line.toLowerCase().contains("vertices")) { stageVertices = true; stageEdges = false; }
                    if (line.toLowerCase().contains("edges")) { stageEdges = true; stageVertices = false; }
                    continue;
                }
                if (line.toLowerCase().startsWith("directed:"))
                {
                    String val = line.substring(line.indexOf(':') + 1).trim();
                    directedFlag = Boolean.parseBoolean(val);
                    continue;
                }
                if (stageVertices)
                {
                    verts.add(line);
                    continue;
                }
                if (stageEdges)
                {
                    edges.add(line);
                    continue;
                }
                if (!stageVertices && !stageEdges)
                {
                    verts.add(line);
                }
            }

            Graph<String> g = new Graph<>(directedFlag);
            for (int i = 0; i < verts.size(); i++)
            {
                g.addVertex(verts.get(i));
            }
            for (int i = 0; i < edges.size(); i++)
            {
                String e = edges.get(i);
                String[] parts = e.split("\\s+");
                if (parts.length < 3) continue;
                String from = parts[0];
                String to = parts[1];
                int w;
                try
                {
                    w = Integer.parseInt(parts[2]);
                }
                catch (NumberFormatException ex)
                {
                    throw new IOException("Invalid weight in file: " + e);
                }
                g.addEdge(from, to, w);
            }
            return g;
        }
    }
}
