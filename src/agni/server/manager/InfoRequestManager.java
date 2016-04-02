package agni.server.manager;


import agni.server.receiver.InfoListener;
import agni.server.sender.HeartbeatSender;
import agni.server.AgniServer;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.sender.InfoSender;

public class InfoRequestManager implements InfoListener{
    private InfoSender infoSender;
    private I_FileDataGuard fileDataGuard;

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
                              I_FileDataGuard fileDataGuard) {

        this.infoSender = infoSender;
        this.fileDataGuard = fileDataGuard;
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
    		
    	}
    	else if (type == InfoRequestType.CURRENT_USERS_ONLINE.bytes()){
    		
    	}
    	else if (type == InfoRequestType.CURRENT_CHATS.bytes()){
    		
    	}
        
    }

}
