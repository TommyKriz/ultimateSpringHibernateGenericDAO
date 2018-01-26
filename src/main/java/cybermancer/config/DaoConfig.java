package cybermancer.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "cybermancer.dao")
@PropertySource("classpath:/config/dao.properties")
@EnableTransactionManagement
public class DaoConfig {

	private static final String PACKAGES_TO_SCAN = "cybermancer.dto";

	private static final String PERSISTENCE_UNIT_NAME = "PersistenceUnitFromDaoConfigJava";

	private static final String HIBERNATE_DIALECT_KEY = "hibernate.dialect";

	private static final String HIBERNATE_HBM2DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";

	@Value(value = "${javax.persistence.jdbc.driver}")
	private String JDBC_DRIVER_CLASS_NAME;

	@Value(value = "${javax.persistence.jdbc.url}")
	private String JDBC_URL;

	@Value(value = "${javax.persistence.jdbc.user}")
	private String JDBC_USER;

	@Value(value = "${javax.persistence.jdbc.password}")
	private String JDBC_PASSWORD;

	@Value(value = "${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Value(value = "${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
		lcemfb.setDataSource(getDataSource());
		lcemfb.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		lcemfb.setPackagesToScan(PACKAGES_TO_SCAN);
		lcemfb.setJpaProperties(additionalHibernateJpaProperties());
		return lcemfb;
	}

	@Bean
	public JpaVendorAdapter getJpaVendorAdapter() {
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return adapter;
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(JDBC_DRIVER_CLASS_NAME);
		dataSource.setUrl(JDBC_URL);
		dataSource.setUsername(JDBC_USER);
		dataSource.setPassword(JDBC_PASSWORD);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
				getEntityManagerFactoryBean().getObject());
		return jpaTransactionManager;
	}

	private Properties additionalHibernateJpaProperties() {
		Properties properties = new Properties();
		properties.setProperty(HIBERNATE_HBM2DDL_AUTO_KEY,
				HIBERNATE_HBM2DDL_AUTO);
		properties.setProperty(HIBERNATE_DIALECT_KEY, HIBERNATE_DIALECT);
		return properties;
	}

}
