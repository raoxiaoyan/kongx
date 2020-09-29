package com.kongx.serve.service.system;

import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void log(SystemProfile systemProfile, OperationLog log) {
        this.executorService.execute(() -> logMapper.add(log));
    }

    public List<OperationLog> findAllByDays(int days, String keyword) {
        return this.logMapper.findAllByDays(days, keyword);
    }

    public List<OperationLog> findAllByBeforeDays(int days) {
        return this.logMapper.findAllByBeforeDays(days);
    }

    public Map getDateStr(int days, String type) {
        return this.logMapper.getDateStr(days, type);
    }
}
