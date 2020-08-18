package com.ai.sqlsession;

import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
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

    public <E> List<E> selectList(String statementId,Object... params) throws Exception {
        Executor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMap().get(statementId);
        return simpleExecutor.query(configuration, mappedStatement, params);
    }

    public <E> E selectOne(String statementId,Object... params) throws Exception {
        List<Object> list = selectList(statementId, params);
        if (list.size() == 1) {
            return (E) list.get(0);
        }
        throw new RuntimeException("数据不存在或者存在多条");
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });
        return (T) o;
    }
}
