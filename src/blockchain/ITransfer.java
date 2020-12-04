package blockchain;

public interface ITransfer {
	
	//For transferring virtual coins from miners to clients
	public void transferFunds(Object object1, Object object2, int amount);
	
	
	//for transferring virtual coins from miners to miners
	public void transferVC_miners(Miner min1, Miner min2, int amount);
	
	//for transferring virtual coins from clients to other clients
	public void transferVC_clients(Client client1, Client client2, int amount);
	
}
