package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("syncTargetsHandler")
public class SyncTargetsHandler extends AbstractSyncHandler {
    @Resource(name = "syncServiceHandler")
    private ISyncHandler syncServiceHandler;

    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        this.syncTargets(syncConfig, service, srcClient, destClient);
        return syncServiceHandler;
    }

    private void syncTargets(SyncConfig syncConfig, Service service, SystemProfile src, SystemProfile dest) throws Exception {
//        SyncLog syncLog = this.syncLog(syncConfig, service, service, src, dest);
//        Upstream upstream = null;
//        try {
//            upstream = findUpstreamByHost(src, service.getHost());
//            if (upstream == null) {
//                syncLog.setComment(String.format("[Targets]：%s,无upstrems,Targets无需同步", service.getHost()));
//                return;
//            }
//            Upstream destUpstream = findUpstreamByHost(dest, upstream.getName());
//            //查询源环境的targets列表
//            KongEntity<Target> targetKongEntity = this.targetService.findAll(src, upstream.getId());
//            //查询目标环境的targets列表
//            KongEntity<Target> destTargetKongEntity = this.targetService.findAll(dest, destUpstream.getId());
//            for (Target target : targetKongEntity.getData()) {
//                boolean exists = false;
//                for (Target desTarget : destTargetKongEntity.getData()) {
//                    if (desTarget.getId().equals(target.getId())) {
//                        if (!target.getTarget().equals(desTarget.getTarget()) || target.getWeight() != desTarget.getWeight()) {
//                            this.targetService.remove(dest, upstream.getId(), desTarget.getId());
//                        } else {
//                            exists = true;
//                        }
//                        break;
//                    }
//                }
//                if (!exists) {
//                    EntityId entityId = new EntityId();
//                    entityId.setId(destUpstream.getId());
//                    target.setUpstream(entityId);
//                    this.syncTarget(syncConfig, target, service, src, dest);
//                }
//            }
//            syncLog.setComment(String.format("[Targets]：%s,同步代理列表成功", service.getHost()));
//        } catch (Exception e) {
//            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
//            syncLog.setComment(String.format("[Targets(58L)]：%s,同步代理列表失败,异常信息：%s,class:%s", service.getHost(), e.getMessage(), getClass().getName()));
//            throw new Exception(syncLog.getComment());
//        } finally {
//            this.syncLogService.add(syncLog);
//        }
    }

    private void syncTarget(SyncConfig syncConfig, Target target, Service service, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        SyncLog syncLog = this.syncLog(syncConfig, target, service, srcClient, destClient);
        try {
            target.setId(null);
            String host = target.getTarget();
            if (host.startsWith(srcClient.getAb() + "-")) {
                target.setTarget(host.replaceAll(srcClient.getAb() + "-", destClient.getAb() + "-"));
            }
            this.targetService.add(destClient, target.getUpstream().getId(), target);
            syncLog.setComment(String.format("[Target]：%s,同步成功", target.getTarget()));
        } catch (Exception e) {
            e.printStackTrace();
            syncLog.setStatus(SyncLog.LOG_STATUS_FAILURE);
            syncLog.setComment(String.format("[Target(72L)]：%s,同步失败,异常信息：%s,class:%s", target.getTarget(), e.getMessage(), getClass().getName()));
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
