package com.es.phoneshop.model.dosProtector;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosServiceImpl implements DosService {

    private static DosService instance;

    private static final int THRESHOLD = 20;
    private Map<String, AtomicInteger> ipCallCount = new ConcurrentHashMap<>();
    private volatile Date lastResetDate = new Date();

    public static synchronized DosService getInstance() {
        if(instance == null) {
            instance = new DosServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        checkPossibleReset();

        AtomicInteger count = ipCallCount.get(ip);
        if (count == null) {
            count = new AtomicInteger(0);
            ipCallCount.put(ip, count);
        }
        int value = count.incrementAndGet();
        return value < THRESHOLD;
    }

    private void checkPossibleReset() {
        Date date = new Date();
        if ((date.getTime() - lastResetDate.getTime()) > 60 * 1000) {
            lastResetDate = date;
            ipCallCount.clear();
        }
    }
}
