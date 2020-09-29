package com.kongx.serve.controller.system;

import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.system.LogParams;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController("/OperationLogController")
@RequestMapping("/operating/logs/")
public class LogController extends BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public JsonHeaderWrapper findAllLogsByDay(LogParams logParams) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {

            jsonHeaderWrapper.setData(query(logParams));
        } catch (Exception e) {
            e.printStackTrace();
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    private Map query(LogParams logParams) {
        Map<String, Object> values = new HashMap<>();
        QueryType queryType = QueryType.to(logParams.getLabel());
        int days = logParams.getValue();
        String fmt = "day";
        switch (queryType) {
            case ALL:
                break;
            case RECENT7:
                logParams.setBegin(0);
                logParams.setEnd(7);
                break;
            case PRE_WEEK:
                if (logParams.getValue() == 0) {
                    Calendar cal = Calendar.getInstance();
                    int _days = cal.get(Calendar.DAY_OF_WEEK);
                    logParams.setValue(_days);
                    logParams.setBegin(_days);
                    logParams.setEnd(_days + 6);
                }
                days = logParams.getValue();
                break;
            case THIS_WEEK:
                fmt = "day";
                Calendar cal = Calendar.getInstance();
                int _days = cal.get(Calendar.DAY_OF_WEEK);
                logParams.setEnd(7 - _days);
                break;
            case PRE_MONTH:
                if (logParams.getValue() == 0) {
                    _days = this.getCurrentDays();
                    logParams.setValue(_days);
                    int preMonthDays = this.getMonthDays(-1);
                    logParams.setBegin(_days);
                    logParams.setEnd(_days + preMonthDays);
                }
                days = logParams.getValue();
                break;
            case THIS_MONTH:
                logParams.setEnd(this.getCurrentDays());
                fmt = "month";
                break;
        }
        values.put("logTags", wrap(logParams));
        values.putAll(this.logService.getDateStr(days, fmt));
        values.put("logParams", logParams);
        values.put("logs", logService.findAllByDays(logParams.getValue(), logParams.getKeyword()));
        return values;
    }

    private Map wrap(LogParams logParams) {
        Map<String, Object> values = new HashMap<>();
        LogParams pre = this.nextOrPre(logParams, true);
        LogParams next = this.nextOrPre(logParams, false);
        if (pre != null) values.put("pre", pre);
        if (next != null) values.put("next", next);
        return values;
    }

    private LogParams nextOrPre(final LogParams logParams, boolean isPre) {

        if (logParams.getValue() == logParams.getBegin() && !isPre) return null;
        if (logParams.getValue() == logParams.getEnd() && isPre) return null;
        if (logParams.getBegin() == logParams.getEnd()) return null;

        if (isPre && logParams.getValue() + 1 == logParams.getEnd()) {
            return null;
        }
        LogParams results = logParams.clone();

        if (isPre) results.setValue(logParams.getValue() + 1);
        if (!isPre) results.setValue(logParams.getValue() - 1);
        return results;
    }

    @RequestMapping(value = "/{days}/before", method = RequestMethod.GET)
    public JsonHeaderWrapper findAllLogsByBefore(@PathVariable("days") int days) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            Map<String, Object> values = new HashMap<>();
            values.putAll(this.logService.getDateStr(days, "day"));
            values.put("logs", logService.findAllByBeforeDays(days));
            jsonHeaderWrapper.setData(values);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }


    enum QueryType {
        ALL("all"), TODAY("today"), YESTERDAY("yesterday"),
        RECENT7("recent7"),
        PRE_WEEK("preweek"),
        THIS_WEEK("thisweek"),
        PRE_MONTH("premonth"),
        THIS_MONTH("thismonth");
        private String type;

        QueryType(String type) {
            this.type = type;
        }

        public static QueryType to(String type) {
            for (QueryType value : QueryType.values()) {
                if (value.type.equals(type)) {
                    return value;
                }
            }
            return TODAY;
        }
    }// 获得本周一0点时间

    public int getMonthDays(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONDAY, month);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getCurrentDays() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
