package per.icescut.action;

import per.icescut.dao.RecordDao;
import per.icescut.dao.mysql.RecordDaoImpl;
import per.icescut.entry.Record;
import per.icescut.util.Constants;

public class RecordAction {

    public RecordAction() {
	recordDao = new RecordDaoImpl();
    }

    /**
     * 设置收入、支出记录，为写入数据库作准备
     * @param type 收入转
     * @param owner 成员
     * @param categoryLv1 类别
     * @param categoryLv2 子类别
     * @param account 资产帐户
     * @param date 日期
     * @param amount 金额
     * @param remark 备注
     */
    public void setupRecord(String type, String owner, String categoryLv1, String categoryLv2, String account,
	    String date, String amount, String remark) {
	record = new Record();
	Record.RecordHelper helper = record.new RecordHelper();
	helper.setType(type);
	helper.setOwner(owner);
	helper.setCategoryLv1(categoryLv1);
	helper.setCategoryLv2(categoryLv2);
	helper.setAccount(account);
	helper.setDate(date);
	helper.setAmount(amount);
	record.setRemark(remark);
    }
    
    /**
     * 设置转帐记录，为写入数据库作准备
     * @param type 收入转
     * @param owner 成员
     * @param transferIn 转入
     * @param transferOut 转出
     * @param date 日期
     * @param amount 金额
     * @param remark 备注
     */
    public void setupRecordTransfer(String type, String owner, String transferIn, String transferOut, 
	    String date, String amount, String remark) {
	record = new Record();
	Record.RecordHelper helper = record.new RecordHelper();
	helper.setType(type);
	helper.setOwner(owner);
	helper.setTransferIn(transferIn);
	helper.setTransferOut(transferOut);
	helper.setDate(date);
	helper.setAmount(amount);
	record.setRemark(remark);
    }

    /**
     * 记录写入数据库
     * @return
     */
    public boolean insertRecord() {
	return recordDao.insertRecord(record);
    }
    
    /**
     * 根据页数得到表格内容
     * @param page
     * @return
     */
    public String[][] queryByPage(int page) {
	int start = (page - 1) * 10;
	return recordDao.queryByLimit(start, Constants.GUI_TABLE_ROW);
    }
    
    /**
     * 得到记录总数
     * @return
     */
    public int getRecordCount() {
	return recordDao.countRecord();
    }

    private Record record = null;
    private RecordDao recordDao = null;
}
