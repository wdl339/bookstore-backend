package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.service.TimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session")
public class TimerServiceImpl implements TimerService {
    private long startTime = 0;

    public void startTimer() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
    }

    public String stopTimer() {
        if (startTime == 0) {
            return "Timer not started";
        } else {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            startTime = 0;
            long hour = duration / 3600000;
            long minute = (duration % 3600000) / 60000;
            long second = (duration % 60000) / 1000;
            long millisecond = duration % 1000;
            return "Session duration: " + hour + "h " + minute + "m " + second + "s " + millisecond + "ms";
        }
    }
}
