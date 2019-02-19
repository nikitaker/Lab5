import java.util.HashMap;
import java.util.Map;

public class Main {

    static Map<Long, Object> collection = new HashMap<Long,Object>();

    public static void main(String[] args)
    {
        String variant = "10149";
        //String path = System.getenv("KARLSON_PATH");
        String path = "/home/hakerman/collection.json";
        String path2 = "/home/hakerman/collection.json";

        FileReader.generate(path);
        FileReader.endProg(path2);
        System.out.println(collection.keySet());
        try {
            ConsoleCom.start();
        }
        catch(java.io.IOException e){}


    }

}
