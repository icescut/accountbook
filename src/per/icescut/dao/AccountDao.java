package per.icescut.dao;

import java.util.List;

import per.icescut.entry.Account;

public interface AccountDao {
    List<Account> getAllAccounts();
    int QueryIdByName(String name);
    String QueryNameById(int id);
    Account getAccountByName(String name);
    boolean updateAccount(Account account);
}
