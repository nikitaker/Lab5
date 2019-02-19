public class Karlson implements Person,Helicopter {

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
