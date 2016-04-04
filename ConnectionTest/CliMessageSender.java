import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CliMessageSender {
    private Socket tcpSocket;
    private DataOutputStream dataOut = null;

    public CliMessageSender(Socket clientSocket) {
        tcpSocket = clientSocket;
        OutputStream out;
        try {
            out = tcpSocket.getOutputStream();
            dataOut = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendMessage(byte[] message) {
        int length = message.length;
        try {
            dataOut.write(message, 0, length);
            dataOut.flush();
        } catch (IOException e) {
            System.out.println("Error writing to the DataOutputStream");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
        } catch (UnknownHostException e) {
            System.out.println("UNKNOWN HOST");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e){
            System.out.println("IOException");
            e.printStackTrace();
            System.exit(1);
        }

        CliMessageSender messageSender = new CliMessageSender(clientSocket);
        CliHeartbeatSender heartbeatSender = new CliHeartbeatSender(messageSender, 1000);
        heartbeatSender.run();
    }
}
