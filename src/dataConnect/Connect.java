package dataConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
public static Connection getConnection() {
	Connection con=null;
	try {
		con=DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return con;
}
}
