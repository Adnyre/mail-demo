package adnyre.maildemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Value("${datastore.url}")
    private String dataStoreUrl;

    @Value("${datastore.user}")
    private String user;

    @Value("${datastore.pass}")
    private String pass;

    @Value("${datastore.driver}")
    private String driver;

    @Bean
    public DataSource restDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(dataStoreUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }
}