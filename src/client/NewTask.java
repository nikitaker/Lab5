package client;

import shared.Karlson;

import java.sql.*;

public class NewTask {

    private static String url = "jdbc:postgresql://localhost:5432/studs";
    private static String name = "studs";
    private static String pass = "555";

    public static void main(String[] args) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, name, pass);
            Karlson karlson = new Karlson("kilo", 1488L);
            int skillId = (int) Math.round(Math.random() * 10000000);
            PreparedStatement preStatement = connection.prepareStatement("INSERT INTO karlson VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?) RETURNING id;");
            //preStatement.setLong();
            preStatement.setString(1, karlson.getName());
            preStatement.setLong(2, karlson.getFlyspeed());
            preStatement.setString(3, "nick");
            preStatement.setDate(4, Date.valueOf(karlson.getDateTime().toLocalDate()));
            preStatement.setInt(5, skillId);
            preStatement.setInt(6, karlson.getX());
            preStatement.setInt(7, karlson.getY());
            ResultSet result = preStatement.executeQuery();
            result.next();
            System.out.println(result.getInt(1));
        } catch (Exception e){e.printStackTrace();}
    }
}
