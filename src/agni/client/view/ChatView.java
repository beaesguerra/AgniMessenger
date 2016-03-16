package agni.client.view;

import agni.client.action.*;

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
    
    
}
