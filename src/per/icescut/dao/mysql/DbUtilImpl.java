package per.icescut.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import per.icescut.dao.DbUtil;
import per.icescut.util.Constants;

import static per.icescut.util.Constants.*;

public class DbUtilImpl implements DbUtil {

    static {
	
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	} catch (SQLException | ClassNotFoundException e) {
	    // TODO handle error and log
	    e.printStackTrace();
	}
    }
    
    @Override
    public Connection getConnection() {
	return conn;
    }

    private static Connection conn;
}
