package agni.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import agni.client.action.ChatActionHandler;
import agni.client.action.FileActionHandler;
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
import agni.client.communication.*;
import agni.client.receiver.*;
import agni.client.action.*;
import agni.client.view.*;

// EXIT CODE OF 3 MEANS TIMEOUT FROM SERVER

public class AgniClient {

    private Socket clientSocket;
    private MessageSender messageSender;
    private MessageReceiver messageReceiver;
    private HeartbeatReceiver heartbeatReceiver;
    private InformationReceiver informationReceiver;
    private StatusReceiver statusReceiver;
    private ChatReceiver chatReceiver;
    private FileReceiver fileReceiver;
    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private UserActionHandler userActionHandler;
    private ChatActionHandler chatActionHandler;
    private FileActionHandler fileActionHandler;
    private HeartbeatSender heartbeatSender;
    private LoginView loginView;
    private IdleView idleView;
    private ChatView chatView;

    public AgniClient(String[] args) throws NumberFormatException, UnknownHostException, IOException {
        clientSocket = new Socket(args[0], Integer.parseInt(args[1]));

        heartbeatReceiver = new HeartbeatReceiver();
        informationReceiver = new InformationReceiver();
        statusReceiver = new StatusReceiver();
        chatReceiver = new ChatReceiver();
        fileReceiver = new FileReceiver();

        messageSender = new MessageSender(clientSocket);
        messageReceiver = new MessageReceiver(clientSocket,
                                              heartbeatReceiver,
                                              informationReceiver,
                                              statusReceiver,
                                              chatReceiver,
                                              fileReceiver);


        loginActionHandler = new LoginActionHandler(messageSender);
        infoRequestActionHandler = new InfoRequestActionHandler(messageSender);
        userActionHandler = new UserActionHandler(messageSender);
        chatActionHandler = new ChatActionHandler(messageSender);
        fileActionHandler = new FileActionHandler(messageSender);
        heartbeatSender = new HeartbeatSender(messageSender, 500);



        loginView = null;
        idleView = null;
        chatView = null;
    }

    public void changeState(AgniClientView.NextState nextState) {
        if(loginView != null){
            heartbeatReceiver.deregister(loginView);
            informationReceiver.deregister(loginView);
            statusReceiver.deregister(loginView);
            chatReceiver.deregister(loginView);
            fileReceiver.deregister(loginView);
        }

        if(idleView != null){
            heartbeatReceiver.deregister(idleView);
            informationReceiver.deregister(idleView);
            statusReceiver.deregister(idleView);
            chatReceiver.deregister(idleView);
            fileReceiver.deregister(idleView);
        }

        if(chatView != null){
            heartbeatReceiver.deregister(chatView);
            informationReceiver.deregister(chatView);
            statusReceiver.deregister(chatView);
            chatReceiver.deregister(chatView);
            fileReceiver.deregister(chatView);
        }

        switch (nextState) {
        case LOGIN_VIEW:
            loginView = new LoginView(this,
                                      loginActionHandler,
                                      infoRequestActionHandler);
            
            heartbeatReceiver.register(loginView);
            informationReceiver.register(loginView);
            statusReceiver.register(loginView);
            chatReceiver.register(loginView);
            fileReceiver.register(loginView);
            loginView.show();
            break;
        case IDLE_VIEW:
            idleView = new IdleView(this,
                                    infoRequestActionHandler,
                                    userActionHandler);
            heartbeatReceiver.register(idleView);
            informationReceiver.register(idleView);
            statusReceiver.register(idleView);
            chatReceiver.register(idleView);
            fileReceiver.register(idleView);
            idleView.show();
            break;
        case CHAT_VIEW:
            chatView = new ChatView(this,
                                    infoRequestActionHandler,
                                    userActionHandler,
                                    chatActionHandler,
                                    fileActionHandler);
            heartbeatReceiver.register(chatView);
            informationReceiver.register(chatView);
            statusReceiver.register(chatView);
            chatReceiver.register(chatView);
            fileReceiver.register(chatView);
            chatView.show();
            break;
        }
    }

    public void runClient() {
        (new Thread(messageReceiver)).start();
        changeState(AgniClientView.NextState.LOGIN_VIEW);
        heartbeatSender.run();
        // We need to run messageReceiver somewhere here

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Client <Server IP> <Server Port>");
            System.exit(1);
        }
        
        try {
            AgniClient cl = new AgniClient(args);
            cl.runClient();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
