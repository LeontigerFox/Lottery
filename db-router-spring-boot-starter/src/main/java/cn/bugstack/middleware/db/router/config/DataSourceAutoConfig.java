package cn.bugstack.middleware.db.router.config;

import cn.bugstack.middleware.db.router.DBRouterConfig;
import cn.bugstack.middleware.db.router.DBRouterJoinPoint;
import cn.bugstack.middleware.db.router.dynamic.DynamicDataSource;
import cn.bugstack.middleware.db.router.dynamic.DynamicMybatisPlugin;
import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.bugstack.middleware.db.router.strategy.impl.DBRouterStrategyHashCode;
import cn.bugstack.middleware.db.router.util.PropertyUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 数据源配置解析
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/22
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Configuration
public class DataSourceAutoConfig implements EnvironmentAware {

    /**
     * 数据源配置组
     */
    private Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    /**
     * 默认数据源配置
     */
    private Map<String, Object> defaultDataSourceConfig;

    /**
     * 分库数量
     */
    private int dbCount;

    /**
     * 分表数量
     */
    private int tbCount;

    /**
     * 路由字段
     */
    private String routerKey;

    @Bean(name = "db-router-point")
    @ConditionalOnMissingBean
    public DBRouterJoinPoint point(DBRouterConfig dbRouterConfig, IDBRouterStrategy dbRouterStrategy) {
        return new DBRouterJoinPoint(dbRouterConfig, dbRouterStrategy);
    }

    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount, routerKey);
    }

    @Bean
    public Interceptor plugin() {
        return new DynamicMybatisPlugin();
    }

    @Bean
    public DataSource dataSource() {
        // 创建数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (String dbInfo : dataSourceMap.keySet()) {
            Map<String, Object> objMap = dataSourceMap.get(dbInfo);
            targetDataSources.put(dbInfo, new DriverManagerDataSource(objMap.get("url").toString(), objMap.get("username").toString(), objMap.get("password").toString()));
        }

        // 设置数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(new DriverManagerDataSource(defaultDataSourceConfig.get("url").toString(), defaultDataSourceConfig.get("username").toString(), defaultDataSourceConfig.get("password").toString()));

        return dynamicDataSource;
    }

    @Bean
    public IDBRouterStrategy dbRouterStrategy(DBRouterConfig dbRouterConfig) {
        return new DBRouterStrategyHashCode(dbRouterConfig);
    }

    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(dataSourceTransactionManager);
        transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return transactionTemplate;
    }

    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "mini-db-router.jdbc.datasource.";

        dbCount = Integer.valueOf(environment.getProperty(prefix + "dbCount"));
        tbCount = Integer.valueOf(environment.getProperty(prefix + "tbCount"));
        routerKey = environment.getProperty(prefix + "routerKey");

        // 分库分表数据源
        String dataSources = environment.getProperty(prefix + "list");
        assert dataSources != null;
        for (String dbInfo : dataSources.split(",")) {
            Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dbInfo, Map.class);
            dataSourceMap.put(dbInfo, dataSourceProps);
        }

        // 默认数据源
        String defaultData = environment.getProperty(prefix + "default");
        defaultDataSourceConfig = PropertyUtil.handle(environment, prefix + defaultData, Map.class);

    }

}
