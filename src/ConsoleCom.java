import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

public class ConsoleCom {

    static void  start() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i = 0;
        while(true){
            System.out.println("Введите команду");

            String line = br.readLine();
            if(Arrays.asList(line.split(" ")).contains("remove")){
                try { Main.collection.remove(Long.parseLong(Comands.findInPer(line))); }
                catch (NumberFormatException e){ System.out.println("В скобках должно находиться числовое значение"); }
            }


            if(Arrays.asList(line.split(" ")).contains("remove_lover")){
                try {
                    Long lng = Long.parseLong(Comands.findInPer(line));
                    Iterator<Long> iterator = Main.collection.keySet().iterator();
                    while (iterator.hasNext()){
                        Long element = iterator.next();
                        if(element < lng){
                            iterator.remove();
                            Main.collection.remove(element);}
                    }
                }
                catch (NumberFormatException e){ System.out.println("В скобках должно находиться числовое значение"); }
            }


            if (line.equals("exit")){break;}
            if (line.equals("show")){System.out.println(Main.collection.values());}
        }
    }
}
