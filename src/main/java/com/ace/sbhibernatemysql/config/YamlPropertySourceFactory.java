package com.ace.sbhibernatemysql.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

//this class is use to read properties form custom .yml file
//if we do not implement we cannot able to read properties from custom yml file. 
public class YamlPropertySourceFactory implements PropertySourceFactory{

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		 Properties propertiesFromYaml = loadYamlIntoProperties(resource);
	        String sourceName = name != null ? name : resource.getResource().getFilename();
	        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
	}

	private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
}
