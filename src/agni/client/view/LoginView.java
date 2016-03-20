package agni.client.view;

import agni.client.action.*;
import agni.server.sender.StatusSender.Status; 

public class LoginView implements AgniClientView {
    
    private LoginActionHandler loginActionHandler;
    private InfoRequestActionHandler infoRequestActionHandler;
    private HeartbeatActionHandler heartbeatActionHandler;
    
    public LoginView(LoginActionHandler loginActionHandler,
                     InfoRequestActionHandler infoRequestActionHandler,
                     HeartbeatActionHandler heartbeatActionHandler) {
        
        this.loginActionHandler = loginActionHandler;
        this.infoRequestActionHandler = infoRequestActionHandler;
        this.heartbeatActionHandler = heartbeatActionHandler;
    }

	@Override
	public void heartbeatReaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void infoReaction(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void statusReaction(String user, Status status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chatReaction(String sender, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fileReaction(String filename, String fromUser, int size) {
		// TODO Auto-generated method stub
		return false;
	}
}
