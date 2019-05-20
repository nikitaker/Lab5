package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.nio.file.AccessDeniedException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class FileReader {

    public static ConcurrentHashMap<Long,Karlson> generate(String path) {

        File file;

        if (path == null) { System.out.println("Отсутствует путь в переменной"); return null;}

        else {

            try {
                file = new File(path);
                Scanner sc = new Scanner(file);
                StringBuilder stringBuilder = new StringBuilder();
                while (sc.hasNextLine()) {
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                String string = stringBuilder.toString().replaceAll("\t","").replaceAll("\n","");
                return Comands.parseKarlsonMap(string);
            }
            catch (Exception e) {
                System.out.println("Файл пуст или не сущесвует");
                //e.printStackTrace();
                return null;
            }
        }
    }

    public static byte[] endProg(ConcurrentHashMap<Long,Karlson> collection,String path){
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            Gson gson = new GsonBuilder().create();
            String s = gson.toJson(collection);
            outputStream.write(s.getBytes());
            outputStream.close();
            return "<<<<<<<<<<<<<<< Файл успешно сохранен >>>>>>>>>>>>>>>".getBytes();
        }
        catch (AccessDeniedException e){
            return ("Нет доступа к файлу").getBytes();
        }
        catch (FileNotFoundException e){
            return ("Файл записи был удален или перемещен").getBytes();
        }
        catch (IOException e){
            return ("Возник мем").getBytes();
        }


    }

}
