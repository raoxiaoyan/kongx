package com.kongx.serve.service;

import com.kongx.serve.entity.gateway.SyncLog;
import com.kongx.serve.mapper.SyncLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncLogService {
    @Autowired
    private SyncLogMapper syncLogMapper;

    public int add(SyncLog syncLog) {
        return this.syncLogMapper.add(syncLog);
    }

    public List<SyncLog> findAllBySyncNo(String syncNo) {
        return this.syncLogMapper.findBySyncNo(syncNo);
    }

    public List<SyncLog> findBySyncNoAndService(String syncNo, String service, String destClient) {
        return this.syncLogMapper.findBySyncNoAndService(syncNo, service, destClient);
    }
}
