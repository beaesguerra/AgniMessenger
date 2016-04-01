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

    public AgniClient() {
    }

    public static void main(String[] args) {
        MessageSender messageSender = new MessageSender();
        MessageReceiver messageReceiver = new MessageReceiver();

        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver();
        InformationReceiver informationReceiver = new InformationReceiver();
        StatusReceiver statusReceiver = new StatusReceiver();
        ChatReceiver chatReceiver = new ChatReceiver();
        FileReceiver fileReceiver = new FileReceiver();

        LoginActionHandler loginActionHandler = new LoginActionHandler(messageSender);
        InfoRequestActionHandler infoRequestActionHandler = new InfoRequestActionHandler(messageSender);
        UserActionHandler userActionHandler = new UserActionHandler(messageSender);
        ChatActionHandler chatActionHandler = new ChatActionHandler(messageSender);
        FileActionHandler fileActionHandler = new FileActionHandler(messageSender);
        HeartbeatSender heartbeatSender = new HeartbeatSender(messageSender, 500);

        LoginView loginView = new LoginView(loginActionHandler,
                                            infoRequestActionHandler);
        IdleView idleView = new IdleView(infoRequestActionHandler,
                                         userActionHandler);
        ChatView chatView = new ChatView(infoRequestActionHandler,
                                         userActionHandler,
                                         chatActionHandler,
                                         fileActionHandler);

        (new Thread(heartbeatSender)).start();
        loginView.displayUi();
    }
}
