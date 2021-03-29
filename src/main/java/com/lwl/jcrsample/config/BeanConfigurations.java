package com.lwl.jcrsample.config;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jcr.Repository;

@Configuration
public class BeanConfigurations {
        
        private final String jcrhome = "jcrhome";
        @Bean
        public JackrabbitRepositoryConfigFactory jackrabbitRepositoryConfigFactory(){
            JackrabbitRepositoryConfigFactory jrConfig = new JackrabbitRepositoryConfigFactory();
            jrConfig.setJcrHome(jcrhome);
            return jrConfig;
        }

        @Bean
        public RepositoryConfig repositoryConfig()throws  Exception{
            return jackrabbitRepositoryConfigFactory().create();
        }
        @Bean
        public RepositoryImpl repository() throws  Exception{
           return RepositoryImpl.create(repositoryConfig());
        }

}
