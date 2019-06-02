package shared;

import com.google.gson.*;

import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

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

    public static ConcurrentHashMap<Long,Karlson> parseKarlsonMap(String s)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.enableComplexMapKeySerialization();
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<ConcurrentHashMap<Long, Karlson>>(){}.getType();
        ConcurrentHashMap<Long,Karlson> map = gson.fromJson(s,type);
        ConcurrentHashMap<Long,Karlson> concurrentHashMap = null;
        System.out.println(map);
        return map;
    }

    public static Karlson findKarlson(ConcurrentHashMap<Long,Karlson> map){
        try {
            Karlson karlson = (Karlson) map.values().toArray()[0];
            karlson.setFlyspeed((Long) map.keySet().toArray()[0]);
            karlson.setDateTime(ZonedDateTime.now());
            return karlson;
            }
        catch(Exception e)
            {
                System.out.println("Объект не карлсон");
            }
        return null;
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