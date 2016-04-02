package agni.server.manager;

import agni.server.receiver.HeartbeatListener;
import agni.server.sender.HeartbeatSender;

public class HeartbeatManager implements HeartbeatListener{
	private HeartbeatSender heartbeatSender;
	
	public HeartbeatManager(HeartbeatSender heartbeatSender) {
		this.heartbeatSender = heartbeatSender;
	}

	@Override
	public void receivedHeartBeat(String ip, byte status) {
		// TODO Auto-generated method stub
		
	}
}
