package com.ai.sqlsession;

import com.ai.config.BoundSql;
import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;
import com.ai.utils.GenericTokenParser;
import com.ai.utils.ParameterMapping;
import com.ai.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public class SimpleExecutor implements Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.解析sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        // 3.设置参数
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getContent());
        // 获取预处理对象
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Class paramterTypeClass = Class.forName(mappedStatement.getParameterType());
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            Field field = paramterTypeClass.getDeclaredField(parameterMapping.getContent());
            field.setAccessible(true);
            Object o = field.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        // 4.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.封装结果集
        String resultType = mappedStatement.getResultType();
        Class resultTypeClass = Class.forName(resultType);
        List<Object> list = new ArrayList<>();
        while (resultSet.next()) {
            Object clazz =  resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                //  字段值
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(clazz, value);
            }
            list.add(clazz);
        }
        return (List<E>) list;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sqlText = genericTokenParser.parse(sql);
        return new BoundSql(sqlText, parameterMappingTokenHandler.getParameterMappings());
    }
}
