package agni.client.view;

import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import agni.client.HeartbeatMonitor;
import agni.client.action.*;
import agni.server.sender.StatusSender.Status;
import charva.awt.BorderLayout;
import charva.awt.Color;
import charva.awt.Container;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charva.awt.event.KeyEvent;
import charva.awt.event.KeyListener;
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

public class LoginView extends JFrame implements AgniClientView, ActionListener, KeyListener {

    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private HeartbeatMonitor serverMonitor;
    private JTextField inputLine;
    private JTextArea outputArea;
    private final int WIDTH = 80;
    private final int HEIGHT = 24;

    public LoginView(LoginActionHandler loginActionHandler,
                     InfoRequestActionHandler infoRequestActionHandler) {
        this.loginActionHandler = loginActionHandler;
        this.infoRequestActionHandler = infoRequestActionHandler;
        this.serverMonitor = new HeartbeatMonitor(5000000000L); //5 second timeout period
        inputLine = new JTextField("", WIDTH - 2);
        outputArea = new JTextArea("", HEIGHT - 5, WIDTH - 2);
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
        contentPane.add(new JLabel("============================================================================="));
        contentPane.add(inputLine, BorderLayout.SOUTH);

        setLocation(0, 0);
        setSize(WIDTH, HEIGHT);
        validate();

        outputArea.setEditable(false);
        inputLine.addKeyListener(this);
        // (new Thread(serverMonitor)).start();
    }

    @Override
    public NextState displayUi() {
        show();
        inputLine.requestFocus();
        appendToOutputArea("Welcome to Agni!");
        return null;
    }

    public synchronized void appendToOutputArea(String message) {
        for (int i = WIDTH - 2; i < message.length(); i += WIDTH - 2) {
            message = message.substring(0, i) + "\n" + message.substring(i, message.length());
        }
        outputArea.append(message + " " + "\n");
        String currentText = outputArea.getText();
        int numlines = currentText.length() - currentText.replace("\n", "").length();
        while (numlines > outputArea.getHeight() - 1) {
            outputArea.setText(currentText.substring(currentText.indexOf("\n") + 1, currentText.length()));
            currentText = outputArea.getText();
            numlines = currentText.length() - currentText.replace("\n", "").length();
        }
    }

    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        if (actionCommand.equals("Exit")) {
            System.gc();
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (inputLine.getText().length() >= WIDTH - 3) {
            inputLine.setText(inputLine.getText().substring(0, WIDTH - 4));
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {

    }

    @Override
    public void keyTyped(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!inputLine.getText().equals("")) {
                handleInput(inputLine.getText());
                inputLine.setText("");
                if(serverMonitor.isTimedOut()){
                    appendToOutputArea(" *** TIMED OUT FROM SERVER ***");
                }
            }
        }
    }

    private void handleInput(String input) {
        if (input.charAt(0) == '/') {
            handleCommand(input.substring(1, input.length()));
        }
        appendToOutputArea("User : " + input);
    }

    private void handleCommand(String input) {
        appendToOutputArea("UserCommand(" + input + ")");
        if (input.equals("q") || input.equals("quit")) {
            System.gc();
            System.exit(0);
        }
    }

    @Override
    public void heartbeatReaction() {
        serverMonitor.receivedHeartbeat();
    }

    @Override
    public void infoReaction(String message) {
        if(message.equals("APPROVED")){
            appendToOutputArea("Login successful!");
        } else {
            appendToOutputArea(" *** SERVER *** \n" + message);
        }
    }

    @Override
    public void statusReaction(String user, Status status) {
        // do nothign in login view
    }

    @Override
    public void chatReaction(String sender, String message) {
        // do nothing in login view
    }

    @Override
    public boolean fileReaction(String filename, String fromUser, int size) {
        return false;
    }
}
