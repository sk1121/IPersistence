package com.ai.sqlsession;

import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params) throws SQLException, Exception;
}
