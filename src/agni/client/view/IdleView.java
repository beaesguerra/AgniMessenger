package agni.client.view;

import agni.client.action.*;
import agni.server.sender.StatusSender.Status;

public class IdleView implements AgniClientView {

    private InfoRequestActionHandler infoRequestActionHandler;
    private UserActionHandler userActionHandler;

    public IdleView(InfoRequestActionHandler infoRequestActionHandler,
                    UserActionHandler userActionHandler) {

        this.infoRequestActionHandler = infoRequestActionHandler;
        this.userActionHandler = userActionHandler;
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
        return null;
        // TODO Auto-generated method stub

    }

}
