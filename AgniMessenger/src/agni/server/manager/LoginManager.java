package agni.server.manager;

import agni.server.dataguard.UserDataGuard;
import agni.server.sender.InfoSender;

public class LoginManager {
	private InfoSender infoSender;
	private UserDataGuard userDataGuard;
	
	public LoginManager(InfoSender infoSender, 
						UserDataGuard userDataGuard) {
		this.infoSender = infoSender;
		this.userDataGuard = userDataGuard;
	}
	
}
