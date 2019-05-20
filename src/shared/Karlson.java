package shared;

import shared.People;
import shared.Person;
import shared.State;

import java.io.Serializable;
import java.util.Date;

public class Karlson implements Person, Helicopter, Comparable<Karlson>, Serializable {


    @Override
    public int compareTo(Karlson karlson) {
        return this.flyspeed.compareTo(karlson.flyspeed);
    }

    public Karlson(String name, Long flyspeed) {
        this.setName(name);
        this.setFlyspeed(flyspeed);

    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    private Date dateTime;
    State state;
    Clothes clothes = new Clothes();
    Long flyspeed;
    String name;

    void dress(){state = State.WITCH;
        System.out.println("Карлсон переоделся в ведьму");}
    void undress(){state = State.KARLSON;
        System.out.println("Ведьма сорвала плащ и превратилась в Карлсона");}

    @Override
    public boolean isSeen(People person) {
        if (person.toString() == "Малыш")
        {
            swipeHand(person);
            return true;
        }
        return false;
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

    void state(){
        if(state == State.WITCH){
            System.out.println("Карлсон был похож на маленькую ведьму: лицо, вымазанное черным, на голове косынка, а на плечах развевался ведьминский плащ");
        }
    }

    void dissapoint(String reason){
        System.out.println("Карлсон раздраженно покачал головой так как " + reason);
    }
}
