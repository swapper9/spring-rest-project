package ru.swap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence.properties"})
public class JpaConfig {

  @Autowired
  private Environment env;


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setDatabase(Database.POSTGRESQL);

    factory.setDataSource(dataSource());
    factory.setPackagesToScan("ru.swap.entity");
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setJpaProperties(additionalProperties());
    return factory;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource source = new DriverManagerDataSource();
    source.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
    source.setUrl(env.getRequiredProperty("spring.datasource.url"));
    source.setUsername(env.getRequiredProperty("spring.datasource.user"));
    source.setPassword(env.getRequiredProperty("spring.datasource.pass"));
    return source;
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
    properties.setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
    properties.setProperty("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
    return properties;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }



}
