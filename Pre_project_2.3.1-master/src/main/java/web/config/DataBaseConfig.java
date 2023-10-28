package web.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(value = "web")
public class DataBaseConfig {

    private final Environment env;

    public DataBaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));
        ds.setUrl(env.getRequiredProperty("db.url"));
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(getDataSource());

        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.show_sql", env.getRequiredProperty("show_sql"));
        hibernateProp.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("auto"));
        hibernateProp.put("hibernate.dialect", env.getRequiredProperty("dialect"));

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPackagesToScan("web");
        entityManagerFactory.setJpaProperties(hibernateProp);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager getTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return tm;
    }
}