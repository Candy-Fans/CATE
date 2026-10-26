package com.javaclimb.cate.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DataSourceConfigTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DruidDataSourceAutoConfigure.class))
            .withUserConfiguration(DataSourceConfig.class)
            .withInitializer(context -> {
                try {
                    for (PropertySource<?> source : new YamlPropertySourceLoader().load(
                            "datasource-test", new ClassPathResource("application-datasource.yml"))) {
                        context.getEnvironment().getPropertySources().addLast(source);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            })
            .withPropertyValues(
                    "CATE_DB_URL=jdbc:mysql://localhost:1/cate",
                    "CATE_DB_USERNAME=test",
                    "CATE_DB_PASSWORD=test",
                    "CATE_DRUID_USERNAME=test",
                    "CATE_DRUID_PASSWORD=test",
                    // Configuration tests must not connect to a real database.
                    "spring.datasource.druid.initialSize=0",
                    "spring.datasource.druid.minIdle=0");

    @Test
    void installsSqlStatisticsFilterOnTheApplicationDataSource() {
        contextRunner.run(context -> {
            assertThat(context).hasNotFailed().hasSingleBean(DataSource.class);
            DruidDataSource dataSource = (DruidDataSource) context.getBean(DataSource.class);
            StatFilter statFilter = context.getBean(StatFilter.class);
            assertThat(dataSource.getProxyFilters()).contains(statFilter);
            assertThat(statFilter.isMergeSql()).isTrue();
            assertThat(statFilter.isLogSlowSql()).isTrue();
            assertThat(statFilter.getSlowSqlMillis()).isEqualTo(1000L);
        });
    }

    @Test
    void bindsNestedPoolSettingsAndKeepsBasicConnectionProperties() {
        contextRunner.run(context -> {
            assertThat(context).hasNotFailed().hasSingleBean(DataSource.class);
            DruidDataSource dataSource = (DruidDataSource) context.getBean(DataSource.class);
            assertThat(dataSource.getMaxActive()).isEqualTo(20);
            assertThat(dataSource.getMaxWait()).isEqualTo(60000L);
            assertThat(dataSource.getValidationQuery()).isEqualTo("SELECT 1 FROM DUAL");
            assertThat(dataSource.getUrl()).isEqualTo("jdbc:mysql://localhost:1/cate");
            assertThat(dataSource.getUsername()).isEqualTo("test");
            assertThat(dataSource.getPassword()).isEqualTo("test");
        });
    }
}
