package com.ai.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songkang
 * @Date 2020/8/7
 */
public class Configuration {

    private DataSource dataSource;

    /**
     * key:statementId
     * value:对应的statement
     */
    private Map<String, MappedStatement> map = new HashMap<String, MappedStatement>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMap() {
        return map;
    }

    public void setMap(Map<String, MappedStatement> map) {
        this.map = map;
    }
}
