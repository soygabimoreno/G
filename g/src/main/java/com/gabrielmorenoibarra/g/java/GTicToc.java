package com.gabrielmorenoibarra.g.java;

/**
 * Utility to measure times between lines of code during execution.
 * Created by Gabriel Moreno on 2016-11-28.
 */
public class GTicToc {

    private long tic;
    private String tag = "";

    public GTicToc(long tic) {
        this.tic = tic;
    }

    public GTicToc(long tic, String tag) {
        this.tic = tic;
        this.tag = tag;
    }

    /**
     * Show how many milliseconds elapse between 'tic' and 'toc'.
     * There is to set 'tic' with <code>System.currentTimeMillis()</code> on constructor.
     */
    public void toc() {
        if (tag != null) tag += ": ";
        System.out.println("TIME: " + tag + (System.currentTimeMillis() - tic) + " ms");
    }

    /**
     * Show how many nanoseconds elapse between 'tic' and 'toc'.
     * There is to set 'tic' with <code>System.nanoTime()</code> on constructor.
     */
    public void tocNano() {
        if (tag != null) tag += ": ";
        System.out.println("TIME: " + tag + (System.nanoTime() - tic) + " ns");
    }

    /**
     * Show how many microseconds elapse between 'tic' and 'toc'.
     * There is to set 'tic' with <code>System.nanoTime()</code> on constructor.
     */
    public void tocMicro() {
        if (tag != null) tag += ": ";
        System.out.println("TIME: " + tag + (System.nanoTime() - tic) / 1000 + " us");
    }
}