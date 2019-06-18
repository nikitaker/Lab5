package shared;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Karlson implements Person, Helicopter, Comparable<Karlson>, Serializable {


    @Override
    public int compareTo(Karlson karlson) {
        return this.flyspeed.compareTo(karlson.flyspeed);
    }

    public Karlson(String name, Long flyspeed) {
        this.setName(name);
        this.setFlyspeed(flyspeed);
    }

    public Karlson(Karlson karlson) {
        this.setName(karlson.getName());
        this.setFlyspeed(karlson.getFlyspeed());
        this.setY(karlson.getY());
        this.setX(karlson.getX());
        this.setDateTime(karlson.getDateTime());
        this.setOwner(karlson.getOwner());
        this.setClothes(karlson.getClothes());
    }



    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ZonedDateTime getDateTime() {
        return ZonedDateTime.now();
    }

    public int getOwnerId(){
        if(owner != null){
        return (int)Math.round(Math.log(owner.hashCode())*10);}
        else{
            return 1;
        }
    }

    private Integer x;
    private Integer y;
    private ZonedDateTime dateTime;
    private String owner = "all";
    private Clothes clothes;
    private Long flyspeed;
    private String name;

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        if(x == null){
            return 1;
        }else{
        return x;}
    }

    public Integer getY() {
        if(y == null){
            return 1;
        }else{
            return y;}
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        if(owner != null){
        return owner;}
        else{return "admin";}
    }


    @Override
    public boolean isSeen(People person) {
        if (person.toString() == "Малыш")
        {
            swipeHand(person);
            return true;
        }
        return false;
    }

    public void setClothes(Clothes clothes){
        this.clothes = clothes;
    }

    public Clothes getClothes() {
        if(clothes != null){
        return clothes;}
        else {return new Clothes("","");}
    }

    void swipeHand(Object obj){
        System.out.println("Карлсон помахал рукой " + obj.toString());
    }

    @Override
    public void fly(String place) {
        System.out.println("Карлсон полетел к " + place);
    }

    @Override
    public void talk(People person){
        System.out.println("Карлсон сказал:");

    }

    public Long getFlyspeed(){
        return this.flyspeed;
    }
    public void setFlyspeed(Long flyspeed){
        this.flyspeed = flyspeed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name " + this.getName() + ", flyspeed " + this.getFlyspeed() + ", date " + this.getDateTime();
    }

    @Override
    public void scream() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAA");
    }


}
