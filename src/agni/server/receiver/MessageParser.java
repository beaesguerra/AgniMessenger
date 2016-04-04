package agni.server.receiver;

import java.sql.SQLException;

public interface MessageParser {    
    public void receiveMessage(String ip, byte[] message) throws SQLException;
}
