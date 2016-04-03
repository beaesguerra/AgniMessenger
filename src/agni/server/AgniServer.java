package agni.server;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Vector;

import agni.client.action.InfoRequestActionHandler;
import agni.server.communication.MessageReceiver;
import agni.server.communication.MessageSender;
import agni.server.dataguard.GroupChatDataGuard;
import agni.server.dataguard.I_FileDataGuard;
import agni.server.dataguard.I_GroupChatDataGuard;
import agni.server.dataguard.FileDataGuard;
import agni.server.dataguard.InfoDataGuard;
import agni.server.dataguard.UserDataGuard;
import agni.server.manager.ChatManager;
import agni.server.manager.FileManager;
import agni.server.manager.HeartbeatManager;
import agni.server.manager.InfoRequestManager;
import agni.server.manager.LoginManager;
import agni.server.manager.StatusManager;
import agni.server.manager.UserManager;
import agni.server.receiver.ChatReceiver;
import agni.server.receiver.FileReceiver;
import agni.server.receiver.HeartbeatReceiver;
import agni.server.receiver.InfoRequestReceiver;
import agni.server.receiver.LoginReceiver;
import agni.server.receiver.UserReceiver;
import agni.server.sender.ChatSender;
import agni.server.sender.FileSender;
import agni.server.sender.HeartbeatSender;
import agni.server.sender.InfoSender;
import agni.server.sender.StatusSender;
import agni.server.communication.ChannelList;
import agni.server.communication.IpChannelPair;

public class AgniServer {

    public static void main(String[] args) {

        ChannelList channels = new ChannelList();
        MessageSender messageSender = new MessageSender(channels);

        InfoSender infoSender = new InfoSender(messageSender);
        ChatSender chatSender = new ChatSender(messageSender);
        FileSender fileSender = new FileSender(messageSender);
        StatusSender statusSender = new StatusSender(messageSender);
        HeartbeatSender heartbeatSender = new HeartbeatSender(messageSender);

        // TODO 
        UserDataGuard userDataGuard = new UserDataGuard(null, null, null);
        GroupChatDataGuard chatDataGuard = new GroupChatDataGuard(null, null, null);
        FileDataGuard fileDataGuard = new FileDataGuard(null, null, null);
        InfoDataGuard infoDataGuard = new InfoDataGuard(null, null, null);

        LoginReceiver loginReceiver = new LoginReceiver();
        UserReceiver userReceiver = new UserReceiver();
        ChatReceiver chatReceiver = new ChatReceiver();
        FileReceiver fileReceiver = new FileReceiver();
        InfoRequestReceiver infoRequestReceiver = new InfoRequestReceiver();
        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver();

        MessageReceiver messageReceiver = new MessageReceiver(channels,
                                                              loginReceiver,
                                                              userReceiver,
                                                              chatReceiver,
                                                              fileReceiver,
                                                              infoRequestReceiver,
                                                              heartbeatReceiver);

        LoginManager loginManager = new LoginManager(infoSender, userDataGuard);
        UserManager userManager = new UserManager(infoSender, userDataGuard, chatDataGuard);
        ChatManager chatManager = new ChatManager(userDataGuard, chatDataGuard, infoSender, chatSender);
        FileManager fileManager = new FileManager(infoSender, fileSender, fileDataGuard, userDataGuard);
        InfoRequestManager infoRequestManager = new InfoRequestManager(infoSender, heartbeatSender, fileDataGuard);
        //StatusManager statusManager = new StatusManager(statusSender, userDataGuard);
        HeartbeatManager heartbeatManager = new HeartbeatManager(heartbeatSender, userDataGuard); 
        
        loginReceiver.register(loginManager);
        userReceiver.register(userManager);
        chatReceiver.register(chatManager);
        fileReceiver.register(fileManager);
        infoRequestReceiver.register(infoRequestManager);
        heartbeatReceiver.register(heartbeatManager);
    }
}
