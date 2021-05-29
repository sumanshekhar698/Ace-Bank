package com.sumu.acebank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
	private static ConnectionManager connectionManager;
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String CONNECTION_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
	private static final String USERNAME = "system";
	private static final String PASSWORD = "suman";

	private ConnectionManager() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
	}
	
    public static ConnectionManager getInstance(){
        if (connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

}
