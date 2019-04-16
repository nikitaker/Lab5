import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;

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
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                boolean hasPer = true;
                while (hasPer) {
                    String inputLine = br.readLine();
                    stringBuilder.append(inputLine);
                    if (inputLine == "") {
                        hasPer = false;
                    }
                }
                String lines = stringBuilder.toString().replaceAll("insert", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
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
            if (Arrays.asList(line.split(" ")).contains("add_if_max")) {
                String lines = line.replaceAll("add_if_max", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
                Gson gson = new GsonBuilder().create();
                Karlson karlson = gson.fromJson(lines,Karlson.class);
                Long ln = Collections.max(Main.collection.keySet());
                if(karlson.flyspeed > ln){
                    Comands.addToCollection(lines);
                }
            }
            if (Arrays.asList(line.split(" ")).contains("add_if_min")) {
                String lines = line.replaceAll("add_if_min", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
                Gson gson = new GsonBuilder().create();
                Karlson karlson = gson.fromJson(lines,Karlson.class);
                Long ln = Collections.min(Main.collection.keySet());
                if(karlson.flyspeed > ln){
                    Comands.addToCollection(lines);
                }
            }
            if (line.equals("exit")) {
                FileReader.endProg(Main.path);
                break;
            }
            if (line.equals("info")) {
                StringBuilder result = new StringBuilder();
                Iterator e = Main.collection.entrySet().iterator();

                result.append("Размер коллекции: "+ Main.collection.size()+ "\n");
                result.append("Тип коллекции: "+ Main.collection.getClass().getName()+"\n");
                Date d = new Date();
                result.append("Дата инцициализации: "+d.toString());
                System.out.println(result.toString());

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

