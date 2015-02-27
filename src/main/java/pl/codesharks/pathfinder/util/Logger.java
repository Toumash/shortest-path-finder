package pl.codesharks.pathfinder.util;

/**
 * Simple console logger
 */
public class Logger {
    public void addLine(String s) {
        System.out.println(s);
    }

    public void addLine() {
        System.out.println();
    }

    public void add(String s) {
        System.out.print(s);
    }
}
