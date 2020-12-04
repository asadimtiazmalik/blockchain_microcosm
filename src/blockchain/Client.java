package blockchain;

public class Client {
	
	private String name;
	private String message;
	private int msgId;
	private int acc_balance = 0;
	private int transaction;
	public boolean is_sufficient;
	
	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	
	
	public int getAcc_balance() {
		return acc_balance;
	}

	public void setAcc_balance(int acc_balance) {
		this.acc_balance = acc_balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	 public void deposit(int amount) {
	        acc_balance += amount;
	}

	 public void withdraw(int amount) {
		 	acc_balance -= amount; 
	}
	
	@Override
	public String toString() {
		if(is_sufficient) {
		return name + " sent " +transaction+" VC to " + message;
		}
		else return "This transaction was not processed. Insufficeint balance !!!";
	}

	
	
	
	

}
