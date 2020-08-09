package com.ai.config;

import com.ai.io.Resources;
import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author songkang
 * @Date 2020/8/7
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(in);
        // 解析configuration的datasource部分。
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        // 读取mapper.xml文件并解析
        Map<String, MappedStatement> map = new HashMap<String, MappedStatement>();
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            InputStream mapperIn = Resources.getResourceAsSteam(resource);
            xmlMapperBuilder.parse(mapperIn);
        }

        return configuration;
    }
}
