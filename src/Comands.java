import com.google.gson.*;
import java.util.HashMap;
import java.util.Map;

public class Comands {
    static Long getFlySpeed(String string) {
        if (!string.equals("{") && !string.equals("}")) {
            char[] res = string.toCharArray();
            StringBuilder a1 = new StringBuilder();
            int i = 0;
            while (res[i] != ':') {
                if (res[i] == '"') {
                    i++;
                    while (res[i] != '"') {
                        a1.append(res[i]);
                        i++;
                    }
                }
                i++;
            }
            System.out.println(a1);
            try {
                return Long.parseLong(a1.toString());
            }
            catch (NumberFormatException e){
                System.out.println("Ключ должен быть числом");
            }
        }
        return null;
    }

    static String getObjectName(String string) {
        if (!string.equals("{") && !string.equals("}")) {
            char[] res = string.toCharArray();
            StringBuilder a1 = new StringBuilder();
            int i = 0;
            while (res[i] != ':') {i++;}
            i++;
            if (res[i] == '"') {
                i+=1;
                while (res[i] != '"') {
                    a1.append(res[i]);
                    i++;
                }
            }
            System.out.println(a1);
            return a1.toString();
        }
        return null;
    }

    static void addToCollection(String s){
        Gson gson = new GsonBuilder().create();
        Map<Long, Karlson> map = gson.fromJson(s,Map.class);
        Main.collection.putAll(map);
    }


    static String findInPer(String string){
        int i = 0;
        char[] array = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        while (array[i] != '{' && i<array.length-1){i++;}
        i++;
        while (array[i] != '}' && i<array.length-1)
        {
            stringBuilder.append(array[i]);
            i++;
        }
        return stringBuilder.toString();
    }

}
