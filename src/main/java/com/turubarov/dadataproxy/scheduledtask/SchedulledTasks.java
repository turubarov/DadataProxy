package com.turubarov.dadataproxy.scheduledtask;

import com.turubarov.dadataproxy.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class SchedulledTasks {

    private final long HOURS_BETWEEN_DB_CLEAR = 3600;
    private final int TIME_THRESHOLD_FOR_DELETE = 1;
    private final int COUNT_USE_THRESHOLD_FOR_DELETE = 3;

    @Autowired
    private RequestRepository requestRepository;

    @Scheduled(fixedDelay = HOURS_BETWEEN_DB_CLEAR * 3600 * 1000)
    public void deleteOldRequests() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -TIME_THRESHOLD_FOR_DELETE);
        Date timeThreshold = cal.getTime();
        requestRepository.deleteOldRequests(timeThreshold,
                COUNT_USE_THRESHOLD_FOR_DELETE);
    }
}
