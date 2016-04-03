package agni.client;

public class HeartbeatMonitor implements Runnable {

    private long TIMEOUT_PERIOD;

    private long lastHeartbeatTime;
    private boolean timedOut;

    public HeartbeatMonitor(long timeoutPeriod) {
        lastHeartbeatTime = System.nanoTime();
        timedOut = false;
        TIMEOUT_PERIOD = timeoutPeriod;
    }

    public void receivedHeartbeat() {
        lastHeartbeatTime = System.nanoTime();
    }

    public final boolean isTimedOut() {
        return timedOut;
    }

    @Override
    public void run() {
        while (true) {
            if (System.nanoTime() - lastHeartbeatTime > TIMEOUT_PERIOD) {
                timedOut = true;
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
