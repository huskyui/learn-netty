package org.example.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author huskyui
 */
public final class IdUtil {
    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() {
        // no instance
    }

    public static long nextId(){
        return IDX.incrementAndGet();
    }
}
