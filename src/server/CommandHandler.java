package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import shared.*;
import shared.FileReader;

public class CommandHandler extends Thread {

    private InetAddress inetAddress;
    DatagramPacket datagramPacket;
    private String username = null;
    private String password = null;
    private String mail = null;
    private int port;
    public static String fileName;
    public static String FILEPATH = "karlson.json";
    private Object response;
    private ConcurrentHashMap<Long,Karlson> starthashMap;
    private Command startComand;
    private DataBaseConnection db;

    public CommandHandler(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public CommandHandler() {

    }

    public void setStart(Command command, ConcurrentHashMap<Long,Karlson> map, DatagramPacket datagramPacket, DataBaseConnection db){
        this.startComand = command;
        this.starthashMap = map;
        this.datagramPacket = datagramPacket;
        this.db = db;

    }

    @Override
    public void run() {
        try {
            DatagramSocket udpSocket = new DatagramSocket();
            this.response = handleCommand(startComand, starthashMap, db);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            Response response = new Response(this.getResponse());

            if (response.getResponse() != null) {
                oos.writeObject(response);
                oos.flush();
                datagramPacket.setData(baos.toByteArray());
                System.out.println(datagramPacket);
                udpSocket.send(datagramPacket);
            }
        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    public Object getResponse() {
        return response;
    }

    public Object handleCommand(Command com, ConcurrentHashMap<Long,Karlson> storage, DataBaseConnection db) {

        Object credentials = com.getCredentials();
        String command = com.getCommand();
        Object data = com.getData();

        if (com.getCredentials() != null && command.toLowerCase().equals("register")) {
            username = ((String) credentials).split(" ")[0];
            mail = ((String) credentials).split(" ")[1];
            password = ((String) credentials).split(" ")[2];
        } else if (credentials != null) {
            username = ((String) credentials).split(" ")[0];
            password = ((String) credentials).split(" ")[1];
        }

            byte[] buffer;

            switch (command.toLowerCase()) {
                case "connecting":
                    buffer = "connected".getBytes();
                    break;
                case "add":
                    if (data != null) {
                        buffer = add(storage, (Karlson) data, db, username);
                    } else {buffer = help();}
                    break;
                case "add_if_min":
                    if (data != null) {
                        buffer = add_if_min(storage, (Karlson) data, db, username);
                    } else {buffer = help();}
                    break;
                case "add_if_max":
                    if (data != null) {
                        buffer = add_if_max(storage, (Karlson) data, db, username);
                    } else {buffer = help();}
                    break;
                case "remove":
                    if (data != null) {
                        buffer = remove(storage, (Karlson) data, db, username);
                    } else {buffer = help();}
                    break;
                case "remove_lower":
                    if (data != null) {
                        buffer = remove_lower(storage, (Karlson) data, db, username);
                    } else {buffer = help();}
                    break;
                case "show":
                    buffer = show(storage);
                    break;
                case "save":
                    buffer = save(storage);
                    break;
                case "loadhash":
                    ConcurrentHashMap<Long,Karlson> hashMap = new ConcurrentHashMap<Long,Karlson>();
                    for (Karlson karlson:(ArrayList<Karlson>) data) {
                        hashMap.put(karlson.getFlyspeed(),karlson); }
                    buffer = save(hashMap);
                        break;
                case "import":
                    buffer = import1(storage, (ConcurrentHashMap<Long,Karlson>) data, db, username);
                    break;
                case "info":
                    buffer = info(storage);
                    break;
                case "help":
                    buffer = help();
                    break;
                case "register":
                    int resultR = db.executeRegister(username, mail, password);
                    if (resultR == 1) {
                        buffer = "Email registration is approved!".getBytes();
                    } else if (resultR == 0) {
                        buffer = "You've already registered!".getBytes();
                    } else {
                        buffer = "Can't register you".getBytes();
                    }
                    break;
                case "login":
                    int result = db.executeLogin(username, password);
                    if (result == 0) {
                        buffer = "Logged in".getBytes();
                    } else if (result == 1) {
                        buffer = "You need to register first!".getBytes();
                    } else if (result == 2) {
                        buffer = "Wrong Password!".getBytes();
                    } else {
                        buffer = "Can't log in".getBytes();
                    }
                    break;
                default:
                    buffer = "Error: undefined command! Type \"help\" for a list of available commands".getBytes();
            }
            return buffer;

    }


    public byte[] show(ConcurrentHashMap<Long,Karlson> storage) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            ArrayList<Karlson> list = new ArrayList<Karlson>(storage.values());
            Collections.sort(list);
            oos.writeObject(list);
            oos.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            System.err.println("Aliens snatched the collection! Can't show it.");
        }

        return null;

    }

    public byte[] save(ConcurrentHashMap<Long,Karlson> storage) {
        if (storage != null) {
            db.savePersons(storage);
            return "Saved Karlsons to the DataBase".getBytes();
        } else return "Collection is empty; nothing to save!".getBytes();
    }

    public byte[] add(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson, DataBaseConnection db, String username) {
        synchronized (storage) {
            if (storage.put(karlson.getFlyspeed(),karlson) == null) {
                db.addToDB(karlson, username);
                System.out.println("A karlson " + karlson.toString()+ " was successfully added.");
                return ("A karlson " + karlson.toString()+ " was successfully added.").getBytes();
            } else {
                return (storage.put(karlson.getFlyspeed(),karlson) + "was replaced").getBytes();
            }
        }
    }

    public byte[] add_if_min(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson, DataBaseConnection db, String username) {
        if (storage.size() > 0) {
            synchronized (storage.entrySet()) {
                Karlson min = storage
                        .values()
                        .stream()
                        .min(Karlson::compareTo)
                        .get();
                if (karlson.compareTo(min) < 0) {
                    return add(storage, karlson, db, username);
                } else {
                    return (karlson.getName()+ "'s name isn't the smallest: Can't add to a collection!").getBytes();
                }
            }
        } else {
            return add(storage, karlson, db, username);
        }
    }

    public byte[] add_if_max(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson, DataBaseConnection db, String username) {
        if (storage.size() > 0) {
            synchronized (storage.entrySet()) {
                Karlson max = storage
                        .values()
                        .stream()
                        .max(Karlson::compareTo)
                        .get();
                if (karlson.compareTo(max) > 0) {
                    return add(storage, karlson, db, username);
                } else {
                    return (karlson.getName()+ "'s name isn't the biggest: Can't add to a collection!").getBytes();
                }
            }
        } else {
            return add(storage, karlson, db, username);
        }
    }

    public byte[] import1(ConcurrentHashMap<Long,Karlson> storage, ConcurrentHashMap<Long,Karlson> importing, DataBaseConnection db, String username) {
        long start = storage.entrySet().stream().count();
        Collection<Karlson>imported = importing.values();
        for (Karlson karlson: imported) {
            add(storage, karlson, db, username);
        }
        long end = storage.entrySet().stream().count();
        System.out.println("Imported "+ (end-start) + " objects.");
        return ("+++++ Imported "+ (end-start) + " objects +++++").getBytes();
    }

    public byte[] info(ConcurrentHashMap<Long,Karlson> storage) {
        return ("Collection is a " + storage.getClass() + " type.\n" +
                "Currently it contains " + storage.size() + " objects.").getBytes();
    }

    public byte[] remove(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson, DataBaseConnection db, String username){
        if (storage.entrySet().removeIf(x -> (x.getValue().getFlyspeed().equals(karlson.getFlyspeed())) && (username.equals(x.getValue().getOwner()) || x.getValue().getOwner().equals("all")))) {
            db.removePerson(username, karlson);
            System.out.println("A human " + karlson.toString() + " has been deleted");
            return ("A human " + karlson.toString() + " has been deleted :(").getBytes();
        } else {
            return "There's no such object in the collection. Try adding instead.\nPerhaps, you don't have rights to delete it!".getBytes();
        }
    }

    public byte[] remove_lower(ConcurrentHashMap<Long,Karlson> storage, Karlson endObject, DataBaseConnection db, String username) {

        long start = storage.entrySet().stream().count();
        synchronized (storage) {
            storage.entrySet().stream()
                    .filter(x -> (username.equals(x.getValue().getOwner()) || x.getValue().getOwner().equals("all")))
                    .filter(x -> x.getValue().compareTo(endObject) < 0)
                    .forEach(x -> {
                        db.removePerson(username, x.getValue());
                    });
            storage.entrySet().removeIf(item -> item.getValue().compareTo(endObject) < 0 && (username.equals(item.getValue().getOwner()) || item.getValue().getOwner().equals("all")));
        }
        long end = storage.entrySet().stream().count();
        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();
    }

    public byte[] help() {
        String jsonExample = "\r\n{\r\n   123 : { \"name\": \"kolya\",\r\n  \"flyspeed\":  \"123\" \r\n   \"clothes\": \"{}\"\r\n}\r";

        return ("\nAvailable commands: \n" +
                "Example of JSON Karlson declaration:" + jsonExample +
                "\n* insert {element} - adds an element to collection, element - is a JSON string, see above" +
                "\n* show - shows a list of all elements in a collection" +
                "\n* save - save a collection to a source file" +
                "\n* import {path} - adds all of the elements to a collection from a file, path - path to the .csv file" +
                "\n* info - information about collection" +
                "\n* remove {element} - removes an element from collection, element - is a JSON string, see above" +
                "\n* add_if_min {element} - adds an element to collection if it's the smallest, element - is a JSON String, see above" +
                "\n* add_if_max {element} - adds an element to collection if it's the biggest, element - is a JSON String, see above"+
                "\n* remove_lower {element} - removes objects lower than an element, element - is a JSON String, see above" +
                "\n* help - a list of all available commands").getBytes();
    }


}