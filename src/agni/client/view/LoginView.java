package agni.client.view;

import agni.client.action.*;
import agni.server.sender.StatusSender.Status;
import charva.awt.BorderLayout;
import charva.awt.Color;
import charva.awt.Container;
import charva.awt.event.ActionEvent;
import charvax.swing.BoxLayout;
import charvax.swing.JFrame;
import charvax.swing.JLabel;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;
import charvax.swing.JPanel;

public class LoginView extends JFrame implements AgniClientView {

    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private HeartbeatActionHandler heartbeatActionHandler;

    public LoginView(LoginActionHandler loginActionHandler,
                     InfoRequestActionHandler infoRequestActionHandler,
                     HeartbeatActionHandler heartbeatActionHandler) {

        this.loginActionHandler = loginActionHandler;
        this.infoRequestActionHandler = infoRequestActionHandler;
        this.heartbeatActionHandler = heartbeatActionHandler;
        setupUi();
    }

    private void setupUi() {
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
        validate();
    }

    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        if (actionCommand.equals("Exit")) {
            System.gc(); 
            System.exit(0);
        }
    }

    @Override
    public void heartbeatReaction() {
        // TODO Auto-generated method stub

    }

    @Override
    public void infoReaction(String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void statusReaction(String user, Status status) {
        // TODO Auto-generated method stub

    }

    @Override
    public void chatReaction(String sender, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean fileReaction(String filename, String fromUser, int size) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public NextState displayUi() {
        show();
        return null;
    }

}
