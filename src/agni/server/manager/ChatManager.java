package agni.server.manager;

import agni.server.dataguard.GroupChatDataGuard;
import agni.server.sender.ChatSender;
import agni.server.sender.InfoSender;

public class ChatManager {

    private I_GroupChatDataGuard groupChatDataGuard;
    private InfoSender infoSender;
    private ChatSender chatSender;

    public ChatManager(GroupChatDataGuard groupChatDataGuard,
                       InfoSender infoSender,
                       ChatSender chatSender) {
        this.groupChatDataGuard = groupChatDataGuard;
        this.infoSender = infoSender;
        this.chatSender = chatSender;
    }
}
