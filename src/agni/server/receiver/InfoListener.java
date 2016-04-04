package agni.server.receiver;

import java.sql.SQLException;

public interface InfoListener {

    public void infoRequest (String ip, byte type) throws SQLException;
}
