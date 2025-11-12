import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ориентированный граф? (true/false): ");
        boolean directed = Boolean.parseBoolean(sc.nextLine().trim());
        Graph<String> graph = new Graph<>(directed);

        while (true)
        {
            System.out.println();
            System.out.println("Меню:");
            System.out.println("1 - Добавить вершину");
            System.out.println("2 - Добавить ребро");
            System.out.println("3 - Удалить вершину");
            System.out.println("4 - Удалить ребро");
            System.out.println("5 - Показать смежные вершины");
            System.out.println("6 - DFS (обход в глубину)");
            System.out.println("7 - BFS (обход в ширину)");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("0"))
            {
                System.out.println("Программа завершена.");
                break;
            }

            try
            {
                switch (choice)
                {
                    case "1":
                        String v = readVertex(sc, "Введите вершину: ");
                        if (v == null) break;
                        graph.addVertex(v);
                        System.out.println("Вершина добавлена: " + v);
                        break;

                    case "2":
                        String from = readVertex(sc, "Из вершины: ");
                        if (from == null) break;
                        String to = readVertex(sc, "В вершину: ");
                        if (to == null) break;
                        System.out.print("Вес: ");
                        String wStr = sc.nextLine().trim();
                        int weight;
                        try
                        {
                            weight = Integer.parseInt(wStr);
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Ошибка: ожидалось целое число (вес).");
                            break;
                        }
                        if (weight == 0)
                        {
                            System.out.println("Ошибка: вес не может быть 0.");
                            break;
                        }
                        try
                        {
                            graph.addEdge(from, to, weight);
                            System.out.println("Ребро добавлено: " + from + " -> " + to + " (вес " + weight + ")");
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "3":
                        String delV = readVertex(sc, "Введите вершину для удаления: ");
                        if (delV == null) break;
                        graph.removeVertex(delV);
                        System.out.println("Вершина удалена: " + delV);
                        break;

                    case "4":
                        String delFrom = readVertex(sc, "Из вершины: ");
                        if (delFrom == null) break;
                        String delTo = readVertex(sc, "В вершину: ");
                        if (delTo == null) break;
                        graph.removeEdge(delFrom, delTo);
                        System.out.println("Ребро удалено: " + delFrom + " -> " + delTo);
                        break;

                    case "5":
                        String adjV = readVertex(sc, "Введите вершину для показа смежных: ");
                        if (adjV == null) break;
                        System.out.print("Смежные вершины: ");
                        MyList<String> adj = graph.getAdjacent(adjV);
                        if (adj.size() == 0)
                        {
                            System.out.print("Нет смежных вершин");
                        }
                        else
                        {
                            for (int i = 0; i < adj.size(); i++)
                            {
                                System.out.print(adj.get(i) + (i < adj.size() - 1 ? ", " : ""));
                            }
                        }
                        System.out.println();
                        break;

                    case "6":
                        String startDFS = readVertex(sc, "Начальная вершина для DFS: ");
                        if (startDFS == null) break;
                        System.out.print("DFS: ");
                        try
                        {
                            graph.dfs(startDFS);
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "7":
                        String startBFS = readVertex(sc, "Начальная вершина для BFS: ");
                        if (startBFS == null) break;
                        System.out.print("BFS: ");
                        try
                        {
                            graph.bfs(startBFS);
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Неверный выбор.");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private static String readVertex(Scanner sc, String prompt)
    {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty())
        {
            System.out.println("Отмена: пустая строка.");
            return null;
        }
        if (line.length() != 1)
        {
            System.out.println("Неверный формат: введите ровно одну букву (A-Z).");
            return null;
        }
        char c = line.charAt(0);
        if (!Character.isLetter(c))
        {
            System.out.println("Неверный формат: имя вершины должно быть буквой (A-Z).");
            return null;
        }
        String res = String.valueOf(Character.toUpperCase(c));
        return res;
    }
}
