package agni.server.communication;

import java.nio.channels.SocketChannel;
import java.util.Vector;

import agni.server.IPChannelPair;
import agni.server.receiver.ChatReceiver;
import agni.server.receiver.FileReceiver;
import agni.server.receiver.HeartbeatReceiver;
import agni.server.receiver.InfoRequestReceiver;
import agni.server.receiver.LoginReceiver;
import agni.server.receiver.UserReceiver;

public class MessageReceiver implements Runnable {
	


	public MessageReceiver(Vector<IPChannelPair<String, SocketChannel>> channels, LoginReceiver loginReceiver,
			UserReceiver userReceiver, ChatReceiver chatReceiver, FileReceiver fileReceiver,
			InfoRequestReceiver infoRequestReceiver, HeartbeatReceiver heartbeatReceiver) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
