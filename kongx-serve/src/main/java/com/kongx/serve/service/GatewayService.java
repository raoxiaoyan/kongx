package com.kongx.serve.service;

import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.feign.KongFeignService;

import java.net.URISyntaxException;

public abstract class GatewayService<T> extends AbstractCacheService<KongEntity<T>> {

    protected KongFeignService<T> kongFeignService;

    protected String CACHE_KEY;

    protected String ENTITY_URI = "";
    protected String ENTITY_URI_ID = "";

    /**
     * 查询所有upstream
     *
     * @return
     */
    public KongEntity<T> findAll(SystemProfile systemProfile) {
        return this.get(systemProfile, CACHE_KEY).getData();
    }

    /**
     * 新增upstream
     *
     * @param entity
     * @return
     * @throws URISyntaxException
     */
    public T add(SystemProfile systemProfile, T entity) throws Exception {
        T results = this.kongFeignService.add(uri(systemProfile, ENTITY_URI), entity);
        refresh(systemProfile, CACHE_KEY);
        return results;
    }

    /**
     * 更新sni
     *
     * @param id
     * @param entity
     * @return
     * @throws URISyntaxException
     */
    public T update(SystemProfile systemProfile, String id, T entity) throws Exception {
        T results = this.kongFeignService.update(uri(systemProfile, String.format(ENTITY_URI_ID, id)), entity);
        refresh(systemProfile, CACHE_KEY);
        return results;
    }

    /**
     * 删除sni
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public KongEntity<T> remove(SystemProfile systemProfile, String id) throws Exception {
        this.kongFeignService.remove(uri(systemProfile, String.format(ENTITY_URI_ID, id)));
        return refresh(systemProfile, CACHE_KEY).getData();
    }

    /**
     * 查询单个sni的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    public T findEntity(SystemProfile systemProfile, String id) throws URISyntaxException {
        return this.kongFeignService.findById(uri(systemProfile, String.format(ENTITY_URI_ID, id)));
    }


}
