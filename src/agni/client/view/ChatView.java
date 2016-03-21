package agni.client.view;

import agni.client.action.*;
import agni.server.sender.StatusSender.Status;

public class ChatView implements AgniClientView {

    private InfoRequestActionHandler infoRequestActionHandler;
    private UserActionHandler userActionHandler;
    private ChatActionHandler chatActionHandler;
    private FileActionHandler fileActionHandler;
    private HeartbeatActionHandler heartbeatActionHandler;

    public ChatView(InfoRequestActionHandler infoRequestActionHandler,
                    UserActionHandler userActionHandler,
                    ChatActionHandler chatActionHandler,
                    FileActionHandler fileActionHandler,
                    HeartbeatActionHandler heartbeatActionHandler) {

        this.infoRequestActionHandler = infoRequestActionHandler;
        this.userActionHandler = userActionHandler;
        this.chatActionHandler = chatActionHandler;
        this.fileActionHandler = fileActionHandler;
        this.heartbeatActionHandler = heartbeatActionHandler;
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


}
