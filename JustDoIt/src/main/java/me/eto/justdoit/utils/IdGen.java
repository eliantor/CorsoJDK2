package me.eto.justdoit.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eto on 10/19/13.
 */
public class IdGen {

    private final static AtomicInteger COUNTER = new AtomicInteger();

    public static int get(){
        return COUNTER.incrementAndGet();
    }
}
