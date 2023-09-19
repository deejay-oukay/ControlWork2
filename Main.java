// пытался соответствовать этому (https://docs.google.com/document/d/1klyxcleapSjkQX_5uqq2anm_sSsuzofEUFufEJ0nAus/edit?usp=sharing) заданию, а не первоначальному

import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        MainMenu();
    }

    public static void errorCodes(int code, String moreInfo) {
        String[] codes = {
                /* 0 */ "Успешно",
                /* 1 */ "Игрушка с ID %s уже есть в магазине",
                /* 2 */ "Количество игрушек должно быть целым положительным числом, а не %s",
                /* 3 */ "Вес игрушек должен быть положительным числом, а не %s",
                /* 4 */ "Если указать для игрушки вес %s, то новый суммарный вес будет превышать максимально допустимый вес - 100",
                /* 5 */ "Введена некорретная команда: %s",
                /* 6 */ "Список игрушек пока пуст",
                /* 7 */ "Игрушка с ID %s в магазине не найдена",
                /* 8 */ "Суммарный вес игрушек должен быть 100, а не %s",
                /* 9 */ "Список призов пока пуст",
                /* 10 */"Возникли проблемы при записи в файл %s"
        };
        clearScreen();
        System.out.printf("!!!" + codes[code] + "!!!\n", moreInfo);
    }

    public static void errorCodes(int code) {
        errorCodes(code, "");
    }

    private static void MainMenu() {
        boolean need2stop = false;
        while (!need2stop) {
            System.out.println("list   - Показать список игрушек");
            System.out.println("add    - Добавить игрушки");
            System.out.println("change - Изменить вес игрушек");
            System.out.println("play   - Организовать розыгрыш");
            System.out.println("get    - Получить призовую игрушку");
            System.out.println("exit   - Выйти");
            System.out.print("Введите одну из перечисленных команд: ");
            String command = in.nextLine();
            if (command.equals("list")) {
                Shop.printList();
            } else if (command.equals("add")) {
                Shop.addToy();
            } else if (command.equals("change")) {
                Shop.changeWeight();
            } else if (command.equals("play")) {
                Shop.letsPlay();
            } else if (command.equals("get")) {
                Shop.getPrize();
            } else if (command.equals("exit")) {
                need2stop = true;
            } else
                errorCodes(5, command);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}