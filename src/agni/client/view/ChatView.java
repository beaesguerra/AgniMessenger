package agni.client.view;

import agni.client.AgniClient;
import agni.client.HeartbeatMonitor;
import agni.client.action.*;
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

public class ChatView extends JFrame implements AgniClientView, ActionListener, KeyListener {

    private AgniClient client;
    private InfoRequestActionHandler infoRequestActionHandler;
    private UserActionHandler userActionHandler;
    private ChatActionHandler chatActionHandler;
    private FileActionHandler fileActionHandler;
    private HeartbeatMonitor serverMonitor;
    private JTextField inputLine;
    private JTextArea outputArea;
    private final int WIDTH = 80;
    private final int HEIGHT = 24;

    public ChatView(AgniClient client,
                    InfoRequestActionHandler infoRequestActionHandler,
                    UserActionHandler userActionHandler,
                    ChatActionHandler chatActionHandler,
                    FileActionHandler fileActionHandler) {

        this.client = client;
        this.infoRequestActionHandler = infoRequestActionHandler;
        this.userActionHandler = userActionHandler;
        this.chatActionHandler = chatActionHandler;
        this.fileActionHandler = fileActionHandler;
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
        contentPane.add(new JLabel(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"));
        contentPane.add(inputLine, BorderLayout.SOUTH);

        setLocation(0, 0);
        setSize(WIDTH, HEIGHT);
        validate();

        outputArea.setEditable(false);
        inputLine.addKeyListener(this);
        // (new Thread(serverMonitor)).start();
    }

    @Override
    public void displayUi() {
        show();
        inputLine.requestFocus();
        appendToOutputArea("ChatView!");
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
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

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
        if (input.charAt(0) == '/') {
            handleCommand(input.substring(1, input.length()));
        } else {
            appendToOutputArea("User : " + input);
        }
    }

    private void handleCommand(String input) {
        switch (input) {
        case "q":
            System.gc();
            System.exit(0);
            break;
        case "login":
            this.hide();
            client.changeState(AgniClientView.NextState.LOGIN_VIEW);
            break;
        case "idle":
            this.hide();
            client.changeState(AgniClientView.NextState.IDLE_VIEW);
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
}
