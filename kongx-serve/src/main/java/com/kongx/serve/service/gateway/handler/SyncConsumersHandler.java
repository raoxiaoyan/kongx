package com.kongx.serve.service.gateway.handler;

import com.kongx.serve.entity.gateway.*;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ISyncHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("syncConsumersHandler")
public class SyncConsumersHandler extends SyncPluginsHandler {


    @Override
    public ISyncHandler handler(SystemProfile srcClient, SystemProfile destClient, Service service, SyncConfig syncConfig, Object... values) throws Exception {
        KongEntity<Consumer> consumerKongEntity = this.consumerService.findAll(srcClient);
        consumerKongEntity.getData().forEach((consumer -> {
            try {
                this.syncEntity(syncConfig, consumer, srcClient, destClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        return null;
    }

    private void syncEntity(SyncConfig syncConfig, Consumer consumer, SystemProfile srcClient, SystemProfile destClient) throws Exception {
        KongEntity<PluginVO> plugins = pluginService.findAllByConsumer(srcClient, consumer.getId());
        this.consumerService.update(destClient, consumer.getId(), consumer);
        for (PluginVO plugin : plugins.getData()) {
            EntityId entityId = new EntityId();
            entityId.setId(consumer.getId());
            plugin.setService(entityId);
            if (consumer != null) {
                plugin.setScope("global");
                Map map = new HashMap();
                map.put("name", consumer.getId());
                plugin.setApplyObject(map);
            }
            this.syncPlugin(syncConfig, plugin, null, srcClient, destClient);
        }
    }
}
