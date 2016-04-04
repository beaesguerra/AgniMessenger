package agni.server.receiver;

import java.sql.SQLException;

public interface UserListener {

    /*
    * Receives user requests from the UserReceiver
    * ip will have been converted from inet to String
    */
    public void userRequest (String ip, byte type, String message) throws SQLException;

}
