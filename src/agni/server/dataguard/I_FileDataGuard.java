package agni.server.dataguard;

import java.util.Date;

public interface I_FileDataGuard {

    public Boolean isCached(String filepath);

    public Date fileUploadDateTime(String filepath);

    /*
     * File size in bytes
     */
    public long fileSize(String filepath);

    public String fileOwner(String filepath);

    public void cacheFile(String filepath, String owner, long filesize);

}
