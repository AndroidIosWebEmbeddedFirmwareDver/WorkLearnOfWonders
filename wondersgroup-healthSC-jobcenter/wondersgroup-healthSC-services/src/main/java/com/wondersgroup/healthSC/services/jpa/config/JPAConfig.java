package com.wondersgroup.healthSC.services.jpa.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * JPA config
 *
 * @author zhuchunliu
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.wondersgroup.healthSC.services.jpa.repository")
@EnableTransactionManagement
public class JPAConfig {

    @Autowired
    private Environment environment;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.wondersgroup.healthSC.services.jpa.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        if(environment.getActiveProfiles()[0].equals("de")){
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql","true");
        }
        return properties;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory.getObject());
        return manager;
    }

    @Bean(name = "healthSCDatasource", initMethod = "init",destroyMethod = "close")
    @Profile("!build-test")
    public DataSource dataSource(Environment env) throws PropertyVetoException, SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(env.getProperty("mysql.connection.url"));
        dataSource.setUsername(env.getProperty("mysql.connection.username"));
        dataSource.setPassword(env.getProperty("mysql.connection.password"));
        dataSource.setFilters("stat");
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(200);
        dataSource.setMaxWait(60000L);
        dataSource.setTimeBetweenEvictionRunsMillis(60000L);
        dataSource.setMinEvictableIdleTimeMillis(300000L);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("build-test")
    public DataSource unitTestDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).addScript("db/init.sql").build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
