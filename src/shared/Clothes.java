package shared;

import java.io.Serializable;

public class Clothes implements Serializable {

    String name;
    String color;
    void dressUp(){
        System.out.println("Карлсон надел" + name + color + "цвета");
    }
    void dressDown(){
        System.out.println("Карлсон снял" + name + color + "цвета");
    }

}
