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
        return name;
    }

    public String getColor() {
        return color;
    }

    void dressUp(){
        System.out.println("Карлсон надел" + name + color + "цвета");
    }
    void dressDown(){
        System.out.println("Карлсон снял" + name + color + "цвета");
    }

}
