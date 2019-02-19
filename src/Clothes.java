public class Clothes {

    Task task = new Task(true);

    String name;
    String color;
    void dressUp(){
        System.out.println("Карлсон надел" + name + color + "цвета");
    }
    void dressDown(){
        System.out.println("Карлсон снял" + name + color + "цвета");
    }

}
