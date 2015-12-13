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
     * ִ��ȫ�ֳ�ʼ��
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
     * �õ������ʻ�������
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
	int windowWidth = frame.getWidth(); // ��ô��ڿ�   
	int windowHeight = frame.getHeight(); // ��ô��ڸ�   
	Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�   
	Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�   
	int screenWidth = screenSize.width; // ��ȡ��Ļ�Ŀ�   
	int screenHeight = screenSize.height; // ��ȡ��Ļ�ĸ�   
	frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// ���ô��ھ�����ʾ
    }
    
    private static Connection conn = null;
}
