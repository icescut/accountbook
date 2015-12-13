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
     * �������롢֧����¼��Ϊд�����ݿ���׼��
     * @param type ����ת
     * @param owner ��Ա
     * @param categoryLv1 ���
     * @param categoryLv2 �����
     * @param account �ʲ��ʻ�
     * @param date ����
     * @param amount ���
     * @param remark ��ע
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
     * ����ת�ʼ�¼��Ϊд�����ݿ���׼��
     * @param type ����ת
     * @param owner ��Ա
     * @param transferIn ת��
     * @param transferOut ת��
     * @param date ����
     * @param amount ���
     * @param remark ��ע
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
     * ��¼д�����ݿ�
     * @return
     */
    public boolean insertRecord() {
	return recordDao.insertRecord(record);
    }
    
    /**
     * ����ҳ���õ��������
     * @param page
     * @return
     */
    public String[][] queryByPage(int page) {
	int start = (page - 1) * 10;
	return recordDao.queryByLimit(start, Constants.GUI_TABLE_ROW);
    }
    
    /**
     * �õ���¼����
     * @return
     */
    public int getRecordCount() {
	return recordDao.countRecord();
    }

    private Record record = null;
    private RecordDao recordDao = null;
}
