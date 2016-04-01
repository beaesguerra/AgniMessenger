package agni.server.manager;

import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.ChatListener;
import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.sender.ChatSender;
import agni.server.sender.InfoSender;

public class ChatManager implements ChatListener{

    private I_GroupChatDataGuard groupChatDataGuard;
    private InfoSender infoSender;
    private ChatSender chatSender;

    public ChatManager(UserDataGuard userDataGuard, 
    				I_GroupChatDataGuard groupChatDataGuard,
                       InfoSender infoSender,
                       ChatSender chatSender) {
        this.groupChatDataGuard = groupChatDataGuard;
        this.infoSender = infoSender;
        this.chatSender = chatSender;
    }

	@Override
	public void chatRequest(String ip, String message) {
		// TODO Auto-generated method stub
		
	}
}
