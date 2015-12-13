package per.icescut.entry;

import java.math.BigDecimal;

public class Account {
    public Account(){}
    
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    private int id;
    private String name;
    private BigDecimal amount;
}
