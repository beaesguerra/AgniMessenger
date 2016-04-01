package agni.client;

import agni.client.communication.*;
import agni.client.receiver.*;
import agni.client.action.*;
import agni.client.view.*;
import charva.awt.BorderLayout;
import charva.awt.Color;
import charva.awt.Container;
import charvax.swing.BoxLayout;
import charvax.swing.JFrame;
import charvax.swing.JLabel;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;
import charvax.swing.JPanel;

// EXIT CODE OF 3 MEANS TIMEOUT FROM SERVER

public class AgniClient {

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

    public AgniClient() {
        messageSender = new MessageSender();
        messageReceiver = new MessageReceiver();

        heartbeatReceiver = new HeartbeatReceiver();
        informationReceiver = new InformationReceiver();
        statusReceiver = new StatusReceiver();
        chatReceiver = new ChatReceiver();
        fileReceiver = new FileReceiver();

        loginActionHandler = new LoginActionHandler(messageSender);
        infoRequestActionHandler = new InfoRequestActionHandler(messageSender);
        userActionHandler = new UserActionHandler(messageSender);
        chatActionHandler = new ChatActionHandler(messageSender);
        fileActionHandler = new FileActionHandler(messageSender);
        heartbeatSender = new HeartbeatSender(messageSender, 500);

        loginView = new LoginView(this,
                                  loginActionHandler,
                                  infoRequestActionHandler);
        idleView = new IdleView(this,
                                infoRequestActionHandler,
                                userActionHandler);
        chatView = new ChatView(this,
                                infoRequestActionHandler,
                                userActionHandler,
                                chatActionHandler,
                                fileActionHandler);
    }

    public void changeState(AgniClientView.NextState nextState) {
        switch (nextState) {
            case LOGIN_VIEW:
                loginView.displayUi();                
                break;
            case IDLE_VIEW:
                idleView.displayUi();                                
                break;
            case CHAT_VIEW:
                chatView.displayUi();                                
                break;
        }
    }

    public void runClient() {

        (new Thread(heartbeatSender)).start();
        loginView.displayUi();
    }

    public static void main(String[] args) {
        AgniClient cl = new AgniClient();
        cl.runClient();
    }
}
