import java.util.Scanner;

public class ConsoleHelper
{
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try
            {
                return Integer.parseInt(line.trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Неверный формат числа. Попробуйте ещё раз.");
            }
        }
    }

    public static void print(String s)
    {
        System.out.println(s);
    }
}
