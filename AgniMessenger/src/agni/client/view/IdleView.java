package agni.client.view;

import agni.client.action.*;

public class IdleView implements AgniClientView {

	private InfoRequestActionHandler infoRequestActionHandler;
	private UserActionHandler userActionHandler;
	private HeartbeatActionHandler heartbeatActionHandler;
	
	public IdleView(InfoRequestActionHandler infoRequestActionHandler, 
					UserActionHandler userActionHandler,
					HeartbeatActionHandler heartbeatActionHandler) {
		
		this.infoRequestActionHandler = infoRequestActionHandler;
		this.userActionHandler = userActionHandler;
		this.heartbeatActionHandler = heartbeatActionHandler;
	}
}
