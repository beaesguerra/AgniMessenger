package agni.server.dataguard;

public interface I_GroupChatDataGuard {

    public void history(String chat);

    public void users(String chat);

    public void lastMessage(String chat);
}
