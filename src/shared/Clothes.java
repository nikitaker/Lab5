package shared;

import java.io.Serializable;

public class Clothes implements Serializable {

    String name;
    String color;
    public Clothes(String name, String color)
    {
        this.color =color;
        this.name = name;
    }

    public String getName() {
        if (name != null){
        return name;}
        else {return "";}
    }

    public String getColor() {
        if (color != null){
        return color;}
        else {return "";}
    }

    void dressUp(){
        System.out.println("Карлсон надел" + name + color + "цвета");
    }
    void dressDown(){
        System.out.println("Карлсон снял" + name + color + "цвета");
    }

}
