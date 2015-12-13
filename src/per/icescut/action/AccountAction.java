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
     * 根据名字查询Id
     * @param name
     * @return
     */
    public int getIdByName(String name) {
	return accountDao.QueryIdByName(name);
    }
    
    /**
     * 根据id查询名字
     * @param id
     * @return
     */
    public String getNameById(int id) {
	return accountDao.QueryNameById(id);
    }
    
    /**
     * 把支出、收入变动更新到帐户
     * @param type 支出、收入
     * @param name 帐户名字
     * @param movementStr 变动
     * @return 是否更新成功
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
     * 把转帐变动更新到帐户
     * @param transferIn 转入
     * @param transferOut 转出
     * @param movementStr 变动
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
     * 得到所有帐户
     * @return
     */
    public List<Account> getAllAccounts() {
	return accountDao.getAllAccounts();
    }

    private AccountDao accountDao = null;
}
