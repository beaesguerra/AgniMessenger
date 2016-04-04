import java.nio.charset.StandardCharsets;

public class CliHeartbeatReceiver {

    public CliHeartbeatReceiver() {}

    public void receiveMessage(byte[] message) {
        System.out.println("Got a hearbeat" + new String(message, StandardCharsets.US_ASCII));
    }
}
