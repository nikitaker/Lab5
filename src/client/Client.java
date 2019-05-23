package client;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import shared.*;
import shared.FileReader;


public class Client {
    private DatagramChannel udpChanel;
    private InetSocketAddress serverAddress;
    private DatagramSocket udpChanelSocket;
    private Scanner scanner;

    private Client(String destinationAddr, int port) throws IOException {

        //this.port = port;
        this.serverAddress = new InetSocketAddress(destinationAddr,port);
        this.udpChanel = DatagramChannel.open();
        this.udpChanelSocket = udpChanel.socket();
        //this.udpChanelSocket.bind(new InetSocketAddress(6666));

        scanner = new Scanner(System.in);
    }

    public static void showUsage() {
        System.out.println("Hello stranger,");
        System.out.println("To run the client properly you need to follow some rules");
        System.out.println("\tjava -jar Client.jar <host> <port>");
        System.exit(1);
    }

    public void testServerConnection() throws IOException {
        System.out.println("Trying to reach a remote host...");
        ByteArrayOutputStream testRequest = createRequest("connecting", "");
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        udpChanel.socket().setSoTimeout(1000);
        buffer.clear();
        buffer.put(testRequest.toByteArray());
        buffer.flip();


        boolean connected = false;
        String connectString;

        for (int i = 1; i < 11; i++) {
            System.out.println("\t* Attempt #" + i);
            udpChanel.send(buffer,serverAddress);
            buffer.clear();
            try {
                this.udpChanel.socket().receive(new DatagramPacket(buffer.array(), buffer.array().length));
            }
            catch (Exception e){
                continue;
            }

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                Response response = (Response) ois.readObject();
                connectString = new String(decodeResponse("connecting", response));
            } catch (IOException | ClassNotFoundException e) {
                connectString = "Not connected";
            }

            if (connectString.equals("connected")) {
                connected = true;
                break;
            }
        }

            if (connected) {
                System.out.println("Connection with the server is established");
            } else {
                System.err.println("Server is unreachable at this moment");
                System.exit(1);
            }
        }



    private int start() throws IOException {
        System.out.println("Client is established");
        System.out.println();
        System.out.println("Feed me with your commands:");
        System.out.print("> ");
        String input;
        String lastCommand = "";
        String addStr = "";
        boolean commandEnd = true;
        boolean correctCommand = false;
        int nestingJSON = 0;
        ByteArrayOutputStream request = null;

        while (!(input = scanner.nextLine().trim()).toLowerCase().equals("exit")) {
            if (!input.equals("")) {
                String command = input.split(" ")[0].toLowerCase();
                if (nestingJSON < 0) {
                    nestingJSON = 0;
                    lastCommand = "";
                    addStr = "";
                    commandEnd = true;
                }

                if (!commandEnd && (lastCommand.equals("add") || lastCommand.equals("add_if_min")
                        || lastCommand.equals("remove") || lastCommand.equals("add_if_max")
                        || lastCommand.equals("remove_lower"))) {

                    nestingJSON += charCounter(input, '{');
                    nestingJSON -= charCounter(input, '}');
                    correctCommand = true;
                    addStr += input;
                    if (nestingJSON == 0) {
                        commandEnd = true;

                    }

                } else if (command.equals("add_if_min") && commandEnd) {

                    lastCommand = "add_if_min";
                    commandEnd = false;
                    correctCommand = true;
                    addStr = input.substring(10).trim();
                    nestingJSON += charCounter(addStr, '{');
                    nestingJSON -= charCounter(addStr, '}');
                    if (nestingJSON == 0) {
                        commandEnd = true;
                    }

                } else if (command.equals("add") && commandEnd) {

                    lastCommand = "add";
                    commandEnd = false;
                    correctCommand = true;
                    addStr = input.substring(3).trim();
                    nestingJSON += charCounter(addStr, '{');
                    nestingJSON -= charCounter(addStr, '}');
                    if (nestingJSON == 0) {
                        commandEnd = true;
                    }

                } else if (command.equals("remove") && commandEnd) {

                    lastCommand = "remove";
                    commandEnd = false;
                    correctCommand = true;
                    addStr = input.substring(6).trim();
                    nestingJSON += charCounter(addStr, '{');
                    nestingJSON -= charCounter(addStr, '}');
                    if (nestingJSON == 0) {
                        commandEnd = true;
                    }

                }else if (command.equals("add_if_max") && commandEnd) {

                    lastCommand = "add_if_max";
                    correctCommand = true;
                    commandEnd = false;
                    nestingJSON += charCounter(addStr, '{');
                    nestingJSON -= charCounter(addStr, '}');
                    addStr = input.substring(10).trim();
                    if (nestingJSON == 0) {
                        commandEnd = true;
                    }

                }else if (command.equals("remove_lower") && commandEnd) {

                    lastCommand = "remove_lower";
                    commandEnd = false;
                    correctCommand = true;
                    addStr = input.substring(12).trim();
                    nestingJSON += charCounter(addStr, '{');
                    nestingJSON -= charCounter(addStr, '}');
                    if (nestingJSON == 0) {
                        commandEnd = true;
                    }

                }else if (command.equals("show") && commandEnd) {
                    lastCommand = "show";
                    correctCommand = true;
                    request = createRequest("show", null);
                } else if (command.equals("save") && commandEnd) {
                    lastCommand = "save";
                    correctCommand = true;
                    request = createRequest("save", null);
                } else if (command.equals("import") && commandEnd) {
                    lastCommand = "import";
                    correctCommand = true;
                    request = createRequest("import", input.substring(6).trim());
                } else if (command.equals("info") && commandEnd) {
                    lastCommand = "info";
                    correctCommand = true;
                    request = createRequest("info", null);
                }  else if (command.equals("help") && commandEnd) {
                    lastCommand = "help";
                    correctCommand = true;
                    request = createRequest("help", null);
                } else {
                    correctCommand = false;
                    commandEnd = true;
                }

                if (lastCommand.equals("add") && commandEnd && correctCommand) {
                    request = createRequest("add", addStr);
                    addStr = "";
                    correctCommand = true;
                } else if (lastCommand.equals("add_if_min") && commandEnd && correctCommand) {
                    request = createRequest("add_if_min", addStr);
                    addStr = "";
                    correctCommand = true;
                } else if (lastCommand.equals("remove") && commandEnd && correctCommand){
                    request = createRequest("remove", addStr);
                    addStr = "";
                    correctCommand = true;
                } else if (lastCommand.equals("add_if_max") && commandEnd && correctCommand){
                    request = createRequest("add_if_max", addStr);
                    addStr = "";
                    correctCommand = true;
                } else if (lastCommand.equals("remove_lower") && commandEnd && correctCommand){
                    request = createRequest("remove_lower", addStr);
                    addStr = "";
                    correctCommand = true;
                }
            } else {
                if (commandEnd)
                    correctCommand = false;
            }

            if (commandEnd && correctCommand) {
                try {
                    if (request != null) {
                        ByteBuffer buffer = ByteBuffer.allocate(8192);
                        buffer.clear();
                        buffer.put(request.toByteArray());
                        buffer.flip();
                        this.udpChanel.send(buffer,serverAddress);
                    } else {
                        throw new Exception();
                    }

                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    buffer.clear();
                    try {
                        this.udpChanel.socket().receive(new DatagramPacket(buffer.array(), buffer.array().length));
                    } catch (SocketTimeoutException e) {
                        System.err.println("Disconnected from host");
                        testServerConnection();
                    }

                    try(ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                        ObjectInputStream ois = new ObjectInputStream(bais)) {
                        Response response = (Response) ois.readObject();
                        ois.close();
                        String output = new String(decodeResponse(lastCommand, response));
                        if (!output.equals("show")) {
                            System.out.println(output);
                        }
                    } catch (IOException e) {
                        System.out.println("Потеря потока данных");
                        //e.printStackTrace();
                    }

                } catch (Exception e) {
                    System.err.println("Неправильный JSON");

                }
                System.out.print("> ");
            } else if (commandEnd) {
                if (!input.trim().equals("")) {
                    System.err.println("Команда не определена");
                }
                System.out.print("> ");
            }
        }
        return 0;
    }

    private ByteArrayOutputStream createRequest(String command, String data) throws IOException {
        Command c = new Command(command, data);

        if (command.equals("add") || command.equals("add_if_min")
                || command.equals("remove") || command.equals("add_if_max")
                || command.equals("remove_lower")) {
            try {
                c.setData(Comands.findKarlson(Comands.parseKarlsonMap(data)));
            } catch (Exception e) {
                return null;
            }
        } else if (command.equals("import")) {
            c.setData(FileReader.generate(data));
            if (c.getData() == null) {
                return null;
            }
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            oos.writeObject(c);
            oos.flush();
            return outputStream;
        } catch (IOException e) {
            throw new IOException();
        }


    }

    private byte[] decodeResponse(String command, Response response) {
        if (command.equals("show")) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream((byte[])response.getResponse());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                ArrayList<Karlson> storage = (ArrayList<Karlson>) ois.readObject();
                synchronized (storage) {
                    storage.forEach(System.out::println);
                }
            } catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
            }
        } else {
            return (byte[])response.getResponse();
        }
        return "show".getBytes();
    }

    private int charCounter(String in, char c) {
        int count = 0;
        for (char current: in.toCharArray())
            if (current == c)
                count++;
        return count;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            showUsage();
        }

        try {
            Client sender = new Client(args[0], Integer.parseInt(args[1]));
            System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");
            System.out.println("-- UDP client settings --");
            System.out.println("-- UDP connection to " + args[0] + " host --");
            System.out.println("-- UDP port: " + args[1] + " --");
            sender.testServerConnection();
            sender.start();
        } catch (Exception e) {
            System.err.println("Oh no, something bad has happened!");
            //e.printStackTrace();
            showUsage();
        }
    }
}