package per.icescut.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import per.icescut.dao.AccountDao;
import per.icescut.dao.DbUtil;
import per.icescut.entry.Account;
import per.icescut.util.Global;

import static per.icescut.dao.mysql.MysqlConstants.*;

public class AccountDaoImpl implements AccountDao {
    
    public AccountDaoImpl() {
	 conn = Global.getConnection();
    }

    /**
     * 得到资产帐号的列表
     */
    @Override
    public List<Account> getAllAccounts() {
	List<Account> list = new ArrayList<>();
	Statement stmt = null;
	ResultSet rs = null;
	Account account = null;
	try {
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(SQL_QUERY_ACCOUNT);
	    while(rs.next()) {
		account = new Account();
		account.setId(rs.getInt(TABLE_AB_ACCOUNT_ID));
		account.setName(rs.getString(TABLE_AB_ACCOUNT_NAME));
		account.setAmount(rs.getBigDecimal(TABLE_AB_ACCOUNT_AMOUNT));
		list.add(account);
	    }
	} catch (SQLException e) {
	    // TODO handle error and log
	    e.printStackTrace();
	}
	return list;
    }

    /**
     * 从全局对象中根据名字查询资产帐号的ID
     */
    @Override
    public int QueryIdByName(String name) {
	for (Account account : Global.accountList) {
	    if(account.getName().equals(name)) return account.getId();
	}
	return -1;
    }

    /**
     * 根据名字查询帐号
     */
    @Override
    public Account getAccountByName(String name) {
	for (Account account : Global.accountList) {
	    if(account.getName().equals(name)) {
		return account;
	    }
	}
	return null;
    }

    /**
     * 更新帐号
     */
    @Override
    public boolean updateAccount(Account account) {
	PreparedStatement stmt = null;
	int id = account.getId();
	BigDecimal amount = account.getAmount();
	
	try {
	    stmt = conn.prepareStatement(SQL_UPDATE_ACCOUNT);
	    stmt.setBigDecimal(1, amount);
	    stmt.setInt(2, id);
	    if(stmt.executeUpdate() < 1) return false;
	} catch (SQLException e) {
	    // TODO handle error and log
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    
    private Connection conn = null;

    /**
     * 根据名字返回Id
     */
    @Override
    public String QueryNameById(int id) {
	for (Account account : Global.accountList) {
	    if(account.getId() == id) {
		return account.getName();
	    }
	}
	return null;
    }

}
