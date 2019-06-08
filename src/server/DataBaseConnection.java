package server;

import shared.*;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Random;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;


public class DataBaseConnection {
    private String url = "jdbc:postgresql://localhost:5432/studs";
    private String name = "studs";
    private String pass = "555";
    private Connection connection = null;
    private CommandHandler command;

    {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Installed Driver");
            connection = DriverManager.getConnection(url, name, pass);
            System.out.println("The Connection is successfully established\n");
            command = new CommandHandler();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't connect to the database");
        }
    }

    public int loadPersons(ConcurrentHashMap<Long,Karlson> storage) {
        try {
            int i = 0;
            //OffsetDateTime time = OffsetDateTime.now();
            ZonedDateTime time = ZonedDateTime.now();
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM \"karlson\";");
            PreparedStatement getClothes;
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String name = result.getString("name");
                long flyspeed = result.getLong("flyspeed");
                int clothesId = result.getInt("clothesId");
                String date = result.getString("date");
                if (date != null) {
                    time = ZonedDateTime.parse(result.getString("date").replace(' ','T')+".147Z");
                }
                Karlson karlson = new Karlson(name,flyspeed);
                karlson.setDateTime(time);
                karlson.setOwner(username);

                getClothes = connection.prepareStatement("SELECT * FROM \"clothes\" WHERE id=?");
                getClothes.setInt(1,clothesId);
                ResultSet resSkill = getClothes.executeQuery();
                if (resSkill.next()) {
                    karlson.setClothes(new Clothes(resSkill.getString("name"), resSkill.getString("color")));
                }
                storage.put(karlson.getFlyspeed(),karlson);
                i++;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error whilst adding Humans");
            return -1;
        }
    }

    public void addToDB(Karlson karlson, String username) {
        try {
            addHuman(karlson, username);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while adding a human to a DataBase");
        }
    }

    private void addHuman(Karlson karlson, String username) throws SQLException {
        int skillId = karlson.getDateTime().getNano();
        PreparedStatement preStatement = connection.prepareStatement("INSERT INTO karlson VALUES (?, ?, ?, ?, ?, ?);");
        preStatement.setLong(1,karlson.getFlyspeed().hashCode());
        preStatement.setString(2,karlson.getName());
        preStatement.setLong(3,karlson.getFlyspeed());
        preStatement.setString(4,username);
        preStatement.setDate(5,Date.valueOf(karlson.getDateTime().toLocalDate()));
        preStatement.setInt(6,skillId);
        preStatement.executeUpdate();
        if (karlson.getClothes().getName() != null) {
            PreparedStatement statementSkills = connection.prepareStatement("INSERT INTO clothes VALUES (?, ?, ?);");
            statementSkills.setInt(1, skillId);
            statementSkills.setString(2, karlson.getClothes().getName());
            statementSkills.setString(3, karlson.getClothes().getColor());
            statementSkills.executeUpdate();
        }
    }

    public void savePersons(ConcurrentHashMap<Long,Karlson>  map) {
        try {
            if (map != null) {

                Iterator<Karlson> iterator = map.values().iterator();

                while (iterator.hasNext()) {
                    Karlson karlson = iterator.next();
                    addHuman(karlson, karlson.getOwner());
                }
                System.out.println("The DataBase has been updated.");
            } else {
                System.out.println("Collection is empty; nothing to save!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst saving Humans to the DataBase");
        }
    }

    public int executeLogin(String login, String hash) {
        try {
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM users WHERE username=? and hash=?;");
            preStatement.setString(1,login);
            preStatement.setString(2,hash);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) return 0;
            else {
                PreparedStatement preStatement2 = connection.prepareStatement("SELECT * FROM users WHERE username=?;");
                preStatement2.setString(1,login);
                ResultSet result2 = preStatement2.executeQuery();
                if (result2.next()) return 2;
                else return 1;
            }
        } catch (Exception e) {
            System.out.println("Login error");
            return -1;
        }
    }


    public boolean removePerson(String username, Karlson karlson) {
        try {
            String skills = !karlson.getClothes().toString().equals("[]") && karlson.getClothes().toString() != null ? karlson.getClothes().toString() : "NULL";
            PreparedStatement preStatement = connection.prepareStatement(("DELETE FROM karlson WHERE name=? AND username=? AND flyspeed=? AND clothes=?;"));
            preStatement.setString(1, karlson.getName());
            preStatement.setString(2,username);
            preStatement.setLong(3,karlson.getFlyspeed());
            preStatement.setString(4,skills);
            preStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error while removing a human from a DataBase");
            return false;
        }
    }


    public int executeRegister(String login, String mail, String pass) {
        try {

            PreparedStatement ifLog = connection.prepareStatement("SELECT * FROM users WHERE username=?;");
            ifLog.setString(1,login);
            ResultSet result = ifLog.executeQuery();
            if (result.next()){return 0;}
            String hash = DataBaseConnection.encryptString(pass);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?);");
            statement.setString(1, login);
            statement.setString(2, mail);
            statement.setString(3, hash);
            statement.executeUpdate();
            new Thread(() -> JavaMail.registration(mail, pass)).start();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst registration");
            return -1;
        }
    }


    public static String getToken() {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 8;
            Random random = new Random(); // get random string
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }

            return buffer.toString();

        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptString(String input)
    {
        try {
            // getInstance() method is called with algorithm MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into sign representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashText = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            // return the HashText
            return hashText.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Error whilst generating hashText of password");
            e.getMessage();
            return null;
        }
    }

}