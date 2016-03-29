package agni.client;

import agni.client.communication.*;
import agni.client.receiver.*;
import java.net.Socket;
import agni.client.action.*;
import agni.client.view.*;

public class AgniClient {
	
    public AgniClient() {
    	
    }

    public static void main(String[] args) throws Exception {
    	
    	if (args.length != 2) {
            System.out.println("Usage: Client <Server IP> <Server Port>");
            System.exit(1);
        }

        // Initialize a client socket connection to the server
        Socket clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
        
        MessageSender messageSender = new MessageSender(clientSocket);
        MessageReceiver messageReceiver = new MessageReceiver(clientSocket);

        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver();
        InformationReceiver informationReceiver = new InformationReceiver();
        StatusReceiver statusReceiver = new StatusReceiver();
        ChatReceiver chatReceiver = new ChatReceiver();
        FileReceiver fileReceiver = new FileReceiver();

        LoginActionHandler loginActionHandler = new LoginActionHandler(messageSender);
        InfoRequestActionHandler infoRequestActionHandler = new InfoRequestActionHandler(messageSender);
        HeartbeatActionHandler heartbeatActionHandler = new HeartbeatActionHandler(messageSender);
        UserActionHandler userActionHandler = new UserActionHandler(messageSender);
        ChatActionHandler chatActionHandler = new ChatActionHandler(messageSender);
        FileActionHandler fileActionHandler = new FileActionHandler(messageSender);

        LoginView loginView = new LoginView(loginActionHandler,
                                            infoRequestActionHandler,
                                            heartbeatActionHandler);
        IdleView idleView = new IdleView(infoRequestActionHandler,
                                         userActionHandler,
                                         heartbeatActionHandler);
        ChatView chatView = new ChatView(infoRequestActionHandler,
                                         userActionHandler,
                                         chatActionHandler,
                                         fileActionHandler,
                                         heartbeatActionHandler);

    }

}
