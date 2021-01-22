package cn.itcast.jdbc;

import cn.itcast.domain.Emp;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JdbcDemo1 {

    public static void main(String[] args) throws Exception {

        ClassLoader classLoader = JdbcDemo1.class.getClassLoader();
        URL resource = classLoader.getResource("\"jdbc.properties\"");
        String path = resource.getPath();
        Properties properties = new Properties();
        properties.load(new FileReader(path));

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///db1", "root", "hc123456");
        connection.setAutoCommit(false);
        connection.commit();
        connection.rollback();

//        String sql = "update student set math = 100 where id = 1";
//        String sql = "select * from student";
        String sql = "select * from where username = ? password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "root");
        preparedStatement.setString(2, "hc123456");
        ResultSet set1 = preparedStatement.executeQuery();
        Statement statement = connection.createStatement();
//        int count = statement.executeUpdate(sql);
        ResultSet set = statement.executeQuery(sql);
        while (set.next()){
            double balance = set.getDouble(3);
            System.out.println(balance);
        }
//        System.out.println(count);
        statement.close();
        connection.close();

        DataSource dataSource = new ComboPooledDataSource("otherc3p0");
        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            System.out.println(i+"="+connection);
//            connection.close();
//            if (i==5){
//                connection.close();
//            }
        }

        Properties properties = new Properties();
        InputStream resourceAsStream = JdbcDemo1.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "update student set english = 100 where id =?";
        int count = jdbcTemplate.update(sql, 1);
        System.out.println(count);
        sql = "select * from emp where id = 1";
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql);
        System.out.println(stringObjectMap);
        sql = "select * from emp";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.out.println(maps);
//        jdbcTemplate.query(sql, new RowMapper<Emp>() {
//            @Override
//            public Emp mapRow(ResultSet resultSet, int i) throws SQLException {
//                return null;
//            }
//        });
//        List<Emp> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Emp.class));
        Long aLong = jdbcTemplate.queryForObject(sql, Long.class);

    }

    @Test
    public void test1(){
        System.out.println("hh ");

        Jedis jedis = new Jedis("localhost",6379);
//        jedis.set("username", "zhangsan");
//        jedis.setex("username", 10,"zhangsan");
//        System.out.println(jedis.get("username"));
//        jedis.hset("user", "name", "evan");
//        jedis.hset("user", "age", "12");
//        jedis.hset("user", "sex", "male");
//        Map<String, String> users = jedis.hgetAll("user");
//        Set<String> keys = users.keySet();
//        for (String key: keys) {
//            System.out.println(users.get(key));
//        }

//        jedis.lpush("mylist", "a", "b", "c");
//        jedis.rpush("mylist", "a", "b", "c");
//        System.out.println(jedis.lrange("mylist", 0, -1));
//        System.out.println(jedis.lpop("mylist"));
//        System.out.println(jedis.rpop("mylist"));
//        System.out.println(jedis.lrange("mylist", 0, -1));

//        jedis.sadd("mylist2", "a", "b", "c");
//        System.out.println(jedis.smembers("mylist2"));

//        jedis.zadd("mylist3", 40, "evan");
//        jedis.zadd("mylist3", 20, "evan2");
//        jedis.zadd("mylist3", 40, "evan3");
//        System.out.println(jedis.zrange("mylist3", 0, -1));
//        jedis.close();

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);

        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        Jedis resource = jedisPool.getResource();
        resource.set("key4", "key4");
        System.out.println(resource.get("key4"));
        jedisPool.close();


    }


}
