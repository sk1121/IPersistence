package com.ai.sqlsession;

import com.ai.config.XMLConfigBuilder;
import com.ai.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author songkang
 * @Date 2020/8/7
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(InputStream in) throws PropertyVetoException, DocumentException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        return null;
    }
}
