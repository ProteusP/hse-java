package hse.java.lectures.lecture6.tasks.synchronizer;

public class StreamingMonitor {
    // impl your sync here
    private int totalWritersCount;
    private int writersEnded;
    private int expectedWriterIndex;
    private int ticksPerWriter;
    private int[] currWritersTicks;

    public StreamingMonitor(int writersCount, int ticksPerWriter){
        this.totalWritersCount = writersCount;
        this.currWritersTicks = new int[writersCount];
        this.ticksPerWriter = ticksPerWriter;
        this.expectedWriterIndex = 0;
        this.writersEnded = 0;
    }

    public synchronized void startTick(int writerId) throws InterruptedException {
        var writerIndex = writerId - 1;

        if (currWritersTicks[writerIndex] == ticksPerWriter){
            return;
        }

        while (writerIndex != expectedWriterIndex){
            wait();
        }
    }

    public synchronized void endTick(int writerId){
        var writeIndex = writerId-1;

        currWritersTicks[writeIndex]++;

        if (currWritersTicks[writeIndex] == ticksPerWriter){
            writersEnded++;
        }
        expectedWriterIndex = (expectedWriterIndex + 1) % totalWritersCount;
        notifyAll();
    }

    public synchronized void waitAllWriters() throws InterruptedException {
        while (writersEnded < totalWritersCount){
            wait();
        }
    }

    public boolean writerEnded(int writerId){
        var writerIndex = writerId - 1;

        return (currWritersTicks[writerIndex] == ticksPerWriter);
    }
}
