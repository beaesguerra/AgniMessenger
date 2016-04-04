public class CliHeartbeatSender implements Runnable {
    private CliMessageSender messageSender;
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

    public CliHeartbeatSender(CliMessageSender messageSender, int period) {
        this.messageSender = messageSender;
        this.period = period;
    }

    public void run() {
        while (true) {
            messageSender.sendMessage(hexStringToByteArray("000000060101"));
            System.out.println("SENDING HEARTBEAT");
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                  + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
