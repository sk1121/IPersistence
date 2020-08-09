package com.ai.sqlsession;

import com.ai.config.BoundSql;
import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;
import com.ai.utils.GenericTokenParser;
import com.ai.utils.ParameterMappingTokenHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public class SimpleExecutor implements Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException {
        // 1.获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.解析sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        // 3.设置参数
        connection.prepareStatement(boundSql.getContent());
        // 获取预处理对象
        // 4.执行sql
        // 5.封装结果集
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sqlText = genericTokenParser.parse(sql);
        return new BoundSql(sqlText,parameterMappingTokenHandler.getParameterMappings());
    }
}
