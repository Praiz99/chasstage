package com.wckj.taskClient.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wckj.taskClient.server.entity.BatchDeployData;

public class DBUtil {
	
	public Connection conn = null;  
    public PreparedStatement pst = null; 
    
    public DBUtil(BatchDeployData deployData,String sql){
    	String driver = "";
    	if("1".equals(deployData.getDbType())){
    		//数据库连接类型为Mysql
    		driver = "com.mysql.jdbc.Driver";
    	}else{
    		//否则为Oracle数据库
    		driver = "oracle.jdbc.driver.OracleDriver";
    	}
    	try {  
            Class.forName(driver);
            conn = DriverManager.getConnection(deployData.getConInfo(), deployData.getDbUserName(), deployData.getDbUserPswd());  
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    	
    }
    
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }

}
