package agni.client.view;

import agni.client.receiver.ReceiverListener;

public interface AgniClientView extends ReceiverListener {
    public enum NextState {
        LOGIN_VIEW, 
        IDLE_VIEW, 
        CHAT_VIEW;
    }
    
    public NextState displayUi();
}
