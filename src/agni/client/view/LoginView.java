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
        inputLine.addKeyListener(this);
        inputLine.addActionListener(this);
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
        contentPane.add(new JLabel("============================================================================="));
        contentPane.add(inputLine, BorderLayout.SOUTH);
        setLocation(0, 0);
        setSize(WIDTH, HEIGHT);
        validate();
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

    public void appendToOutputArea(String message) {
        for (int i = WIDTH - 2; i < message.length(); i += WIDTH-2) {
            message = message.substring(0, i) + "\n" + message.substring(i, message.length());
        }
        outputArea.append(message + " " + "\n");
        String currentText = outputArea.getText();
        int numlines = currentText.length() - currentText.replace("\n", "").length();
        if(numlines > outputArea.getHeight() - 1){
            // currentText = outputArea.getText();
            // numlines = currentText.length() - currentText.replace("\n", "").length();
            outputArea.setText(currentText.substring(currentText.indexOf("\n") + 1, currentText.length()));
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
            if(!inputLine.getText().equals("")){
                handleInput(inputLine.getText());
                inputLine.setText("");
            }
        }
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
        return null;
    }
}
