package agni.server.manager;

import agni.server.dataguard.UserDataGuard;
import agni.server.receiver.ChatListener;

import java.sql.SQLException;

import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.dataguard.I_UserDataGuard;
import agni.server.sender.ChatSender;
import agni.server.sender.InfoSender;

public class ChatManager implements ChatListener{

    private I_GroupChatDataGuard groupChatDataGuard;
    private I_UserDataGuard userDataGuard; 
    private InfoSender infoSender;
    private ChatSender chatSender;

    public ChatManager(UserDataGuard userDataGuard, 
                    I_GroupChatDataGuard groupChatDataGuard,
                       InfoSender infoSender,
                       ChatSender chatSender) {
        this.groupChatDataGuard = groupChatDataGuard;
        this.userDataGuard = userDataGuard; 
        this.infoSender = infoSender;
        this.chatSender = chatSender;
    }

    @Override
    public void chatRequest(String ip, String message) throws SQLException {
        message = message.replace("\"", "^");
        String sender = userDataGuard.getUsername(ip);
        String groupChatName = groupChatDataGuard.userCurrentChat(sender);
        String [] membersOfChat = groupChatDataGuard.users(groupChatName);
        System.out.println("MEMBERS ARE:");
        for(int i = 0; i < membersOfChat.length; i++){
            System.out.println("    " + membersOfChat[i]);
        }
        groupChatDataGuard.addMessage(message, sender, groupChatName);
        for (String member : membersOfChat) {
            if (userDataGuard.isOnline(member)) {   // would users have to be online to receive a message? 
                System.out.println("SENDING CHAT TO " + member);
                chatSender.sendChat(userDataGuard.getIp(member), sender, message);
            }
        }        
    }
}
