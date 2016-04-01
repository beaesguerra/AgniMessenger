package agni.client;

import java.io.IOException;
import java.net.Socket;
import agni.client.action.ChatActionHandler;
import agni.client.action.FileActionHandler;
import agni.client.action.HeartbeatActionHandler;
import agni.client.action.InfoRequestActionHandler;
import agni.client.action.LoginActionHandler;
import agni.client.action.UserActionHandler;
import agni.client.communication.MessageReceiver;
import agni.client.communication.MessageSender;
import agni.client.receiver.ChatReceiver;
import agni.client.receiver.FileReceiver;
import agni.client.receiver.HeartbeatReceiver;
import agni.client.receiver.InformationReceiver;
import agni.client.receiver.StatusReceiver;
import agni.client.view.ChatView;
import agni.client.view.IdleView;
import agni.client.view.LoginView;

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
       
        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver();
        InformationReceiver informationReceiver = new InformationReceiver();
        StatusReceiver statusReceiver = new StatusReceiver();
        ChatReceiver chatReceiver = new ChatReceiver();
        FileReceiver fileReceiver = new FileReceiver();
        
        MessageSender messageSender = new MessageSender(clientSocket);
        MessageReceiver messageReceiver = new MessageReceiver(clientSocket,
                                                              heartbeatReceiver,
                                                              informationReceiver,
                                                              statusReceiver,
                                                              chatReceiver,
                                                              fileReceiver);
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
        // We need to run messageReceiver somewhere here
        // messageReceiver.run();
        // After termination functionality
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
