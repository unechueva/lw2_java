public class Main
{
    public static void main(String[] args)
    {
        Graph<String> graph = null;
        while (true)
        {
            ConsoleHelper.print("\nМеню:");
            ConsoleHelper.print("1 - Создать граф (ориентированный)");
            ConsoleHelper.print("2 - Создать граф (неориентированный)");
            ConsoleHelper.print("3 - Добавить вершину");
            ConsoleHelper.print("4 - Добавить ребро");
            ConsoleHelper.print("5 - Удалить вершину");
            ConsoleHelper.print("6 - Удалить ребро");
            ConsoleHelper.print("7 - Показать смежные вершины");
            ConsoleHelper.print("8 - DFS");
            ConsoleHelper.print("9 - BFS");
            ConsoleHelper.print("0 - Выход");
            int choice = ConsoleHelper.readInt("Выбор: ");
            switch (choice)
            {
                case 1:
                    graph = new Graph<>(true);
                    ConsoleHelper.print("Ориентированный граф создан.");
                    break;
                case 2:
                    graph = new Graph<>(false);
                    ConsoleHelper.print("Неориентированный граф создан.");
                    break;
                case 3:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String v = ConsoleHelper.readString("Имя вершины: ");
                    graph.addVertex(v);
                    ConsoleHelper.print("Вершина добавлена.");
                    break;
                case 4:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String from = ConsoleHelper.readString("От: ");
                    String to = ConsoleHelper.readString("К: ");
                    int w = ConsoleHelper.readInt("Вес (int): ");
                    graph.addEdge(from, to, w);
                    ConsoleHelper.print("Ребро добавлено.");
                    break;
                case 5:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String rv = ConsoleHelper.readString("Вершина для удаления: ");
                    graph.removeVertex(rv);
                    ConsoleHelper.print("Вершина удалена.");
                    break;
                case 6:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String rf = ConsoleHelper.readString("От: ");
                    String rt = ConsoleHelper.readString("К: ");
                    graph.removeEdge(rf, rt);
                    ConsoleHelper.print("Ребро удалено.");
                    break;
                case 7:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String gv = ConsoleHelper.readString("Вершина: ");
                    MyList<String> adj = graph.getAdjacent(gv);
                    ConsoleHelper.print("Смежные: " + adj.toString());
                    break;
                case 8:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String startD = ConsoleHelper.readString("Старт (для DFS): ");
                    graph.dfs(startD);
                    break;
                case 9:
                    if (graph == null) { ConsoleHelper.print("Сначала создайте граф."); break; }
                    String startB = ConsoleHelper.readString("Старт (для BFS): ");
                    graph.bfs(startB);
                    break;
                case 0:
                    ConsoleHelper.print("Выход.");
                    return;
                default:
                    ConsoleHelper.print("Неверный выбор.");
                    break;
            }
        }
    }
}
