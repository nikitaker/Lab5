package server;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import shared.*;
import shared.FileReader;

import javax.swing.text.Document;

public class CommandHandler extends Thread {

    private InetAddress inetAddress;
    private int port;
    public static String fileName;
    private static String FILEPATH = "karlson.json";
    private Object response;
    private ConcurrentHashMap<Long,Karlson> starthashMap;
    private Command startComand;

    public CommandHandler(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public CommandHandler() {

    }

    public void setStart(Command command, ConcurrentHashMap<Long,Karlson> map){
        this.startComand = command;
        this.starthashMap = map;
    }

    @Override
    public void run() {
         this.response = handleCommand(startComand,starthashMap);
    }

    public Object getResponse() {
        return response;
    }

    public Object handleCommand(Command com, ConcurrentHashMap<Long,Karlson> storage) {
        synchronized (CommandHandler.class) {
            String command = com.getCommand();
            Object data = com.getData();

            byte[] buffer;

            switch (command.toLowerCase()) {
                case "connecting":
                    buffer = "connected".getBytes();
                    break;
                case "add":
                    if (data != null) {
                        buffer = add(storage, (Karlson) data);
                    } else {buffer = help();}
                    break;
                case "add_if_min":
                    if (data != null) {
                        buffer = add_if_min(storage, (Karlson) data);
                    } else {buffer = help();}
                    break;
                case "add_if_max":
                    if (data != null) {
                        buffer = add_if_max(storage, (Karlson) data);
                    } else {buffer = help();}
                    break;
                case "remove":
                    if (data != null) {
                        buffer = remove(storage, (Karlson) data);
                    } else {buffer = help();}
                    break;
                case "remove_lower":
                    if (data != null) {
                        buffer = remove_lower(storage, (Karlson) data);
                    } else {buffer = help();}
                    break;
                case "show":
                    buffer = show(storage);
                    break;
                case "save":
                    buffer = save(storage);
                    break;
                case "import":
                    buffer = import1(storage, (ConcurrentHashMap<Long,Karlson>) data);
                    break;
                case "info":
                    buffer = info(storage);
                    break;
                case "help":
                    buffer = help();
                    break;
                default:
                    buffer = "Error: undefined command! Type \"help\" for a list of available commands".getBytes();
            }
            return buffer;
        }
    }


    public byte[] show(ConcurrentHashMap<Long,Karlson> storage) {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator iterator = storage.values().iterator();
            while (iterator.hasNext())
            {
                Karlson karlson = (Karlson) iterator.next();
                stringBuilder.append("Ключ = " + karlson.getFlyspeed() + " Имя = " + karlson.getName() + " Дата создания = " + karlson.getDateTime() + " \n");
            }

            return stringBuilder.toString().getBytes();
        } catch (Exception e) {
            System.err.println("Aliens snatched the collection! Can't show it.");
            e.printStackTrace();
        }

        return null;

    }

    public byte[] save(ConcurrentHashMap<Long,Karlson> storage) {

        return FileReader.endProg(storage, FILEPATH);

    }

    public byte[] add(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson) {
        synchronized (storage) {
            if (storage.put(karlson.getFlyspeed(),karlson) == null) {
                System.out.println("A karlson " + karlson.toString()+ " was successfully added.");
                return ("A karlson " + karlson.toString()+ " was successfully added.").getBytes();
            } else {
                return (storage.put(karlson.getFlyspeed(),karlson) + "was replaced").getBytes();
            }
        }
    }

    public byte[] add_if_min(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson) {
        if (storage.size() > 0) {
            synchronized (storage.entrySet()) {
                Karlson min = storage
                        .values().stream()
                        .min(Karlson::compareTo)
                        .get();
                if (karlson.compareTo(min) < 0) {
                    return add(storage, karlson);
                } else {
                    return (karlson.getName()+ "'s name isn't the smallest: Can't add to a collection!").getBytes();
                }
            }
        } else {
            return add(storage, karlson);
        }
    }

    public byte[] add_if_max(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson) {
        if (storage.size() > 0) {
            synchronized (storage.entrySet()) {
                Karlson max = storage
                        .values().stream()
                        .max(Karlson::compareTo)
                        .get();
                if (karlson.compareTo(max) > 0) {
                    return add(storage, karlson);
                } else {
                    return (karlson.getName()+ "'s name isn't the biggest: Can't add to a collection!").getBytes();
                }
            }
        } else {
            return add(storage, karlson);
        }
    }

    public byte[] import1(ConcurrentHashMap<Long,Karlson> storage, ConcurrentHashMap<Long,Karlson> importing) {
        long start = storage.size();
        Collection<Karlson>imported = importing.values();
        for (Karlson karlson: imported) {
            add(storage, karlson);
        }
        long end = storage.size();
        System.out.println("Imported "+ (end-start) + " objects.");
        return ("+++++ Imported "+ (end-start) + " objects +++++").getBytes();
    }

    public byte[] info(ConcurrentHashMap<Long,Karlson> storage) {
        return ("Collection is a " + storage.getClass() + " type.\n" +
                "Currently it contains " + storage.size() + " objects.").getBytes();
    }

    public byte[] remove(ConcurrentHashMap<Long,Karlson> storage, Karlson karlson){
        long start = storage.size();
        synchronized(storage) {
            storage.entrySet()
                    .removeIf(entry -> (karlson.getFlyspeed().equals(entry.getKey())));
        }
        long end = storage.size();
        if ((start - end) > 0) {
            System.out.println("A karlson \"" + karlson.toString() + "\" has been deleted");
            return ("A karlson \"" + karlson.toString() + "\" has been deleted :(").getBytes();
        } else {return "There's no such object in the collection. Try adding instead.".getBytes();}
    }

    public byte[] remove_lower(ConcurrentHashMap<Long,Karlson> storage, Karlson endObject) {


        long start = storage.size();
        synchronized(storage) {
            storage.entrySet().removeIf(item -> item.getValue().compareTo(endObject) > 0);
        }
        long end = storage.size();


        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();

    }

    public byte[] help() {
        String jsonExample = "\r\n{\r\n   \"name\": \"Elizabeth\",\r\n   \"age\": \"16\",\r\n   \"skill\": {\r\n      \"name\": \"\u041F\u0440\u044B\u0433\u0430\u0442\u044C\"\r\n   },\r\n   \"disability\": \"chin\"\r\n}\r";

        return ("\nAvailable commands: \n" +
                "Example of JSON Karlson declaration:" + jsonExample +
                "\n* add {element} - adds an element to collection, element - is a JSON string, see above" +
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