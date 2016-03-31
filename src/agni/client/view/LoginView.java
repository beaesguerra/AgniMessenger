package agni.client.view;

import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import agni.client.action.*;
import agni.server.sender.StatusSender.Status;
import charva.awt.BorderLayout;
import charva.awt.Color;
import charva.awt.Container;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.BoxLayout;
import charvax.swing.JFrame;
import charvax.swing.JLabel;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;
import charvax.swing.JPanel;
import charvax.swing.JTextArea;
import charvax.swing.JTextField;
import charvax.swing.SwingConstants;

public class LoginView extends JFrame implements AgniClientView, ActionListener {

    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private HeartbeatActionHandler heartbeatActionHandler;
    private JTextField inputLine;
    private JTextArea outputArea;
    private final int WIDTH = 80;
    private final int HEIGHT = 24;

    public LoginView(LoginActionHandler loginActionHandler,
                     InfoRequestActionHandler infoRequestActionHandler,
                     HeartbeatActionHandler heartbeatActionHandler) {

        this.loginActionHandler = loginActionHandler;
        this.infoRequestActionHandler = infoRequestActionHandler;
        this.heartbeatActionHandler = heartbeatActionHandler;
        inputLine = new JTextField("", WIDTH - 2);
        outputArea = new JTextArea("", HEIGHT - 5, WIDTH - 2);
        outputArea.setEditable(false);
        setupUi();
    }

    private void setupUi() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JMenuBar menubar = new JMenuBar();
        JMenu jMenuFile = new JMenu("Agni");
        JMenuItem jMenuItemFileExit = new JMenuItem("Exit");
        jMenuItemFileExit.addActionListener((ActionListener) this);
        jMenuFile.add(jMenuItemFileExit);

        menubar.add(jMenuFile);

        setJMenuBar(menubar);

        contentPane.add(outputArea, BorderLayout.NORTH);
        contentPane.add(new JLabel("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
        contentPane.add(inputLine, BorderLayout.SOUTH);
        setLocation(0, 0);
        setSize(WIDTH, HEIGHT);
        validate();
    }

    public void appendToOutputArea(String message) {
    	outputArea.append(message + "\n");
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
        inputLine.requestFocus();
        appendToOutputArea("Welcome to Agni!");
        appendToOutputArea("SampleDude : Hey nice to meet you!");
        appendToOutputArea("SomeDude : Multiple lines? \n yea?");
        return null;
    }

}
