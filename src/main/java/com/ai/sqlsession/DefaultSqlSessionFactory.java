package com.ai.sqlsession;

import com.ai.pojo.Configuration;

/**
 * @author songkang
 * @date 2020/8/9
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        DefaultSqlSession sqlSession = new DefaultSqlSession(configuration);
        return sqlSession;
    }
}
