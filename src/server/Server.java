package server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

import shared.*;
import shared.FileReader;

public class Server extends Thread{

    private DatagramChannel udpChannel;
    private int port;
    private ConcurrentHashMap<Long,Karlson> storage;
    private String filename;
    private CommandHandler handler;
    InetSocketAddress clientAddress;

    public Server(int port) throws IOException {
        this.port = port;
        this.udpChannel = DatagramChannel.open().bind(new InetSocketAddress("localhost", port));
        System.out.println("-- Yahoo! We Have A Lift Off! --");
        System.out.println("-- UDP Server settings --");
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
            // Receiving udp package
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            buffer.clear();
            clientAddress = (InetSocketAddress) udpChannel.receive(buffer);

            Command command;

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                 ObjectInputStream ois = new ObjectInputStream(bais)){

                command = (Command) ois.readObject();
                ois.close();
                bais.close();
                System.out.println("-- Client's input: " + command.getCommand());


                handler = new CommandHandler();
                handler.setStart(command, storage);
                handler.start();
                run();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void run()
    {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.flush();
            Response response = new Response(handler.getResponse());

            int i = 0;
            while (i <= 100000 && response.getResponse() == null) {
                response.setResponse(handler.getResponse());
                i = i+1;
            }

            if (response.getResponse() != null) {
                oos.writeObject(response);
                response.setResponse(null);
                oos.flush();
                buffer.clear();
                buffer.put(baos.toByteArray());
                buffer.flip();
                udpChannel.send(buffer, clientAddress);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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