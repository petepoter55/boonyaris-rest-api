//package com.rest.api.boonyarisRestApi.config;
//
//import org.apache.tomcat.jdbc.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
//import org.apache.tomcat.jdbc.pool.PooledConnection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import javax.management.MBeanServer;
//import java.lang.management.ManagementFactory;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Configuration
//@EnableScheduling
//public class MyConnectionPoolLogger {
//    private static final Logger logger = LogManager.getLogger(MyConnectionPoolLogger.class);
//
//    @Autowired
//    private DataSource dataSource;
//
//    public static void monitorConnectionPool(ConnectionPool connectionPool) {
//        // ดึงข้อมูลเกี่ยวกับ connection ใน pool
//        int numActive = connectionPool.getActive();
//        int numIdle = connectionPool.getIdle();
//        int total = connectionPool.getSize();
//
//        // นำข้อมูลมา log
//        logger.info("connections pools | Total: {} Active: {} Idle: {}", total, numActive, numIdle);
//    }
//
//    @Scheduled(cron = "10 * * ? * *") //every 0.30 sec
//    private void logConnectionPoolStatus() throws SQLException {
//        if (dataSource != null) {
//            org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource = dataSource;
//            for (int i = 0; i < 10; i++) {
//                Connection connection = dataSource.getConnection();
//                // Perform operations using the connection
//                logger.info("Connection {} used.", i);
//                connection.createStatement().execute("SELECT id FROM USERPROFILE.ACCOUNT WHERE ID = 21");
//            }
//            monitorConnectionPool(tomcatDataSource.getPool());
//        } else {
//            logger.info("DataSource is not an instance of Tomcat DataSource.");
//        }
//    }
//}
