package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example")
@MapperScan("com.example.mapper")   // 扫描 Mapper 接口，让 Spring 生成代理对象供 @Autowired 注入
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
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
        cfg.setMapUnderscoreToCamelCase(true);   // 下划线转驼峰，chc_cust_name -> chcCustName
        cfg.setCacheEnabled(true);
        cfg.setAggressiveLazyLoading(true);
        cfg.setLazyLoadingEnabled(true);
        bean.setConfiguration(cfg);

        // 加载 Mapper XML（resources 下与接口同包的 XML）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/example/mapper/**/*.xml"));

        return bean.getObject();
    }
}
