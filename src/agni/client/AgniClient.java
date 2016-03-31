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
        jMenuFile.setMnemonic('F');

        JMenuItem jMenuItemFileChooser = new JMenuItem("JFileChooser", 'F');
        jMenuFile.add(jMenuItemFileChooser);

        JMenuItem jMenuItemCustomFileChooser = new JMenuItem("custom FileChooser", 'c');
        jMenuFile.add(jMenuItemCustomFileChooser);

        jMenuFile.addSeparator();

        JMenuItem jMenuItemFileExit = new JMenuItem("Exit", 'x');
        jMenuFile.add(jMenuItemFileExit);

        JMenu jMenuLayout = new JMenu("Layouts");
        jMenuLayout.setMnemonic('L');
        JMenuItem jMenuItemLayoutNull = new JMenuItem("Null Layout");
        jMenuItemLayoutNull.setMnemonic('N');
        jMenuLayout.add(jMenuItemLayoutNull);

        jMenuLayout.addSeparator();

        JMenuItem jMenuItemLayoutMisc = new JMenuItem("Miscellaneous Layouts");
        jMenuItemLayoutMisc.setMnemonic('M');
        jMenuLayout.add(jMenuItemLayoutMisc);

        JMenuItem jMenuItemLayoutColor = new JMenuItem("Layouts in Color");
        jMenuItemLayoutColor.setMnemonic('C');
        jMenuLayout.add(jMenuItemLayoutColor);

        JMenuItem jMenuItemLayoutGBL = new JMenuItem("GridBagLayout");
        jMenuItemLayoutGBL.setMnemonic('G');
        jMenuLayout.add(jMenuItemLayoutGBL);

        JMenu jMenuContainers = new JMenu("Containers");
        jMenuContainers.setMnemonic('C');

        JMenuItem jMenuItemContainerJTabbedPane = new JMenuItem("JTabbedPane");
        jMenuItemContainerJTabbedPane.setMnemonic('T');
        jMenuContainers.add(jMenuItemContainerJTabbedPane);

        JMenu jMenuItemContainerJOptionPane = new JMenu("JOptionPane...");
        jMenuItemContainerJOptionPane.setMnemonic('O');
        jMenuContainers.add(jMenuItemContainerJOptionPane);

        JMenuItem jMenuItemShowMessageDialog =
                new JMenuItem("showMessageDialog");
        jMenuItemContainerJOptionPane.add(jMenuItemShowMessageDialog);

        JMenuItem jMenuItemShowConfirmDialog =
                new JMenuItem("showConfirmDialog");
        jMenuItemContainerJOptionPane.add(jMenuItemShowConfirmDialog);

        JMenuItem jMenuItemShowInputDialog =
                new JMenuItem("showInputDialog");
        jMenuItemContainerJOptionPane.add(jMenuItemShowInputDialog);

        JMenuItem jMenuItemShowCustomInputDialog =
                new JMenuItem("show Custom InputDialog");
        jMenuItemContainerJOptionPane.add(jMenuItemShowCustomInputDialog);

        JMenu jMenuWidgets = new JMenu("Widgets");
        jMenuWidgets.setMnemonic('W');

        JMenuItem jMenuItemWidgetText = new JMenuItem("Text components");
        jMenuItemWidgetText.setMnemonic('T');
        jMenuWidgets.add(jMenuItemWidgetText);

        JMenuItem jMenuItemWidgetSelection = new JMenuItem("Selection components");
        jMenuItemWidgetSelection.setMnemonic('S');
        jMenuWidgets.add(jMenuItemWidgetSelection);

        JMenuItem jMenuItemWidgetButtons = new JMenuItem("Buttons");
        jMenuItemWidgetButtons.setMnemonic('B');
        jMenuWidgets.add(jMenuItemWidgetButtons);

        JMenuItem jMenuItemWidgetJTable = new JMenuItem("JTable");
        jMenuItemWidgetJTable.setMnemonic('J');
        jMenuWidgets.add(jMenuItemWidgetJTable);

        JMenu jMenuEvents = new JMenu("Events");
        jMenuEvents.setMnemonic('E');

        JMenuItem jMenuItemKeyEvents = new JMenuItem("KeyEvents");
        jMenuItemKeyEvents.setMnemonic('K');
        jMenuEvents.add(jMenuItemKeyEvents);

        JMenuItem jMenuItemFocusEvents = new JMenuItem("FocusEvents");
        jMenuItemFocusEvents.setMnemonic('F');
        jMenuEvents.add(jMenuItemFocusEvents);

        JMenu jMenuThreads = new JMenu("Threads");
        jMenuThreads.setMnemonic('T');

        JMenuItem jMenuItemProgressBar = new JMenuItem("JProgressBar");
        jMenuItemProgressBar.setMnemonic('P');
        jMenuThreads.add(jMenuItemProgressBar);

        menubar.add(jMenuFile);
        menubar.add(jMenuLayout);
        menubar.add(jMenuContainers);
        menubar.add(jMenuWidgets);
        menubar.add(jMenuEvents);
        menubar.add(jMenuThreads);

        setJMenuBar(menubar);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(new JLabel("Use LEFT and RIGHT cursor keys to select a menu."));
        labelPanel.add(new JLabel("Use ENTER to invoke a menu or menu-item."));
        labelPanel.add(new JLabel("(You can also use the " +
                "underlined \"mnemonic key\" to invoke a menu.)"));
        labelPanel.add(new JLabel("Use BACKSPACE or ESC to dismiss a menu."));
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
