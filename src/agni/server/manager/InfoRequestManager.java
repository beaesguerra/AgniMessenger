package agni.server.manager;


import agni.server.receiver.InfoListener;
import agni.server.sender.HeartbeatSender;
import agni.server.AgniServer;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.dataguard.I_InfoDataGuard;
import agni.server.dataguard.InfoDataGuard;
import agni.server.sender.InfoSender;

public class InfoRequestManager implements InfoListener{
    private InfoSender infoSender;
    private I_InfoDataGuard infoDataGuard;

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
                              InfoDataGuard infoDataGuard) {

        this.infoSender = infoSender;
        this.infoDataGuard = infoDataGuard;
    }

    @Override
    public void infoRequest(String ip, byte type) {
    	if (type == InfoRequestType.SERVER_IP.bytes()){
    		infoSender.sendInfo(ip, AgniServer.getServerIp());
    	}
    	else if (type == InfoRequestType.PORT.bytes()){
    		infoSender.sendInfo(ip, AgniServer.getServerPort());
    	}
    	else if (type == InfoRequestType.NAME.bytes()){
    		infoSender.sendInfo(ip, infoDataGuard.serverName());
    	}
    	else if (type == InfoRequestType.CURRENT_USERS_ONLINE.bytes()){
    		String usersOnline = ""; 
    		for( String user : infoDataGuard.usersOnline()) {
    			usersOnline.concat(user + "\n");
    		}
    		infoSender.sendInfo(ip, "Online users:\n" + usersOnline);
    	}
    	else if (type == InfoRequestType.CURRENT_CHATS.bytes()){
    		String chats = "";
    		for(String chat : infoDataGuard.availableChats()) {
    			chats.concat(chat + "\n");
    		}
    		infoSender.sendInfo(ip, "Available chats:\n" + chats);
    	}
        
    }

}
