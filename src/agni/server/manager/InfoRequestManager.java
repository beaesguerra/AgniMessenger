package agni.server.manager;


import agni.server.receiver.InfoListener;
import agni.server.sender.HeartbeatSender;

import java.sql.SQLException;

import agni.server.AgniServer;
import agni.server.dataguard.GroupChatDataGuard;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.dataguard.I_UserDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.sender.InfoSender;

public class InfoRequestManager implements InfoListener{
    private InfoSender infoSender;
    private I_UserDataGuard userDataGuard; 
    private I_GroupChatDataGuard groupChatDataGuard; 

    public enum InfoRequestType {
        SERVER_IP((byte) 0x00), 
        PORT((byte) 0x01), 
        NAME((byte) 0x02), 
        CURRENT_USERS_ONLINE((byte) 0x03), 
        CURRENT_CHATS((byte) 0x04);

        private final byte bytes;

        private InfoRequestType(byte bytes) {
            this.bytes = bytes;
        }

        public final byte bytes() {
            return bytes;
        }
    }
    
    public InfoRequestManager(InfoSender infoSender,
                              HeartbeatSender heartbeatSender, 
                              UserDataGuard userDataGuard,
                              GroupChatDataGuard groupChatDataGuard) {

        this.infoSender = infoSender;
        this.userDataGuard = userDataGuard;
        this.groupChatDataGuard = groupChatDataGuard; 
    }

    @Override
    public void infoRequest(String ip, byte type) throws SQLException {
    	if (type == InfoRequestType.SERVER_IP.bytes()){
    		infoSender.sendInfo(ip, AgniServer.getServerIp());
    	}
    	else if (type == InfoRequestType.PORT.bytes()){
    		infoSender.sendInfo(ip, AgniServer.getServerPort());
    	}
    	else if (type == InfoRequestType.NAME.bytes()){
    		infoSender.sendInfo(ip, AgniServer.getServerName());
    	}
    	else if (type == InfoRequestType.CURRENT_USERS_ONLINE.bytes()){
    		String usersOnline = ""; 
    		for( String user : userDataGuard.usersOnline()) {
    			usersOnline.concat(user + "\n");
    		}
    		infoSender.sendInfo(ip, "Online users:\n" + usersOnline);
    	}
    	else if (type == InfoRequestType.CURRENT_CHATS.bytes()){
    		String chats = "";
    		for(String chat : groupChatDataGuard.allGroupChats()) {
    			chats.concat(chat + "\n");
    		}
    		infoSender.sendInfo(ip, "Available chats:\n" + chats);
    	}
        
    }

}
