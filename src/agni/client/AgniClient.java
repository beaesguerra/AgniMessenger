package agni.client;

import agni.client.communication.*;
import agni.client.receiver.*;
import agni.client.action.*;
import agni.client.view.*;
import javax.swing.SwingUtilities;

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

        loginView = null;
        idleView = null;
        chatView = null;
    }

    public void changeState(AgniClientView.NextState nextState) {
        switch (nextState) {
        case LOGIN_VIEW:
            loginView = new LoginView(this,
                                      loginActionHandler,
                                      infoRequestActionHandler);
            loginView.show();
            break;
        case IDLE_VIEW:
            idleView = new IdleView(this,
                                    infoRequestActionHandler,
                                    userActionHandler);
            idleView.show();
            break;
        case CHAT_VIEW:
            chatView = new ChatView(this,
                                    infoRequestActionHandler,
                                    userActionHandler,
                                    chatActionHandler,
                                    fileActionHandler);
            chatView.show();
            break;
        }
    }

    public void runClient() {
        (new Thread(heartbeatSender)).start();
        loginView = new LoginView(this,
                                  loginActionHandler,
                                  infoRequestActionHandler);
        loginView.displayUi();
    }

    public static void main(String[] args) {
        AgniClient cl = new AgniClient();
        cl.runClient();
    }
}
