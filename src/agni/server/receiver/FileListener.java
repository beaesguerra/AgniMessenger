package agni.server.receiver;

public interface FileListener {

    public void fileRequest (String ip, byte EOF, String fileName, byte[] file);
}
