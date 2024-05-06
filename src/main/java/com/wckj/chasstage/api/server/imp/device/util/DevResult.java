package com.wckj.chasstage.api.server.imp.device.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DevResult {
    /**
     * 1正常              0异常
     */
    private int code;
    /**
     * 描述消息
     */
    private String message;
    /**
     * 失败原因给客户端使用
     */
    private String reason;
    /**
     * 返回数据集合
     */
    private Map<String, Object> data;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private Integer totalPage;
    private Integer recordStart;
    private Integer pageSize;
    private Integer draw;

    public void add(String name, Object obj) {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        data.put(name, obj);
    }

    public Object get(String name) {
        Object value = null;
        if (data != null) {
            value = data.get(name);
        }
        return value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setCodeMessage(int code,String message) {
        this.code = code;
        this.message = message;
    }
    public static DevResult success() {
        DevResult w = new DevResult();
        w.setCode(1);
        return w;
    }

    public static DevResult success(String message) {
        DevResult w = new DevResult();
        w.setCode(1);
        w.setMessage(message);
        return w;
    }

    public static DevResult error(String message) {
        DevResult w = new DevResult();
        w.setCode(0);
        w.setMessage(message);
        return w;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordStart() {
        return recordStart;
    }

    public void setRecordStart(Integer recordStart) {
        this.recordStart = recordStart;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        String st;
        try {
            st = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return st;
    }

    public static DevResult parse(String str) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析器支持解析单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 解析器支持解析结束符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        DevResult r = mapper.readValue(str, DevResult.class);
        return r;

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
