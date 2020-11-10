package com.kongx.serve.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class OperationLog {
    private int id;

    private String userId;

    private String profile;

    private String creator;

    private String operation;

    private String target;

    private Date create_at = new Date();

    private Object content;

    private String remark;


    public static enum OperationType {
        OPERATION_LOGIN("login", "登录"),
        OPERATION_LOGOUT("logout", "登出"),
        OPERATION_SYNC("sync", "同步"),
        OPERATION_ADD("add", "新增"),
        OPERATION_UPDATE("update", "修改"),
        OPERATION_DELETE("delete", "删除"),
        OPERATION_SUBSCRIBE("subscribe", "订阅"),
        JOB_STARTUP("job_startup", "启动"),
        JOB_PAUSE("job_pause", "暂停"),
        JOB_DELETE("job_delete", "删除"),
        DEMOTE_CONFIG("demote_config", "执行降级"),
        ROLLBACK_CONFIG("rollback_config", "降级回滚"),
        ARTHAS_ATTACH("arthas_attach", "Arthas调试"),
        OPERATION_DEFAULT("none", "");
        private String type;
        private String remark;

        private OperationType(String type, String remark) {
            this.type = type;
            this.remark = remark;
        }

        public static OperationType to(String type) {
            for (OperationType value : OperationType.values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
            return OPERATION_DEFAULT;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static enum OperationTarget {
        GLOBAL_PLUGIN("global_plugin", "插件"),
        ROUTE_PLUGIN("route_plugin", "路由插件"),
        SERVICE_PLUGIN("service_plugin", "服务插件"),
        ROUTE("route", "路由"),
        SERVICE("service", "服务"),
        UPSTREAM("upstream", "上游服务"),
        SNI("sni", "sni"),
        CONSUMERS("consumers", "消费者"),
        CaCertificate("ca_certificate", "CA认证"),
        Certificate("certificate", "认证"),
        TARGETS("targets", "上游代理"),
        SYSTEM("system", "系统"),
        SYSTEM_ROLE("system_role", "系统角色"),
        USER_GROUP("user_group", "用户组"),
        SYSTEM_FUNCTION("system_function", "系统功能菜单"),
        SYNC_SERVICE("sync_service", "网关服务"),
        USER_INFO("user_info", "用户信息"),
        TEMPLATE_TYPE("template_type", "模板类型"),
        SERVER_CONFIG("server_config", "系统参数"),
        SYSTEM_PROFILE("system_profile", "系统环境"),
        DEMOTE_APP_PARAMS("demote_app_params", "降级参数"),
        SERVICE_PROGRESS("service_progress", "服务进程"),
        MONITOR_JOB("monitor_job", "作业"),
        MONITOR_JOB_TASK("monitor_job_task", "作业任务"),
        DING_TALK_ROBOT("ding_talk_robot", "钉钉机器人"),
        ALERT_RULE("alert_rule", "告警规则"),
        ALERT_RECORD("alert_record", "告警记录"),
        ALERT_TEMPLATE("alert_template", "告警模板"),
        PROJECT("project", "项目管理"),
        SYNC_APP_PARAMS("sync_app_params", "热配参数"),
        LOGGER_LEVEL("logger_level", "日志级别");
        private String type;
        private String target;

        private OperationTarget(String type, String target) {
            this.type = type;
            this.target = target;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
