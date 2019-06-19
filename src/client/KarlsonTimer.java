package client;

import java.util.TimerTask;

public class KarlsonTimer extends TimerTask {
    public void run() {
        GUIHand.generateStorage();
        GUIHand.show();

        System.out.println("Storage updated");
    }
}
