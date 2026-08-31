package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
@ComponentScan("com.example")

public class SpringConfig {
    DruidDataSource dataSource = new DruidDataSource();

    @Bean
    public DataSource dataSource() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/my_db3");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("xiahongzhen0");

        dataSource.setValidationQuery("SELECT 1");
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());

        org.apache.ibatis.session.Configuration cfg = new org.apache.ibatis.session.Configuration();
        cfg.setMapUnderscoreToCamelCase(true);
        cfg.getTypeAliasRegistry().getTypeAliases();
        cfg.setCacheEnabled(true);
        cfg.setAggressiveLazyLoading(true);
        cfg.setLazyLoadingEnabled(true);
        bean.setConfiguration(cfg);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/"));


        return bean.getObject();
    }
}
