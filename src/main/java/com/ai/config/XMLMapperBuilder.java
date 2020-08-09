package com.ai.config;

import com.ai.io.Resources;
import com.ai.pojo.Configuration;
import com.ai.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author songkang
 * @date 2020/8/8
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException {
        // 获取mapper.xml文件位置
        Document mapperxml = new SAXReader().read(in);
        // 获取mapper.xml根元素及其属性namespace
        Element mapperRootElement = mapperxml.getRootElement();
        String namespace = mapperRootElement.attributeValue("namespace");
        // select查询
        List<Element> list = mapperRootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            configuration.getMap().put(namespace + "." + id, mappedStatement);
        }
        // 增删改先不写
    }
}
