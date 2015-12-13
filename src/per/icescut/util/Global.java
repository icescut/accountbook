package per.icescut.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.List;

import javax.swing.JFrame;

import per.icescut.action.AccountAction;
import per.icescut.action.RecordAction;
import per.icescut.dao.mysql.DbUtilImpl;
import per.icescut.entry.Account;

public class Global {

    public static AccountAction accountAction = null;
    public static RecordAction recordAction = null;
    
    public static List<Account> accountList = null;
    public static int recordCount;
    
    /**
     * 执行全局初始化
     * @return
     */
    public static boolean setup() {
	conn = new DbUtilImpl().getConnection();
	accountAction = new AccountAction();
	recordAction = new RecordAction();
	accountList = accountAction.getAllAccounts();
	recordCount = recordAction.getRecordCount();
	return true;
    }
    
    /**
     * 得到所有帐户的名字
     * @return
     */
    public static String[] getAccountNames() {
	String[] names = new String[accountList.size()];
	int i = 0;
	for (Account account : accountList) {
	    names[i++] = account.getName();
	}
	return names;
    }
    
    public static Connection getConnection() {
	return conn;
    }
    
    public static void setCenterFrame(JFrame frame) {
	int windowWidth = frame.getWidth(); // 获得窗口宽   
	int windowHeight = frame.getHeight(); // 获得窗口高   
	Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包   
	Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸   
	int screenWidth = screenSize.width; // 获取屏幕的宽   
	int screenHeight = screenSize.height; // 获取屏幕的高   
	frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
    }
    
    private static Connection conn = null;
}
