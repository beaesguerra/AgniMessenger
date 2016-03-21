package agni.server.dataguard;

public interface I_FileDataGuard {

    public boolean isCached(String user, String file);

    public void cacheFile(String user, String file);

    public String getFilePath(String user, String file);
}
