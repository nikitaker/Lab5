import org.jetbrains.annotations.NotNull;

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
            return Long.parseLong(a1.toString());
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

    static void addToCollection(Long l, String s){
        if (l != null && s != null) {
            Karlson obj = new Karlson();
            obj.name = s;
            obj.flyspeed = l;
            Main.collection.put(l, s);
        }
    }

    @NotNull
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
