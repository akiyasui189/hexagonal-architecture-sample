package io.spring.start.sample.hexagon.adapter.persistences.configurations;

import io.spring.start.sample.hexagon.adapter.persistences.daos.UserDao;
import io.spring.start.sample.hexagon.adapter.persistences.mappers.UserMapper;
import io.spring.start.sample.hexagon.adapter.persistences.repositories.UserRepositoryPersistenceImpl;
import io.spring.start.sample.hexagon.core.repositories.UserRepository;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@MapperScan(basePackages = {"io.spring.start.sample.hexagon.adapter.persistences.mappers"})
@Configuration
public class HexagonAdapterPersistenceConfig {

    @Autowired
    private DataSource dataSource;

    /*
    @Autowired
    private UserDao userDao;

     */

    /*
    @Autowired
    private UserMapper userMapper;

    @Primary
    @Bean
    public UserRepository getUserRepository () {
        return new UserRepositoryPersistenceImpl(this.userMapper);
    }

     */

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate batchSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
    }

    @Primary
    @Bean
    public SqlSessionTemplate simpleSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.SIMPLE);
    }

}
