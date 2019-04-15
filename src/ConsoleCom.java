import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class ConsoleCom {

    static void start() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i = 0;
        while (true) {
            System.out.println("Введите команду (для помощи наберите help)");

            String line = br.readLine();
            if (Arrays.asList(line.split(" ")).contains("remove")) {
                try {
                    Main.collection.remove(Long.parseLong(Comands.findInPer(line)));
                } catch (NumberFormatException e) {
                    System.out.println("В скобках должно находиться числовое значение");
                }
            }


            if (Arrays.asList(line.split(" ")).contains("remove_lover")) {
                try {
                    Long lng = Long.parseLong(Comands.findInPer(line));
                    Iterator<Long> iterator = Main.collection.keySet().iterator();
                    while (iterator.hasNext()) {
                        Long element = iterator.next();
                        if (element < lng) {
                            iterator.remove();
                            Main.collection.remove(element);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("В скобках должно находиться числовое значение");
                }
            }

            if (Arrays.asList(line.split(" ")).contains("insert")) {
                char[] chars;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                boolean hasPer = true;
                while (hasPer) {
                    String inputLine = br.readLine();
                    stringBuilder.append(inputLine);
                    chars = stringBuilder.toString().toCharArray();
                    int i1 = 0;
                    while (i1 < chars.length) {
                        if (chars[i1] == '}') {
                            hasPer = false;
                            break;
                        }
                        i1++;
                    }
                }
                String lines = stringBuilder.toString().replaceAll("insert", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
                System.out.println(lines);
                Comands.addToCollection(lines);

            }
            if (line.equals("help")) {
                System.out.println("remove_lower {String key} :удалить из коллекции все элементы, ключ которых меньше, чем заданный");
                System.out.println("insert {element} :добавить новый элемент с заданным ключом");
                System.out.println("show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
                System.out.println("info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
                System.out.println("remove {String key}: удалить элемент из коллекции по его ключу");
                System.out.println("add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
                System.out.println("add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
                System.out.println("exit: выход из программы с сохранением");
            }


            if (line.equals("exit")) {
                FileReader.endProg(Main.path);
                break;
            }

            if (line.equals("show")) {
                Iterator iterator = Main.collection.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry karlsonEntry = (Map.Entry) iterator.next();

                    System.out.println(karlsonEntry.getValue());
                }
            }
        }
    }
}

