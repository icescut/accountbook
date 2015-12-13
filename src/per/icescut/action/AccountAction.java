package per.icescut.action;

import java.math.BigDecimal;
import java.util.List;

import per.icescut.dao.AccountDao;
import per.icescut.dao.mysql.AccountDaoImpl;
import per.icescut.entry.AType;
import per.icescut.entry.Account;

public class AccountAction {
    
    public AccountAction() {
	accountDao = new AccountDaoImpl();
    }
    
    /**
     * �������ֲ�ѯId
     * @param name
     * @return
     */
    public int getIdByName(String name) {
	return accountDao.QueryIdByName(name);
    }
    
    /**
     * ����id��ѯ����
     * @param id
     * @return
     */
    public String getNameById(int id) {
	return accountDao.QueryNameById(id);
    }
    
    /**
     * ��֧��������䶯���µ��ʻ�
     * @param type ֧��������
     * @param name �ʻ�����
     * @param movementStr �䶯
     * @return �Ƿ���³ɹ�
     */
    public boolean updateAccount(String type, String name, String movementStr) {
	Account account = accountDao.getAccountByName(name);
	BigDecimal amount = account.getAmount();
	BigDecimal movement = new BigDecimal(Double.parseDouble(movementStr));
	if(type.equals(AType.Pay.getName())) {
	    account.setAmount(amount.subtract(movement));
	} else {
	    account.setAmount(amount.add(movement));
	}
	accountDao.updateAccount(account);
	return true;
    }
    
    /**
     * ��ת�ʱ䶯���µ��ʻ�
     * @param transferIn ת��
     * @param transferOut ת��
     * @param movementStr �䶯
     * @return
     */
    public boolean updateAccountForTransfer(String transferIn, String transferOut, String movementStr) {
	Account inAccount = accountDao.getAccountByName(transferIn);
	Account outAccount = accountDao.getAccountByName(transferOut);
	BigDecimal inAmount = inAccount.getAmount();
	BigDecimal outAmount = outAccount.getAmount();
	BigDecimal movement = new BigDecimal(Double.parseDouble(movementStr));
	inAccount.setAmount(inAmount.add(movement));
	outAccount.setAmount(outAmount.subtract(movement));
	accountDao.updateAccount(inAccount);
	accountDao.updateAccount(outAccount);
	return true;
    }

    /**
     * �õ������ʻ�
     * @return
     */
    public List<Account> getAllAccounts() {
	return accountDao.getAllAccounts();
    }

    private AccountDao accountDao = null;
}
