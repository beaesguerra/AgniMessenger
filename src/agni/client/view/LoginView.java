package agni.client.view;

import agni.client.action.*; 

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
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
