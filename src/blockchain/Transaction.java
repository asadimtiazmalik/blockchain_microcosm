package blockchain;

//Database of All transactions

public class Transaction implements ITransfer {
	
	
	private ClientDAO dao;
	private BlockChainBuilder blockchain;
	
	
	

	public Transaction(ClientDAO dao, BlockChainBuilder blockchain){
		this.dao = dao;
		this.blockchain = blockchain;
		minerNames();
		clientNames();
	}
	
	
	//List of all Clients
	Client nick  = new Client();
	Client alice = new Client();
	Client bob 	 = new Client();
	Client carshop  = new Client();
	Client shoeshop = new Client();
	Client fastfood	= new Client();
	Client worker1 = new Client();
	Client worker2 = new Client();
	Client worker3 = new Client();
	
		
	//List of All the miners
	Miner miner1 = new Miner();
	Miner miner2 = new Miner();
	Miner miner3 = new Miner();
	Miner miner4 = new Miner();
	Miner miner5 = new Miner();
	Miner miner6 = new Miner();
	Miner miner7 = new Miner();
	Miner miner8 = new Miner();
	Miner miner9 = new Miner();

	private void minerNames() {
		miner1.setName("miner1");
		miner2.setName("miner2");
		miner3.setName("miner3");
		miner4.setName("miner4");
		miner5.setName("miner5");
		miner6.setName("miner6");
		miner7.setName("miner7");
		miner8.setName("miner8");
		miner9.setName("miner9");
		
	}
	private void clientNames() {
		nick.setName("Nick");
		alice.setName("Alice");
		bob.setName("Bob");
		carshop.setName("CarShop");
		shoeshop.setName("ShoeShop");
		fastfood.setName("FastFood");
		worker1.setName("Worker1");
		worker2.setName("Worker2");
		worker3.setName("Worker3");
		
	}
	
	

	@Override
	public void transferFunds(Object object1,Object object2, int amount) {
		boolean sufficient = false;
		
		
		
		if(((Miner)object1).getAccount()>=amount) {
			sufficient = true;
			((Miner)object1).withdraw(amount);
			((Client)object2).deposit(amount);
			
		}
		
		dao.produceMessages(((Miner)object1).getName(), ((Client)object2).getName(), amount, sufficient);
		dao.storeMessages(blockchain);

		
	}

	@Override
	public void transferVC_miners(Miner min1, Miner min2, int amount) {
		boolean sufficient = false;
		
		if(min1.getAccount()>=amount) {
			sufficient = true;
			min1.withdraw(amount);
			min2.deposit(amount);
		}
		dao.produceMessages(min1.getName(), min2.getName(), amount, sufficient);
		dao.storeMessages(blockchain);
	}
	
	@Override
	public void transferVC_clients(Client client1, Client client2, int amount) {
		boolean sufficient = false;
		if(client1.getAcc_balance()>=amount) {
			sufficient = true;
			client1.withdraw(amount);
			client2.deposit(amount);
		}
		
		dao.produceMessages(client1.getName(), client2.getName(), amount, sufficient);
		dao.storeMessages(blockchain);
	}
	


	//set all miner accounts
	private void setAllMinerAccounts(BlockChainBuilder blockchain) {
		
		blockchain.transactions.add(miner1);
		blockchain.transactions.add(miner2);
		blockchain.transactions.add(miner3);
		blockchain.transactions.add(miner4);
		blockchain.transactions.add(miner5);
		blockchain.transactions.add(miner6);
		blockchain.transactions.add(miner7);
		blockchain.transactions.add(miner8);
		blockchain.transactions.add(miner9);
		
		
	}
	
	//set all client accounts
	private void setAllClientAccounts(BlockChainBuilder blockchain) {
		
		blockchain.clientAccounts.add(nick);
		blockchain.clientAccounts.add(alice);
		blockchain.clientAccounts.add(bob);
		blockchain.clientAccounts.add(carshop);
		blockchain.clientAccounts.add(shoeshop);
		blockchain.clientAccounts.add(fastfood);
		blockchain.clientAccounts.add(worker1);
		blockchain.clientAccounts.add(worker2);
		blockchain.clientAccounts.add(worker3);
		
		
	}
	
	public void printAllAccounts(BlockChainBuilder blockchain) {
		setAllMinerAccounts(blockchain);
		setAllClientAccounts(blockchain);
		blockchain.checkAccounts();
	}

	
	
	
	

}
