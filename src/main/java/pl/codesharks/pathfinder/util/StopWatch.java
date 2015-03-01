package pl.codesharks.pathfinder.util;

public class StopWatch {

    private static final int NANO = 1000000000;
    private static final int MILLISECONDS = 1000000;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public long getElapsedTimeMilliseconds() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime) / MILLISECONDS;
        } else {
            elapsed = (stopTime - startTime) / MILLISECONDS;
        }
        return elapsed;
    }

    public long getElapsedTimeNano() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.nanoTime() - startTime) / NANO);
        } else {
            elapsed = ((stopTime - startTime) / NANO);
        }
        return elapsed;
    }
}


