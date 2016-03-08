package agni.server.manager;

import agni.server.dataguard.ChatDataGuard;
import agni.server.sender.ChatSender;
import agni.server.sender.InfoSender;

public class ChatManager {

    private ChatDataGuard chatDataGuard;
    private InfoSender infoSender;
    private ChatSender chatSender;

    public ChatManager(ChatDataGuard chatDataGuard,
                        InfoSender infoSender,
                        ChatSender chatSender) {
        this.chatDataGuard = chatDataGuard;
        this.infoSender = infoSender;
        this.chatSender = chatSender;
    }



}
