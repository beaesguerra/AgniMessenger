package agni.server.manager;

import agni.server.dataguard.ChatDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.ChatListener;
import agni.server.sender.ChatSender;
import agni.server.sender.InfoSender;

public class ChatManager implements ChatListener{

    private ChatDataGuard chatDataGuard;
    private InfoSender infoSender;
    private ChatSender chatSender;

    public ChatManager(UserDataGuard userDataGuard, 
                       ChatDataGuard chatDataGuard,
                       InfoSender infoSender,
                       ChatSender chatSender) {
        this.chatDataGuard = chatDataGuard;
        this.infoSender = infoSender;
        this.chatSender = chatSender;
    }

    @Override
    public void chatRequest(String ip, byte[] message) {
        // TODO Auto-generated method stub
        
    }
}
