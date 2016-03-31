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

public class AgniClient extends JFrame{

    public AgniClient() {
        setForeground(Color.green);
        setBackground(Color.black);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JMenuBar menubar = new JMenuBar();
        JMenu jMenuFile = new JMenu("File");
        JMenuItem jMenuItemFileExit = new JMenuItem("Exit", 'x');
        jMenuFile.add(jMenuItemFileExit);

        menubar.add(jMenuFile);

        setJMenuBar(menubar);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(new JLabel("INPUT BOX:"));
        labelPanel.add(new JLabel("test"));
        contentPane.add(labelPanel, BorderLayout.SOUTH);
        setLocation(0, 0);
        setSize(80, 24);
        validate();    }

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
        AgniClient testwin = new AgniClient();
        testwin.show();

    }

}
