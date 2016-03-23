package agni.server.dataguard;

import java.util.Date;

public class FileDataGuard {
//  private database;

    public FileDataGuard(String dbname, String username, String password) {

    }

    public Boolean isCached(String filepath) {
        return null;
    }

    public Date fileUploadDateTime(String filepath) {
        return null;
    }

    /*
     * File size in bytes
     */
    public long fileSize(String filepath) {
        return -1;
    }

    public String fileOwner(String filepath) {
        return null;
    }

    public void cacheFile(String filepath, String owner, long filesize) {

    }
}
