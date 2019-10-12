package ru.swap.javaconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.client.RestTemplate;
import ru.swap.quartz.CronQuartzTask;
import ru.swap.quartz.QuartzTask;

import java.util.Properties;


@Configuration
@PropertySource(value = "classpath:app.properties")
@ComponentScan(basePackages = "ru.swap")
@EnableScheduling
public class ApplicationConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Value("${spring.datasource.driverClassName}")
  private String driverClass;

  @Value("${spring.datasource.url}")
  private String jdbcUrl;

  @Value("${spring.datasource.username}")
  private String jdbcUserName;

  @Value("${spring.datasource.password}")
  private String jdbcPassword;

  @Value("classpath:dbschema.sql")
  private Resource dbschemaSqlScript;

  @Value("classpath:test-data.sql")
  private Resource testDataSqlScript;

  @Bean(name = "dataSource")
  public DriverManagerDataSource getDriverManagerDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClass);
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(jdbcUserName);
    dataSource.setPassword(jdbcPassword);
    return dataSource;
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer() {
    final DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(getDriverManagerDataSource());
    initializer.setDatabasePopulator(getDatabasePopulator());
    return initializer;
  }

  private DatabasePopulator getDatabasePopulator() {
    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(dbschemaSqlScript);
    populator.addScript(testDataSqlScript);
    return populator;
  }

  @Value("${java.mail.username}") String mailUsername;
  @Value("${java.mail.password}") String mailPassword;
  @Value("${java.mail.host}") String mailHost;


  @Bean(name = "mailSender")
  public JavaMailSenderImpl getJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setUsername(mailUsername);
    javaMailSender.setPassword(mailPassword);
    javaMailSender.setPort(465);

    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.auth", true);
    javaMailProperties.put("mail.smtp.starttls.enable", true);
    javaMailProperties.put("mail.smtp.starttls.required", true);
    javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    javaMailProperties.put("mail.smtp.host", mailHost);

    javaMailSender.setJavaMailProperties(javaMailProperties);
    return javaMailSender;
  }

  @Bean(name = "velocityEngine")
  public VelocityEngineFactoryBean getVelocityEngineFactoryBean() {
    VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
    bean.setResourceLoaderPath("/WEB-INF/email-templates/");
    return bean;
  }

  @Bean(name = "restTemplate")
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setPackagesToScan(new String[] {"ru.swap.entity"});
    em.setDataSource(getDriverManagerDataSource());

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setShowSql(true);
    em.setJpaVendorAdapter(vendorAdapter);

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL95Dialect");
    jpaProperties.put("hibernate.show_sql",true);
    jpaProperties.put("hibernate.format_sql","false");
    jpaProperties.put("hibernate.hbm2ddl.auto","update");

    em.setJpaProperties(jpaProperties);
    return em;
  }

  @Bean(name = "jpaTransactionManager")
  public JpaTransactionManager getJpaTransactionManager() {
    JpaTransactionManager jpa = new JpaTransactionManager();
    jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getNativeEntityManagerFactory());
    return jpa;
  }

    @Bean(name = "simpleTrigger")
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean stfb = new SimpleTriggerFactoryBean();

        stfb.setJobDetail(getSimpleQuartzJob().getObject());

        stfb.setRepeatInterval(1000);
        stfb.setStartDelay(1000);
        return stfb;
    }

    @Bean(name = "cronTrigger")
    public CronTriggerFactoryBean getCronTriggerFactoryBean() {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();

        ctfb.setJobDetail(getQuartzCronJob().getObject());

        ctfb.setCronExpression("0/30 * * * * ?");
        return ctfb;
    }

    @Bean(name = "simpleQuartzJob")
    public MethodInvokingJobDetailFactoryBean getSimpleQuartzJob() {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setTargetBeanName("simpleQuartzTask");
        factoryBean.setTargetMethod("simpleTaskMethod");
        return factoryBean;
    }
    @Bean(name = "quartzCronJob")
    public MethodInvokingJobDetailFactoryBean getQuartzCronJob() {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setTargetBeanName("cronQuartzTask");
        factoryBean.setTargetMethod("cronTaskMethod");
        return factoryBean;
    }

    @Bean(name = "simpleQuartzTask")
    public QuartzTask getSimpleQuartzTask() {
        return new QuartzTask();
    }

    @Bean(name = "cronQuartzTask")
    public CronQuartzTask getCronQuartzTask() {
        return new CronQuartzTask();
    }

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean scheduler  = new SchedulerFactoryBean();
        scheduler.setTriggers(getSimpleTriggerFactoryBean().getObject(), getCronTriggerFactoryBean().getObject());
        return scheduler ;
    }
}
