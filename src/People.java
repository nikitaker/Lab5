public abstract class People implements Person {

    String name;

    void go(String place){
        System.out.println(this + " пошел " + place);
    }

    @Override
    public boolean isSeen(People person) {
        return false;
    }

    public boolean isSeen(Karlson person){
        System.out.println(this + " не видел Карлсона");
        return false;
    }

    public void see(String s){
        System.out.println(this + " увидел " + s);
    }

    @Override
    public void talk(People person) {
        System.out.println(this.toString() + " говорит с " + person.toString());
    }

    @Override
    public void scream() {}

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return name.length();
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) return  false;
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        People pep = (People) obj;
        return this.name == pep.name;
    }
}
