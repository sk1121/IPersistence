package com.ai.pojo;

/**
 * @author songkang
 * @Date 2020/8/7
 */
public class MappedStatement {

    // id
    private String id;
    // 入参
    private String parameterType;
    // 出参
    private String resultType;
    // sql
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
