package shared;

public class Task {
    static public class StaticNestedClass{}

    public class NestedClass{}

    public void method(){

        class LocalClass{}
        new Task(true){};
    }

    Task(boolean anonymous){}
}
