import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean oriented = askOriented(sc);
        Graph<String> g = new Graph<>(oriented);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Добавить вершину");
            System.out.println("2 - Добавить ребро");
            System.out.println("3 - Удалить вершину");
            System.out.println("4 - Удалить ребро");
            System.out.println("5 - Показать смежные вершины");
            System.out.println("6 - DFS");
            System.out.println("7 - BFS");
            System.out.println("8 - Сохранить граф");
            System.out.println("9 - Загрузить граф");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1": {
                    System.out.print("Введите вершину: ");
                    String v = readNewVertex(sc, g);
                    if (v != null) {
                        g.addVertex(v);
                        System.out.println("Добавлена вершина " + v);
                    }
                    break;
                }
                case "2": {
                    System.out.print("Начальная вершина: ");
                    String from = readExistingVertexOrExit(sc, g);
                    if (from == null) break;
                    System.out.print("Конечная вершина: ");
                    String to = readExistingVertexOrExit(sc, g);
                    if (to == null) break;
                    System.out.print("Вес (>0): ");
                    int w = readWeightOrExit(sc);
                    if (w <= 0) break;
                    if (g.hasEdge(from, to)) {
                        int existW = g.getEdgeWeight(from, to);
                        System.out.println("Ошибка: ребро между этими вершинами уже существует (вес: " + existW + ")");
                        break;
                    }
                    boolean added = g.addEdge(from, to, w);
                    if (added) System.out.println("Добавлено ребро " + from + " -> " + to + " (вес " + w + ")");
                    else System.out.println("Ошибка: не удалось добавить ребро.");
                    break;
                }
                case "3": {
                    System.out.print("Введите вершину: ");
                    String rv = readExistingVertexOrExit(sc, g);
                    if (rv == null) break;
                    boolean rem = g.removeVertex(rv);
                    if (rem) System.out.println("Вершина " + rv + " удалена");
                    else System.out.println("Ошибка: не удалось удалить вершину");
                    break;
                }
                case "4": {
                    System.out.print("Вершина начала: ");
                    String ef = readExistingVertexOrExit(sc, g);
                    if (ef == null) break;
                    System.out.print("Вершина конца: ");
                    String et = readExistingVertexOrExit(sc, g);
                    if (et == null) break;
                    boolean r = g.removeEdge(ef, et);
                    if (r) System.out.println("Ребро " + ef + " -> " + et + " удалено");
                    else System.out.println("Ошибка: ребро не найдено");
                    break;
                }
                case "5": {
                    System.out.print("Введите вершину: ");
                    String av = readExistingVertexOrExit(sc, g);
                    if (av == null) break;
                    MyList<String> adj = g.getAdjacent(av);
                    if (adj.size() == 0) System.out.println("У этой вершины нет смежных.");
                    else {
                        System.out.print("Смежные вершины: ");
                        for (int i = 0; i < adj.size(); i++) {
                            System.out.print(adj.get(i) + (i < adj.size()-1 ? ", " : ""));
                        }
                        System.out.println();
                    }
                    break;
                }
                case "6": {
                    System.out.print("Начальная вершина DFS: ");
                    String dv = readExistingVertexOrExit(sc, g);
                    if (dv == null) break;
                    System.out.print("DFS: ");
                    g.dfs(dv);
                    break;
                }
                case "7": {
                    System.out.print("Начальная вершина BFS: ");
                    String bv = readExistingVertexOrExit(sc, g);
                    if (bv == null) break;
                    System.out.print("BFS: ");
                    g.bfs(bv);
                    break;
                }
                case "8": {
                    System.out.print("Имя файла: ");
                    String fn = sc.nextLine().trim();
                    g.saveToFile(fn);
                    System.out.println("Граф сохранён");
                    break;
                }
                case "9": {
                    System.out.print("Имя файла: ");
                    String fn2 = sc.nextLine().trim();
                    g.loadFromFile(fn2);
                    System.out.println("Граф загружен");
                    break;
                }
                default:
                    System.out.println("Неверный ввод");
            }
        }
    }

    private static boolean askOriented(Scanner sc) {
        System.out.print("Граф ориентированный? (yes/no): ");
        String a = sc.nextLine().trim().toLowerCase();
        boolean res = a.equals("y") || a.equals("yes") || a.equals("да") || a.equals("д");
        if (res) System.out.println("Выбран: ориентированный граф");
        else System.out.println("Выбран: неориентированный граф");
        return res;
    }

    private static String readNewVertex(Scanner sc, Graph<String> g) {
        String s = sc.nextLine().trim().toUpperCase();
        if (!s.matches("[A-Z]")) {
            System.out.println("Ошибка: требуется одна буква (A–Z)");
            return null;
        }
        if (g.containsVertex(s)) {
            System.out.println("Ошибка: вершина " + s + " уже существует");
            return null;
        }
        return s;
    }

    private static String readExistingVertexOrExit(Scanner sc, Graph<String> g) {
        String s = sc.nextLine().trim().toUpperCase();
        if (!s.matches("[A-Z]")) {
            System.out.println("Ошибка: требуется одна буква (A–Z)");
            return null;
        }
        if (!g.containsVertex(s)) {
            System.out.println("Ошибка: такой вершины не существует");
            return null;
        }
        return s;
    }

    private static int readWeightOrExit(Scanner sc) {
        String s = sc.nextLine().trim();
        try {
            int w = Integer.parseInt(s);
            if (w <= 0) {
                System.out.println("Ошибка: вес должен быть > 0");
                return -1;
            }
            return w;
        } catch (Exception e) {
            System.out.println("Ошибка: вес должен быть целым числом");
            return -1;
        }
    }
}
