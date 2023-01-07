package views;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String dbServer = "postgresql-100470-0.cloudclusters.net"; // change it to your database server name
        int dbPort = 10121; // change it to your database server port
        String dbName = "Demochat";
        String userName = "admin";
        String password = "192002Nhuy";
        String url = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", 
                                    dbServer, dbPort, dbName, userName, password);
        try {
			conn = DriverManager.getConnection(url);
			Statement stmt = null;
			stmt = conn.createStatement();
			String sql = "SELECT * from users";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.print(rs.getString(2));
			}
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Cannot connect");
		}
	}

}
