package com.biglibrary.catalog_service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.util.TimeZone;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer postgresContainer() {
		return new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));
	}

	@Bean
	@Primary
	DataSource dataSource(PostgreSQLContainer postgresContainer) {
		DataSourceBuilder<?> builder = DataSourceBuilder.create();
		builder.url(postgresContainer.getJdbcUrl() + "?TimeZone=Asia/Kolkata");
		builder.username(postgresContainer.getUsername());
		builder.password(postgresContainer.getPassword());
		return builder.build();
	}

	@PostConstruct
	public void init() {
		// Setting the proper recognized IANA timezone
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	}

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Kolkata");
		SpringApplication.from(CatalogServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
