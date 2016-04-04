package agni.server.receiver;

import java.sql.SQLException;

public interface ChatListener {

    public void chatRequest (String ip, String message) throws SQLException;
}
