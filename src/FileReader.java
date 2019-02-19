import java.io.*;
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
                while (sc.hasNextLine()) {

                    String string = sc.nextLine();
                    Comands.addToCollection(Comands.getFlySpeed(string), Comands.getObjectName(string));
                }
                sc.close();
            } catch (java.io.FileNotFoundException e) {
                System.out.println(e);
            }
        }
    }

    static void endProg(String path){
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bytes;
            StringBuilder output = new StringBuilder();
            output.append("{\n");
            Iterator<Long> iterator = Main.collection.keySet().iterator();
            while (iterator.hasNext()) {
                Long element = iterator.next();
                output.append('"' + element.toString() +'"'+':'+'"'+ Main.collection.get(element) + '"' + ",\n");
            }
            output.append('}');
            bytes = output.toString().getBytes();
            outputStream.write(bytes);
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
