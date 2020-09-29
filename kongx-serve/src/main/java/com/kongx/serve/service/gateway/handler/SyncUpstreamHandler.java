package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.gateway.SyncConfig;
import com.kongx.serve.entity.gateway.SyncLog;
import com.kongx.serve.entity.gateway.Upstream;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("syncUpstreamHandler")
public class SyncUpstreamHandler extends AbstractSyncHandler {


    @Resource(name = "syncTargetsHandler")
    private ISyncHandler syncTargetsHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        this.syncUpstream(syncConfig, service, srcClient, destClient);
        return syncTargetsHandler;
    }

    private void syncUpstream(SyncConfig syncConfig, Service service, SystemProfile src, SystemProfile dest) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, service, service, src, dest);
        Upstream upstream = null;
        try {
            upstream = findUpstreamByHost(src, service.getHost());
            //1. 判断host是否存在upstream
            if (upstream == null) {
                Map map = new HashMap<>();
                map.put("host", service.getHost());
                syncLog.setContent(map);
                syncLog.setComment(String.format("[Upstream]：%s,源环境不存在Upstream，不需要同步", service.getHost()));
                return;
            }

            //2.host存在upstream
            syncLog.setContent(upstream);
            //3. 查询目标环境是否存在同名的upstream
            Upstream destUpstream = findUpstreamByHost(dest, upstream.getName());
            if (destUpstream == null) {
                destUpstream = upstream;
            }
            this.upstreamService.update(dest, destUpstream.getId(), upstream);
            syncLog.setComment(String.format("[Upstream]：%s,同步成功", service.getHost()));
        } catch (Exception e) {
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Upstream(63L)]：%s,同步失败,异常信息：%s", service.getHost(), e.getMessage()));
            throw new Exception(syncLog.getComment());
        } finally {
            this.syncLogService.add(syncLog);
        }
    }

    private Upstream findUpstreamByHost(SystemProfile src, String host) {
        Upstream upstream = null;
        try {
            upstream = this.upstreamService.findUpstream(src, host);
        } catch (Exception e) {
            log.debug("[upstream]:{},Msg:{}", host, e.getMessage());
        }
        return upstream;
    }
}
