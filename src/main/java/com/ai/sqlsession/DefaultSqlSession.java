package com.ai.sqlsession;

import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;

import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId,Object... params) {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMap().get(statementId);
        return simpleExecutor.query(configuration, mappedStatement, params);
    }

    public <E> E selectOne(String statementId,Object... params) {
        List<Object> list = selectList(statementId, params);
        if (list.size() == 1) {
            return (E) list.get(0);
        }
        throw new RuntimeException("数据不存在或者存在多条");
    }
}
