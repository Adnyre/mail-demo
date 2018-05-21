package adnyre.maildemo.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Primary
    @Bean(name = "datasource1")
    @ConfigurationProperties("datastore1")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}