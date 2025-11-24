import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Graph<V> {
    private MyList<V> vertices = new MyList<>();
    private MyList<MyList<Edge<V>>> edges = new MyList<>();
    private boolean oriented;

    private static class Edge<V> {
        V to;
        int weight;
        Edge(V t, int w) { to = t; weight = w; }
    }

    public Graph() { this.oriented = false; }

    public Graph(boolean oriented) { this.oriented = oriented; }

    public boolean isOriented() { return oriented; }

    public void setOriented(boolean oriented) { this.oriented = oriented; }

    public boolean containsVertex(V v) { return indexOf(v) != -1; }

    public boolean addVertex(V v) {
        if (v == null) return false;
        if (containsVertex(v)) return false;
        vertices.add(v);
        edges.add(new MyList<>());
        return true;
    }

    public boolean removeVertex(V v) {
        int idx = indexOf(v);
        if (idx == -1) return false;
        vertices.remove(idx);
        edges.remove(idx);
        for (int i = 0; i < edges.size(); i++) {
            MyList<Edge<V>> row = edges.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).to.equals(v)) {
                    row.removeAt(j);
                    j--;
                }
            }
        }
        return true;
    }

    public boolean addEdge(V from, V to, int weight) {
        if (weight <= 0) return false;
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) return false;
        MyList<Edge<V>> row = edges.get(i);
        if (edgeExists(row, to)) return false;
        row.add(new Edge<>(to, weight));
        if (!oriented) {
            MyList<Edge<V>> row2 = edges.get(j);
            if (!edgeExists(row2, from)) row2.add(new Edge<>(from, weight));
        }
        return true;
    }

    public boolean removeEdge(V from, V to) {
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) return false;
        MyList<Edge<V>> row = edges.get(i);
        boolean removed = false;
        for (int k = 0; k < row.size(); k++) {
            if (row.get(k).to.equals(to)) {
                row.removeAt(k);
                removed = true;
                break;
            }
        }
        if (!oriented) {
            MyList<Edge<V>> row2 = edges.get(j);
            for (int k = 0; k < row2.size(); k++) {
                if (row2.get(k).to.equals(from)) {
                    row2.removeAt(k);
                    break;
                }
            }
        }
        return removed;
    }

    public MyList<V> getAdjacent(V v) {
        MyList<V> res = new MyList<>();
        int i = indexOf(v);
        if (i == -1) return res;
        MyList<Edge<V>> row = edges.get(i);
        for (int k = 0; k < row.size(); k++) res.add(row.get(k).to);

        if (!oriented) {
            for (int j = 0; j < edges.size(); j++) {
                if (j == i) continue;
                MyList<Edge<V>> row2 = edges.get(j);
                for (int k = 0; k < row2.size(); k++) {
                    if (row2.get(k).to.equals(v) && !res.contains(vertices.get(j))) {
                        res.add(vertices.get(j));
                    }
                }
            }
        }

        MyList<V> sorted = new MyList<>();
        for (int idx = 0; idx < vertices.size(); idx++) {
            if (res.contains(vertices.get(idx))) sorted.add(vertices.get(idx));
        }
        return sorted;
    }

    public boolean hasEdge(V from, V to) {
        int i = indexOf(from);
        if (i == -1) return false;
        return edgeExists(edges.get(i), to);
    }

    public int getEdgeWeight(V from, V to) {
        int i = indexOf(from);
        if (i == -1) return -1;
        MyList<Edge<V>> row = edges.get(i);
        for (int k = 0; k < row.size(); k++) {
            if (row.get(k).to.equals(to)) return row.get(k).weight;
        }
        return -1;
    }

    private boolean edgeExists(MyList<Edge<V>> list, V to) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).to.equals(to)) return true;
        }
        return false;
    }

    public void dfs(V start) {
        int s = indexOf(start);
        if (s == -1) {
            System.out.println("Ошибка: вершина не найдена.");
            return;
        }
        boolean[] visited = new boolean[vertices.size()];
        MyStack<Integer> st = new MyStack<>();
        st.push(s);
        StringBuilder out = new StringBuilder();
        while (!st.isEmpty()) {
            int cur = st.pop();
            if (!visited[cur]) {
                visited[cur] = true;
                out.append(vertices.get(cur)).append(" ");
                MyList<V> adj = getAdjacent(vertices.get(cur));
                for (int i = adj.size() - 1; i >= 0; i--) {
                    int idx = indexOf(adj.get(i));
                    if (!visited[idx]) st.push(idx);
                }
            }
        }
        System.out.println(out.toString().trim());
    }

    public void bfs(V start) {
        int s = indexOf(start);
        if (s == -1) {
            System.out.println("Ошибка: вершина не найдена.");
            return;
        }
        boolean[] visited = new boolean[vertices.size()];
        MyQueue<Integer> q = new MyQueue<>();
        q.offer(s);
        visited[s] = true;
        StringBuilder out = new StringBuilder();
        while (!q.isEmpty()) {
            int cur = q.poll();
            out.append(vertices.get(cur)).append(" ");
            MyList<V> adj = getAdjacent(vertices.get(cur));
            for (int i = 0; i < adj.size(); i++) {
                int idx = indexOf(adj.get(i));
                if (!visited[idx]) {
                    visited[idx] = true;
                    q.offer(idx);
                }
            }
        }
        System.out.println(out.toString().trim());
    }

    private int indexOf(V v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(v)) return i;
        }
        return -1;
    }

    public void saveToFile(String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(oriented ? "1" : "0");
            out.println(vertices.size());
            for (int i = 0; i < vertices.size(); i++) out.println(vertices.get(i));
            for (int i = 0; i < edges.size(); i++) {
                MyList<Edge<V>> row = edges.get(i);
                for (int j = 0; j < row.size(); j++) {
                    Edge<V> e = row.get(j);
                    out.println(vertices.get(i) + " " + e.to + " " + e.weight);
                }
            }
        } catch (Exception ignored) {
            System.out.println("Ошибка при сохранении файла.");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            String dir = sc.nextLine().trim();
            this.oriented = dir.equals("1");
            vertices = new MyList<>();
            edges = new MyList<>();
            int vcount = Integer.parseInt(sc.nextLine().trim());
            for (int i = 0; i < vcount; i++) {
                V v = (V) sc.nextLine().trim();
                vertices.add(v);
                edges.add(new MyList<>());
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+");
                if (p.length < 3) continue;
                V from = (V) p[0];
                V to = (V) p[1];
                int w = Integer.parseInt(p[2]);
                addEdge(from, to, w);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке файла.");
        }
    }
}
