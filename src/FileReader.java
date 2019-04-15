import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Iterator;
import java.util.Scanner;

class FileReader {

    static void generate(String path) {

        File file;

        if (path == null) { System.out.println("Отсутствует путь в переменной");}
        else {

            try {
                file = new File(path);
                Scanner sc = new Scanner(file);
                StringBuilder stringBuilder = new StringBuilder();
                while (sc.hasNextLine()) {
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                String string = stringBuilder.toString().replaceAll("insert","").replaceAll(" ","").replaceAll("\t","").replaceAll("\n","");
                Comands.addToCollection(string);
            } catch (Exception e) {
                System.out.println("Файл пуст или не существует");
            }
        }
    }

    static void endProg(String path){
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            Gson gson = new GsonBuilder().create();
            String s = gson.toJson(Main.collection);
            System.out.println(s);
            outputStream.write(s.getBytes());
            outputStream.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Файл записи был удален или перемещен");
        }
        catch (IOException e){
            System.out.println(e);
        }

    }

}
