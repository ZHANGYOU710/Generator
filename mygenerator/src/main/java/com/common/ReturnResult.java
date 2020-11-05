package com.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

/**
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */

@Data
public class ReturnResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer ret;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static ReturnResult build(Integer ret, String msg, Object data) {
        return new ReturnResult(ret, msg, data);
    }

    public static ReturnResult ok(Object data) {
        return new ReturnResult(data);
    }

    public static ReturnResult ok() {
        return new ReturnResult(null);
    }

    public static ReturnResult ok(String msg, Object data) {
        return new ReturnResult(Cons.RESULT_OK, msg, data);
    }

    public ReturnResult() {

    }

    public static ReturnResult build(Integer ret, String msg) {
        return new ReturnResult(ret, msg, null);
    }

    public static ReturnResult build(String msg) {
        return new ReturnResult(Cons.RESULT_ERR, msg, null);
    }

    public ReturnResult(Integer ret, String msg, Object data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }

    public ReturnResult(Object data) {
        this.ret = Cons.RESULT_OK;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.ret == 200;
//    }


    /**
     * 将json结果集转化为ReturnResult对象
     *
     * @param jsonData json数据
     * @param clazz    ReturnResult中的object类型
     * @return
     */
    public static ReturnResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ReturnResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("ret").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ReturnResult format(String json) {
        try {
            return MAPPER.readValue(json, ReturnResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static ReturnResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("ret").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
