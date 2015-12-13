package per.icescut.entry;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import per.icescut.action.AccountAction;
import per.icescut.util.ArrayUtil;
import per.icescut.util.Constants;
import per.icescut.util.Global;

public class Record {
    public Record() {}
    
    public Record(int type, int owner, int categoryLv1, int categoryLv2, int transferOut, int transferIn, int account,
	    Date date, BigDecimal amount, String remark) {
	super();
	this.type = type;
	this.owner = owner;
	this.categoryLv1 = categoryLv1;
	this.categoryLv2 = categoryLv2;
	this.transferOut = transferOut;
	this.transferIn = transferIn;
	this.account = account;
	this.date = date;
	this.amount = amount;
	this.remark = remark;
    }

    public int getType() {
	return type;
    }

    public void setType(int type) {
	this.type = type;
    }

    public int getOwner() {
	return owner;
    }

    public void setOwner(int owner) {
	this.owner = owner;
    }

    public int getCategoryLv1() {
	return categoryLv1;
    }

    public void setCategoryLv1(int categoryLv1) {
	this.categoryLv1 = categoryLv1;
    }

    public int getCategoryLv2() {
	return categoryLv2;
    }

    public void setCategoryLv2(int categoryLv2) {
	this.categoryLv2 = categoryLv2;
    }

    public int getTransferOut() {
	return transferOut;
    }

    public void setTransferOut(int transferOut) {
	this.transferOut = transferOut;
    }

    public int getTransferIn() {
	return transferIn;
    }

    public void setTransferIn(int transferIn) {
	this.transferIn = transferIn;
    }

    public int getAccount() {
	return account;
    }

    public void setAccount(int account) {
	this.account = account;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public String getRemark() {
	return remark;
    }

    public void setRemark(String remark) {
	this.remark = remark;
    }

    public static int getNull() {
	return NULL;
    }
    
    public class RecordHelper {
	/**
	 * �õ�������ַ���ʽ
	 * @return
	 */
	public String getCategoryLv1() {
	    if(categoryLv1 == NULL) return null;
	    if(getType().equals(AType.Pay)) {
		return Constants.CATEGORY_LV1_PAY[categoryLv1];
	    } else if(getType().equals(AType.Income)) {
		return Constants.CATEGORY_LV1_INCOME[categoryLv1];
	    }
	    return null;
	}
	
	/**
	 * �������ֵõ�����ID
	 * @param categoryLv1
	 */
	public void setCategoryLv1(String categoryLv1) {
	    if(getType().equals(AType.Pay)) {
		Record.this.categoryLv1 = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV1_PAY, categoryLv1);
	    } else if(getType().equals(AType.Income)) {
		Record.this.categoryLv1 = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV1_INCOME, categoryLv1);
	    }
	}
	
	/**
	 * �õ��ӷ�����ַ���ʽ
	 * @return
	 */
	public String getCategoryLv2() {
	    if(categoryLv1 == NULL) return null;
	    if(getType().equals(AType.Pay)) {
		return Constants.CATEGORY_LV2_PAY[categoryLv1][categoryLv2];
	    } else if(getType().equals(AType.Income)) {
		return Constants.CATEGORY_LV2_INCOME[categoryLv1][categoryLv2];
	    }
	    return null;
	}
	
	/**
	 * �������ֵõ�������ID
	 * @param categoryLv2
	 */
	public void setCategoryLv2(String categoryLv2) {
	    if(getType().equals(AType.Pay)) {
		Record.this.categoryLv2 = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV2_PAY[categoryLv1], categoryLv2);
	    } else if(getType().equals(AType.Income)) {
		Record.this.categoryLv2 = ArrayUtil.IndexOfArray(Constants.CATEGORY_LV2_INCOME[categoryLv1], categoryLv2);
	    }
	}

	/**
	 * �õ���ʽ���������
	 * @return
	 */
	public String getDate() {
	    return Constants.SDF.format(date);
	}
	
	/**
	 * �����ַ�����������
	 * @param date
	 */
	public void setDate(String date) {
	    try {
		Record.this.date = Constants.SDF.parse(date);
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	/**
	 * �õ�����
	 * @return AType
	 */
	public AType getType() {
	    if(type == NULL) return null;
	    return AType.getTypeByIndex(type);
	}
	
	/**
	 * ���������������Ͷ�Ӧ��ID
	 * @param type
	 */
	public void setType(String type) {
	    for (AType t : AType.values()) {
		if(t.getName().equals(type)) {
		     Record.this.type = t.getIndex();
		}
	    }
	}

	/**
	 * �õ�ת����Ӧ���ַ�����
	 * @return
	 */
	public String getTransferOut() {
	    if(transferOut == NULL) return null;
	    return accountAction.getNameById(transferOut);
	}
	
	/**
	 * ������������ת���ʻ���id
	 * @param transferOut
	 */
	public void setTransferOut(String transferOut) {
	    for (Account account : Global.accountList) {
		if(account.getName().equals(transferOut)) {
		    Record.this.transferOut = account.getId();
		}
	    }
	}
	
	/**
	 *  �õ�ת���Ӧ���ַ�����
	 * @return
	 */
	public String getTransferIn() {
	    if(transferIn == NULL) return null;
	    return accountAction.getNameById(transferIn);
	}
	
	/**
	 * ������������ת���ʻ���id
	 * @param transferIn
	 */
	public void setTransferIn(String transferIn) {
	    for (Account account : Global.accountList) {
		if(account.getName().equals(transferIn)) {
		    Record.this.transferIn = account.getId();
		}
	    }
	}
	
	/**
	 * �����ʲ��ʻ�����������ID
	 * @param account
	 */
	public void setAccount(String account) {
	    for (Account a : Global.accountList) {
		if(a.getName().equals(account)) {
		    Record.this.account = a.getId();
		}
	    }
	}
	
	/**
	 * ���ݳ�Ա�������ó�Ա��ID
	 * @param owner
	 */
	public void setOwner(String owner) {
	    for(int i = 0; i< Constants.OWNERS.length; i++) {
		if(owner.equals(Constants.OWNERS[i])) {
		    Record.this.owner = i;
		}
	    }
	}
	
	/**
	 * ���ݽ����ַ������ý��
	 * @param amount
	 */
	public void setAmount(String amount) {
	    BigDecimal am = new BigDecimal(amount);
	    Record.this.amount = am;
	}

    }

    private static final int NULL = -1;
    private int type;
    private int owner;
    private int categoryLv1;
    private int categoryLv2;
    private int transferOut;
    private int transferIn;
    private int account;
    private Date date;
    private BigDecimal amount;
    private String remark;
    
    private static AccountAction accountAction = Global.accountAction; 
}
