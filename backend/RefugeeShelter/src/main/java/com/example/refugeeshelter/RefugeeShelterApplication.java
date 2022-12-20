package com.example.refugeeshelter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class RefugeeShelterApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String result = bCryptPasswordEncoder.encode("akrik1234");
        System.out.println(result);

        SpringApplication.run(RefugeeShelterApplication.class, args);
    }

//    @Bean("masterDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    DataSource masterDataSource() {
//        log.info("Create main database...");
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean("slaveDataSource")
//    @ConfigurationProperties(prefix = "spring.ro-datasource")
//    DataSource slaveDataSource() {
//        log.info("Create test database...");
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    DataSource primaryDataSource(
//            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
//            @Autowired @Qualifier("slaveDataSource") DataSource slaveDataSource
//    ) {
//        log.info("create routing datasource...");
//
//        Map<Object, Object> map = new HashMap<>();
//        map.put("masterDataSource", masterDataSource);
//        map.put("slaveDataSource", slaveDataSource);
//        RoutingDataSource routing = new RoutingDataSource();
//        routing.setTargetDataSources(map);
//        routing.setDefaultTargetDataSource(masterDataSource);
//        return routing;
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


