package com.ai.sqlsession;

import java.util.List;

/**
 * @author songkang
 * @date 2020/8/9
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId,Object... params);

    <E> E selectOne(String statementId,Object... params);
}
