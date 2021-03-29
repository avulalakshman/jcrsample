package com.lwl.jcrsample.config;


import lombok.Getter;
import lombok.Setter;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.config.RepositoryConfigurationParser;
import org.springframework.beans.factory.BeanCreationException;
import org.xml.sax.InputSource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Setter
public class JackrabbitRepositoryConfigFactory {
    private static final String JCR_REP_HOME = "jcr.rep.home";
    private String jcrHome;
    private String configFilename = "repository.xml";
    private String propertiesFilename = "jackrabbit.properties";

    public RepositoryConfig create() throws Exception {
        Properties properties = new Properties();
        try{
            InputStream is = JackrabbitRepositoryConfigFactory.class.getClassLoader().getResourceAsStream(propertiesFilename);
            if (is != null) {
                try {
                    Properties p = new Properties();
                    p.load(is);
                    properties.putAll(p);
                } finally {
                    is.close();
                }
            }
            String home = properties.getProperty(JCR_REP_HOME);
            properties.setProperty(RepositoryConfigurationParser.REPOSITORY_HOME_VARIABLE, home);
            is = JackrabbitRepositoryConfigFactory.class.getClassLoader().getResourceAsStream(configFilename);
            if (is == null) {
                throw new FileNotFoundException(configFilename);
            }
            return RepositoryConfig.create(new InputSource(is), properties);
        } catch(ConfigurationException e){
            throw new BeanCreationException("Unable to configure repository with: " + configFilename + " and " + properties);
        }
    }

}