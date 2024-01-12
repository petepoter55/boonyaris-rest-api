//package com.rest.api.boonyarisRestApi.service;
//
//import com.rest.api.boonyarisRestApi.controller.AccountController;
//import com.rest.api.boonyarisRestApi.entity.Account;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class ConnectionPoolTesterService {
//    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolTesterService.class);
//
////    @Autowired
////    private EntityManager entityManager;
//    private final DataSource dataSource;
//
//    public ConnectionPoolTesterService(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @PostConstruct
//    public void testConnectionPool() throws SQLException {
//        Connection connection = null;
//        try {
//            for(int i = 0; i < 3; i++){
//                try {
//                    connection = dataSource.getConnection();
//                    connection.createStatement().execute("SELECT id FROM USERPROFILE.ACCOUNT WHERE ID = 21");
//                }finally {
////                    if (connection != null){
////                        connection.close();
////                    }
//                }
//            }
//            // Use the connection to perform database operations
//            System.out.println("Connection obtained successfully.");
//
//            // Simulate using the connection for some time
////            TimeUnit.SECONDS.sleep(2);
//
//            // The connection will be automatically released back to the pool
//        } catch (SQLException e) {
//            logger.debug("Error testing Connection Pool: " + e.getMessage());
//        }
////        try {
////            for (int i = 0; i < 4; i++) {
////                Query query = entityManager.createNativeQuery("SELECT id FROM USERPROFILE.ACCOUNT WHERE ID = :id");
////                query.setParameter("id", "21");
////
////                logger.info("id result {}",query.getResultList().get(0));
////                logger.info("Connection {} obtained from Connection Pool.",(i + 1));
////            }
////            logger.debug("Connection Pool is working correctly.");
////            logger.info("Connection Pool is working correctly.");
////        } catch (Exception e) {
////            logger.debug("Error testing Connection Pool: " + e.getMessage());
////        }
//    }
//}
