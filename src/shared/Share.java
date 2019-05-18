package shared;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;


public class Share {

        static ConcurrentHashMap<Long, Karlson> collection = new ConcurrentHashMap<Long, Karlson>();
        static String path = "/home/s263143/Lab5/out/artifacts/collection.json";

        public static void main(String[] args)
        {
            try{
                System.setOut(new PrintStream(System.out, true, "UTF-8"));
            }
            catch (UnsupportedEncodingException e){
                System.out.println("Kdirovka - huinya");
            }
            String variant = "10149";
            //String path = System.getenv("KARLSON");
            //String path = "/home/hakerman/collection.json";
            //String path2 = "/home/hakerman/collection.json";

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println();
                    //FileReader.endProg(path);
                } catch (Exception e) {
                    System.err.println("************* YARIK BOCHOK POTIK  **************");
                }
            }));

            FileReader.generate(path);


        }

    }


