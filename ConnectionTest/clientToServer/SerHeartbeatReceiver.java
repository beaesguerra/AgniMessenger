import java.nio.charset.StandardCharsets;

import java.util.Vector;

public class SerHeartbeatReceiver {

    public SerHeartbeatReceiver() {
    
    }

    public void receiveMessage(String ip, byte[] message) {
        System.out.println("Got a hearbeat" + new String(message, StandardCharsets.US_ASCII));
    }
}
