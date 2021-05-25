package com.problem.mrp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.problem.mrp.constants.MRPConstants;
import com.problem.mrp.util.MRPUtil;

public abstract class BaseDAOAbstract implements BaseDAO {

	protected Connection getConnection() {
		String driver = 
				MRPUtil.getValueFromProperties(MRPConstants.EXTERNAL_PROPERTIES_FILE_PATH, MRPConstants.DB_CONN_DRIVER_CLASS);
		String url = 
				MRPUtil.getValueFromProperties(MRPConstants.EXTERNAL_PROPERTIES_FILE_PATH, MRPConstants.DB_CONN_URL);
		String userName = 
				MRPUtil.getValueFromProperties(MRPConstants.EXTERNAL_PROPERTIES_FILE_PATH, MRPConstants.DB_CONN_USER_NAME);
		String userSecret = 
				MRPUtil.getValueFromProperties(MRPConstants.EXTERNAL_PROPERTIES_FILE_PATH, MRPConstants.DB_CONN_USER_SECRET);
		
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, userSecret);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	protected void releaseResource(ResultSet rs, Statement st, Connection con) {
		if(rs!=null) {try {rs.close();} catch(Exception e) {}}
		if(st!=null) {try {st.close();} catch(Exception e) {}}
		if(con!=null) {try{con.close();} catch(Exception e) {}}
	}
}
