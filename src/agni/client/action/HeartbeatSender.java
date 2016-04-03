package agni.client.action;

import agni.client.communication.MessageSender;

public class HeartbeatSender implements Runnable {
    private MessageSender messageSender;
    private int period;

    public enum Status {
        OFFLINE((byte)0x00),
        ONLINE((byte)0x01);

        private final byte bytes;
        private Status(byte bytes) {
            this.bytes = bytes;
        }

        public byte bytes() {
            return bytes;
        }
    }

    public HeartbeatSender(MessageSender messageSender, int period) {
        this.messageSender = messageSender;
        this.period = period;
    }

    public void run() {
        while (true) {
            // send a heart beat
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
