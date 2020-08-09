package com.ai.config;

import com.ai.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public class BoundSql {

    private String content;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String content, List<ParameterMapping> parameterMappings) {
        this.content = content;
        this.parameterMappings = parameterMappings;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
