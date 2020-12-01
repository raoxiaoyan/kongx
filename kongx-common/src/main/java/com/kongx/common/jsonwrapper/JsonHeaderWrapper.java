package com.kongx.common.jsonwrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

/**
 * 所有数据传输对象的父类 Created by liuyang on 2015/3/10.
 */
public class JsonHeaderWrapper<T extends Object> {


    /**
     * 当前数据结构的版本号, 版本升级意味着json数据结构变化
     */
    private String version = "1.0";
    /**
     * 客户标识. 要求接口传入, 原值返回
     */
    private String clientFrom;

    /**
     * 服务器返回json时的时间戳
     */
    private String timestamp;
    /**
     * 可选, http get请求的原始URL, post请求可不填写, 用于跟踪调试
     */
    private String url;
    /**
     * 0:全部成功, 202:部分成功, 405:接口超时返回,406客户流量超量, 500:全部失败, 505:传入参数错误(如:不存在的参数名称)
     */
    private int status;
    /**
     * 可选, 错误信息, 当status不为0的时候必填
     */
    private String errmsg;
    /**
     * 可选, 接口接到请求到返回数据花销的毫秒数, 建议填写
     */
    private long elapsed;

    /**
     * 必填, 用于表示本次会话的唯一ID
     */
    private String trackId;
    /**
     * 可选, 安全限制 [内网(1)，内网认证(2)，公网(3)，公网认证(4)，IP限制(5)], 建议填写, 该值可能会在反向代理等节点做安全处理
     */
    @JsonIgnore
    private int securType;
    /**
     * 业务数据
     */
    private T data;

    /*
     */
    public JsonHeaderWrapper() {
        clientFrom = "";
        url = "";
        status = StatusEnum.Success.getCode();
        errmsg = "";
        elapsed = 0;
        trackId = UUID.randomUUID().toString();
        securType = SecurTypeEnum.UnknownOther.getCode();
        data = null;
    }


    public String getVersion() {
        return version;
    }

    public String getClientFrom() {
        return clientFrom;
    }

    public void setClientFrom(String clientFrom) {
        this.clientFrom = clientFrom;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public long getElapsed() {
        return elapsed;
    }


    /*
     * public void setAcc_point(String acc_point) { this.acc_point = acc_point;
     * }
     */
    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getSecurType() {
        return securType;
    }

    public void setSecurType(int securType) {
        this.securType = securType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public enum SecurTypeEnum {
        /**
         * 安全限制 [内网(1)，内网认证(2)，公网(3)，公网认证(4)，IP限制(5)], 建议填写, 该值可能会在反向代理等节点做安全处理
         */

        InnerAcc(1, "内网"), InnerAuthAcc(2, "内网认证"), PublicAcc(3, "公网"), PublicAuthAcc(4, "公网认证"), IPAuthAcc(5, "IP限制"), UnknownOther(599, "未知类型");

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCodee(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        SecurTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    /**
     * 状态枚举类
     */
    public enum StatusEnum {

        Success(0, "全部成功"), //
        Success4M(200, "全部成功"),
        //无线端统一使用200作为成功代码返回
        NOT_FOUND(404, "not found"),
        Failed(500, "全部失败"), //
        ParamError(505, "传入参数错误"), //
        UnknownOther(599, "未知错误,系统错误");

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int value) {
            this.code = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        StatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


}
