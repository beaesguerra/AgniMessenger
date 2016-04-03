package agni.client.view;

import agni.client.AgniClient;
import agni.client.HeartbeatMonitor;

import agni.client.action.InfoRequestActionHandler;
import agni.client.action.UserActionHandler;
import agni.client.action.InfoRequestActionHandler;
import agni.client.action.LoginActionHandler;
import agni.client.action.ChatActionHandler;
import agni.client.action.FileActionHandler;
import agni.client.action.HeartbeatSender;
import agni.server.sender.StatusSender.Status;
import charva.awt.BorderLayout;
import charva.awt.Container;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charva.awt.event.KeyEvent;
import charva.awt.event.KeyListener;
import charvax.swing.JFrame;
import charvax.swing.JLabel;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;
import charvax.swing.JTextArea;
import charvax.swing.JTextField;

public class LoginView extends JFrame implements AgniClientView, ActionListener, KeyListener {

    private AgniClient client;
    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private HeartbeatMonitor serverMonitor;
    private JTextField inputLine;
    private JTextArea outputArea;
    private final int WIDTH = 80;
    private final int HEIGHT = 24;

    public LoginView(AgniClient client,
                     LoginActionHandler loginActionHandler,
                     InfoRequestActionHandler infoRequestActionHandler) {
        this.client = client;
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
        outputArea.append("Welcome to Agni! Please Login!\nUse the command \n/login username password\nOR\n/register username password\n");
        inputLine.addKeyListener(this);
        // (new Thread(serverMonitor)).start(); // uncomment later
    }

    @Override
    public void displayUi() {
        show();
        inputLine.requestFocus();
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
                if (serverMonitor.isTimedOut()) {
                    appendToOutputArea(" *** TIMED OUT FROM SERVER ***");
                }
            }
        }
    }

    private void handleInput(String input) {
        if (input.charAt(0) == '/' && input.length() > 1) {
            String[] inputArray = input.substring(1, input.length()).split(" ");
            handleCommand(inputArray[0], input.split(" "));
        } else {
            appendToOutputArea("User : " + input);
        }
    }

    private void handleCommand(String input, String[] args) {
        switch (input) {
        case "q":
            System.gc();
            System.exit(0);
            break;
        case "idle":
            client.changeState(AgniClientView.NextState.IDLE_VIEW);
            break;
        case "chat":
            client.changeState(AgniClientView.NextState.CHAT_VIEW);
            break;
        case "login":
            if(args.length < 3){
                appendToOutputArea("Please include a username and password");
            } else {
                appendToOutputArea(" |*| Logging in with  " + args[1] + " and " + args[2]);
                loginActionHandler.requestLogin(args[1], args[2], (byte) 0x00);
            }
            break;
        default:
            appendToOutputArea("Unknown command \"" + input + "\"");
            break;
        }
    }

    @Override
    public void heartbeatReaction() {
        serverMonitor.receivedHeartbeat();
    }

    @Override
    public void infoReaction(String message) {
        if (message.equals("approved")) {
            appendToOutputArea("Login successful!");
        } else {
            appendToOutputArea(" *** SERVER *** \n" + message);
        }
    }

    @Override
    public void statusReaction(String user, Status status) {
        // do nothing in login view
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
