import java.io.IOException;
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
            System.out.println("8 - Изменить вес ребра");
            System.out.println("9 - Показать матрицу смежности");
            System.out.println("10 - Сохранить граф в файл");
            System.out.println("11 - Загрузить граф из файла (заменить текущий)");
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
                        try
                        {
                            MyList<String> adj = graph.getAdjacent(adjV);
                            if (adj.size() == 0)
                                System.out.println("Смежные вершины: (нет)");
                            else
                            {
                                System.out.print("Смежные вершины: ");
                                for (int i = 0; i < adj.size(); i++)
                                {
                                    System.out.print(adj.get(i) + (i < adj.size() - 1 ? ", " : ""));
                                }
                                System.out.println();
                            }
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "6":
                        String startDFS = readVertex(sc, "Начальная вершина для DFS: ");
                        if (startDFS == null) break;
                        try
                        {
                            System.out.print("DFS: ");
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
                        try
                        {
                            System.out.print("BFS: ");
                            graph.bfs(startBFS);
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "8":
                        String uFrom = readVertex(sc, "Из вершины: ");
                        if (uFrom == null) break;
                        String uTo = readVertex(sc, "В вершину: ");
                        if (uTo == null) break;
                        System.out.print("Новый вес: ");
                        String nw = sc.nextLine().trim();
                        int newW;
                        try
                        {
                            newW = Integer.parseInt(nw);
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Ошибка: ожидалось целое число (вес).");
                            break;
                        }
                        if (newW == 0)
                        {
                            System.out.println("Ошибка: вес не может быть 0.");
                            break;
                        }
                        try
                        {
                            graph.updateEdge(uFrom, uTo, newW);
                            System.out.println("Вес ребра обновлён: " + uFrom + " -> " + uTo + " (" + newW + ")");
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "9":
                        System.out.println("Матрица смежности:");
                        System.out.println(graph.toString());
                        break;

                    case "10":
                        System.out.print("Путь для сохранения (Enter — data/graph.txt): ");
                        String savePath = sc.nextLine().trim();
                        if (savePath.isEmpty()) savePath = "data/graph.txt";
                        try
                        {
                            graph.saveToFile(savePath);
                            System.out.println("Граф сохранён в " + savePath);
                        }
                        catch (IOException e)
                        {
                            System.out.println("Ошибка записи файла: " + e.getMessage());
                        }
                        break;

                    case "11":
                        System.out.print("Путь для загрузки (Enter — data/graph.txt): ");
                        String loadPath = sc.nextLine().trim();
                        if (loadPath.isEmpty()) loadPath = "data/graph.txt";
                        try
                        {
                            Graph<String> loaded = Graph.loadFromFile(loadPath);
                            graph = loaded;
                            System.out.println("Граф загружен из " + loadPath);
                        }
                        catch (IOException e)
                        {
                            System.out.println("Ошибка загрузки файла: " + e.getMessage());
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
        return String.valueOf(Character.toUpperCase(c));
    }
}
