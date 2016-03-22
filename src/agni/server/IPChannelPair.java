package agni.server;

public class IPChannelPair<I,C> {
        private final I ipAddress;
        private final C channel; 
        public IPChannelPair(I ip, C channel) {
                this.ipAddress = ip;
                this.channel = channel; 
        }
}
