package agni.client.view;

import agni.client.receiver.ReceiverListener;

public interface AgniClientView extends ReceiverListener {
    public enum NextState {
        LoginView, IdleView, ChatView;
    }
    public NextState displayUi();
}
