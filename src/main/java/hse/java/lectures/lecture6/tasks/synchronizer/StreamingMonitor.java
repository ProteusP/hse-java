package hse.java.lectures.lecture6.tasks.synchronizer;

public class StreamingMonitor {
    // impl your sync here
    private int totalWritersCount;
    private int writersEnded;
    private int expectedWriterId;
    private int ticksPerWriter;
    private int[] currWritersTicks;

    public StreamingMonitor(int writersCount, int ticksPerWriter){
        this.totalWritersCount = writersCount;
        this.currWritersTicks = new int[writersCount];
        this.ticksPerWriter = ticksPerWriter;
        this.expectedWriterId = 0;
        this.writersEnded = 0;
    }

    public synchronized void startTick(int writerId) throws InterruptedException {
        if (writerId != expectedWriterId){
            wait();
        }
    }

    public synchronized void endTick(int writerId){
        currWritersTicks[writerId]++;

        if (currWritersTicks[writerId] == ticksPerWriter){
            writersEnded++;
        }

        notifyAll();
    }

    public synchronized void waitAllWriters() throws InterruptedException {
        while (writersEnded < totalWritersCount){
            wait();
        }
    }
}
