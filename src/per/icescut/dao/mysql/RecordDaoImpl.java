package per.icescut.dao.mysql;

import static per.icescut.dao.mysql.MysqlConstants.SQL_QUERY_RECORD;
import static per.icescut.util.Constants.GUI_TABLE_COLUMN;
import static per.icescut.util.Constants.GUI_TABLE_ROW;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import per.icescut.dao.RecordDao;
import per.icescut.entry.Record;
import per.icescut.entry.AType;
import per.icescut.util.Global;

import static per.icescut.dao.mysql.MysqlConstants.*;

public class RecordDaoImpl implements RecordDao {

    /**
     * 插入一条记录
     */
    @Override
    public boolean insertRecord(Record record) {
	PreparedStatement stmt = null;
	int type = record.getType();
	int owner = record.getOwner();
	int categoryLv1 = record.getCategoryLv1();
	int categoryLv2 = record.getCategoryLv2();
	java.sql.Date date = new java.sql.Date(record.getDate().getTime());
	BigDecimal amount = record.getAmount();
	int transferOut = record.getTransferOut();
	int transferIn = record.getTransferIn();
	int account = record.getAccount();
	String remark = record.getRemark();
	int i = 1;

	try {
	    stmt = conn.prepareStatement(SQL_INSERT_RECORD);
	    stmt.setInt(i++, type);
	    stmt.setInt(i++, owner);
	    if (categoryLv1 == NULL) {
		stmt.setNull(i++, Types.TINYINT);
	    } else {
		stmt.setInt(i++, categoryLv1);
	    }
	    if (categoryLv2 == NULL) {
		stmt.setNull(i++, Types.TINYINT);
	    } else {
		stmt.setInt(i++, categoryLv2);
	    }
	    stmt.setDate(i++, date);
	    stmt.setBigDecimal(i++, amount);
	    if (account == NULL) {
		stmt.setNull(i++, Types.TINYINT);
	    } else {
		stmt.setInt(i++, account);
	    }
	    if (transferOut == NULL) {
		stmt.setNull(i++, Types.TINYINT);
	    } else {
		stmt.setInt(i++, transferOut);
	    }
	    if (transferIn == NULL) {
		stmt.setNull(i++, Types.TINYINT);
	    } else {
		stmt.setInt(i++, transferIn);
	    }
	    stmt.setString(10, remark);
	    if (stmt.executeUpdate() < 1)
		return false;
	} catch (SQLException e) {
	    // TODO handle error and log
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    /**
     * 根据开始位置及个数查询记录
     * 
     * @param start
     *            开始位置
     * @param len
     *            个数
     * @return
     */
    @Override
    public String[][] queryByLimit(int start, int len) {
	PreparedStatement stmt = null;
	ResultSet rs = null;
	AType t = null;
	Record record = null;
	String[][] data = new String[GUI_TABLE_ROW][GUI_TABLE_COLUMN];
	StringBuilder desc;
	int row = 0;
	int col = 0;
	try {
	    stmt = conn.prepareStatement(SQL_QUERY_RECORD);
	    stmt.setInt(1, start);
	    stmt.setInt(2, len);
	    rs = stmt.executeQuery();

	    while (rs.next()) {
		desc = new StringBuilder();
		col = 0;
		record = new Record(rs.getInt(TABLE_AB_RECORD_TYPE), rs.getInt(TABLE_AB_RECORD_OWNER),
			rs.getInt(TABLE_AB_RECORD_CATEGORYLV1), rs.getInt(TABLE_AB_RECORD_CATEGORYLV2),
			rs.getInt(TABLE_AB_RECORD_TRANSFEROUT), rs.getInt(TABLE_AB_RECORD_TRANSFERIN),
			rs.getInt(TABLE_AB_RECORD_ACCOUNT), rs.getDate(TABLE_AB_RECORD_DATE),
			rs.getBigDecimal(TABLE_AB_RECORD_AMOUNT), rs.getString(TABLE_AB_RECORD_REMARK));
		
		Record.RecordHelper helper = record.new RecordHelper();
		data[row][col++] = helper.getDate();
		t = helper.getType();
		
		desc.append(t.getName());
		desc.append(": ");
		switch(t) {
		case Pay:
		case Income:
		    desc.append(helper.getCategoryLv1());
		    desc.append(" > ");
		    desc.append(helper.getCategoryLv2());
		    break;
		case Transfer:
		    desc.append(helper.getTransferOut());
		    desc.append(" --> ");
		    desc.append(helper.getTransferIn());
		    break;
		default:
		    // TODO handle error and log
		    break;
		}
		data[row][col++] = desc.toString();
		data[row][col++] = record.getAmount().toString();
		row++;
	    }
	} catch (SQLException e) {
	    // TODO handle error and log
	    e.printStackTrace();
	}
	return data;
    }

    /**
     * 得到记录总数
     */
    @Override
    public int countRecord() {
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(SQL_QUERY_RECORD_COUNT);
	    while(rs.next()) {
		return rs.getInt(1);
	    }
	} catch (SQLException e) {
	    // TODO 1
	    e.printStackTrace();
	}
	return 0;
    }

    private Connection conn = Global.getConnection();
    private static final int NULL = -1;
}
