package blockchain;

public class Miner {
	
	private String name;
	private int balance = 100;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccount() {
		return balance;
	}
	public void setAccount(int account) {
		this.balance = account;
	}
	
	 public void deposit(int amount) {
	        balance += amount;
	}

	 public void withdraw(int amount) {
		 if(amount<=balance) {
			balance -= amount;
		 }
	        
	}

	
	
	
	}






