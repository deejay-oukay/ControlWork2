import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Shop {
    // список игрушек в магазине
    private static ArrayList<Toy> list = new ArrayList<>();
    // суммарный вес всех игрушек
    private static Double summaryWeight = 0.0;
    // список игрушек для выдачи
    private static ArrayList<Toy> prizes = new ArrayList<>();

    // файл с данными о выданных призах
    private static String file = "won.txt";

    public static Double getSummaryWeight() {
        return summaryWeight;
    }

    public static void increaseSummaryWeight(Double weightPlus) {
        Shop.summaryWeight += weightPlus;
    }

    // проверка наличия товара по ID
    public static boolean toyExistsInShop(String id) {
        for (Toy toy : list) {
            if (toy.getId().equals(id))
                return true;
        }
        return false;
    }

    // добавление игрушки в магазин (если все параметры заданы корректно)
    public static void addToy() {
        Main.clearScreen();
        list.add(new Toy());
        Main.errorCodes(0);
    }

    public static void changeWeight() {
        if (list.isEmpty())
            Main.errorCodes(6);
        else {
            Toy toy = null;
            do {
                printList();
                System.out.print("Введите id игрушки, для которой необходимо изменить вес: ");
                toy = getToyById(Main.in.nextLine());
            } while (toy == null);
            toy.changeWeight();
            Main.errorCodes(0);
        }
    }

    private static Toy getToyById(String id) {
        for (Toy toy : list) {
            if (toy.getId().equals(id))
                return toy;
        }
        Main.errorCodes(7, id);
        return null;
    }

    public static void printList() {
        // Main.clearScreen();
        if (list.isEmpty())
            Main.errorCodes(6);
        else {
            System.out.println("--------------------------");
            System.out.println("Список игрушек, доступных для игры:");
            for (Toy toy : list) {
                System.out.println(toy.toString());
            }
            System.out.println("Суммарный вес: " + getSummaryWeight());
            System.out.println("--------------------------");
        }
    }

    public static void letsPlay() {
        if (list.isEmpty())
            Main.errorCodes(6);
        else if (getSummaryWeight() != 100)
            Main.errorCodes(8, Double.toString(getSummaryWeight()));
        else {
            // формируем массив из 100 элементов, в каждом из которых хранится ID игрушки
            String[] toys = new String[100];
            // количество элементов для каждой игрушки должно быть равно её весу
            int start = 0;
            for (Toy toy : list) {
                int finish = start + toy.getWeight().intValue();
                for (int i = start; i < finish; i++) {
                    toys[i] = toy.getId();
                    // System.out.println(i + ": " + toy.getId());
                }
                start += toy.getWeight();
            }

            // выбираем случайный элемент в массиве - в нём хранится ID выигранной игрушки
            Random rnd = new Random();
            int randomToy = rnd.nextInt(100);

            // выигранную игрушку нужно добавить в очередь на выдачу
            prizes.add(getToyById(toys[randomToy]));
            System.out.println("--------------------------");
            System.out.println("Вы выиграли игрушку с id " + toys[randomToy]);
            System.out.println("--------------------------");
            printPrizes();

            // после розыгрыша нужно уменьшить количество для выигранной игрушки
            Toy toy = getToyById(toys[randomToy]);
            toy.decreaseCount();
            // если количество игрушки нулевое, то её нужно удалить из магазина, а также
            if (toy.getCount() <= 0) {
                // сначала уменьшить суммарный вес
                increaseSummaryWeight(-toy.getWeight());
                // уже после этого удалить игрушку из магазина
                list.remove(toy);
            }
            // отображаем список того, что осталось только, если что-то осталось
            if (list.size() > 0)
                printList();
        }
    }

    public static void printPrizes() {
        if (prizes.isEmpty())
            Main.errorCodes(9);
        else {
            System.out.println("--------------------------");
            System.out.println("Список призов, ожидающих выдачи:");
            for (Toy toy : prizes) {
                System.out.println("id: " + toy.getId() + ", Название: " + toy.getName());
            }
            System.out.println("--------------------------");
        }
    }

    public static void getPrize() {
        if (prizes.isEmpty())
            Main.errorCodes(9);
        else {
            // пишем информацию о выданной игрушке в файле
            try (
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("id: " + prizes.get(0).getId() + ", Название: " + prizes.get(0).getName() + "\n");
            } catch (IOException e) {
                Main.errorCodes(10, file);
            }
            // удаляем выданную игрушку из списка пока невыданных призов
            prizes.remove(0);
            // выводим список того, что осталось выдать
            System.out.println("--------------------------");
            System.out.println("Приз выдан. Приходите к нам ещё :-)");
            System.out.println("--------------------------");
            printPrizes();
        }
    }
}
