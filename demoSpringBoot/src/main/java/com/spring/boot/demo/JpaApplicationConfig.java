//package com.spring.boot.demo;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.support.PersistenceExceptionTranslator;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.OpenJpaDialect;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.util.logging.Logger;
//
//@Configuration
//@EnableJpaRepositories("com.spring.boot.demo.entity")
//@EnableTransactionManagement
//class JpaApplicationConfig {
//    private static final Logger logger = Logger
//            .getLogger(JpaApplicationConfig.class.getName());
//    @Bean
//    public AbstractEntityManagerFactoryBean entityManagerFactory() {
//        logger.info("Loading Entity Manager...");
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setPersistenceUnitName("transactions-optional");
//        return factory;
//    }
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        logger.info("Loading Transaction Manager...");
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return txManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslator persistenceExceptionTranslator() {
//        return new OpenJpaDialect();
//    }
//}