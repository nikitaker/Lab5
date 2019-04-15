import java.util.HashMap;
import java.util.Map;

public class Main {

    static Map<Long, Karlson> collection = new HashMap<Long, Karlson>();
    static String path = "/home/hakerman/collection.json";

    public static void main(String[] args)
    {
        String variant = "10149";
        //String path = System.getenv("KARLSON");
        //String path = "/home/hakerman/collection.json";
        //String path2 = "/home/hakerman/collection.json";

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println();
                FileReader.endProg(path);
            } catch (Exception e) {
                System.err.println("************* YARIK BOCHOK POTIK  **************");
            }
        }));

        FileReader.generate(path);

        System.out.println(collection.keySet());
        try {
            ConsoleCom.start();
        }
        catch(java.io.IOException e){}


    }

}
