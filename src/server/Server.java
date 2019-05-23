package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import shared.*;
import shared.FileReader;

public class Server extends Thread{

    private DatagramSocket udpSocket;
    private int port;
    private ConcurrentHashMap<Long,Karlson> storage;
    private String filename;
    private CommandHandler handler;
    byte[] buf = new byte[8192];

    public Server(int port) throws IOException {
        this.port = port;
        this.udpSocket = new DatagramSocket(port);
        System.out.println("-- Yahoo! We Have A Lift Off! --");
        System.out.println("-- UDP Server setimport java.util.Setings --");
        System.out.println("-- UDP address: " + InetAddress.getLocalHost() + " --");
        System.out.println("-- UDP port: " + this.port + " --");

    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void loadCollection() {
        System.out.println("Loading the collection...");
        try {
            storage = FileReader.generate(filename);
            CommandHandler.fileName = filename;
        } catch (Exception e) {
            System.err.println("On no, backup file is not found. Do you even care ?!");
            System.exit(1);
        }
    }

    private void listen() throws Exception {

        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + " --");

        while (true) {

            DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
            udpSocket.receive(datagramPacket);
            InetAddress address = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            datagramPacket = new DatagramPacket(buf,buf.length,address,port);

            Command command;

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                 ObjectInputStream ois = new ObjectInputStream(bais)){
                command = (Command) ois.readObject();

                System.out.println("-- Client's input: " + command.getCommand());


                handler = new CommandHandler();
                handler.setStart(command, storage, datagramPacket);
                handler.start();

            } catch (IOException | ClassNotFoundException e) {

            }
        }

    }


    @Override
    public void run()
    {

    }

    public static void showUsage() {
        System.out.println("Hello stranger,");
        System.out.println("To run the server properly you need to follow some rules");
        System.out.println("\tjava -jar Server.jar <port> <path to backup file>");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        int input_port = -1;

        if (args.length == 0) {
            showUsage();
        }

        try {
            input_port = Integer.parseInt(args[0]);
        } catch (IllegalArgumentException e) {
            showUsage();
        }

        Server server = new Server(input_port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FileReader.endProg(server.storage,server.filename);
                System.out.println("Server data saved");
            } catch (Exception e) {
                System.err.println("************* YARIK BOCHOK POTIK  **************");
            }
        }));

        if (args.length == 2) {
            server.setFilename(args[1]);
        }
        try {
            server.loadCollection();
            server.listen();
            server.listen();
            server.listen();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}