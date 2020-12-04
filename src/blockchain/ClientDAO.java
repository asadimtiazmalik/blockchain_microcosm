package blockchain;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
		Client client;	
		private String client_msg;
		public String data ;
		
		
	public void produceMessages(String name, String message, int balance, boolean sufficient){
			client = new Client();
			client.setName(name);
			client.setMessage(message);
			client.setTransaction(balance);
			client.is_sufficient = sufficient;
			
	}
	
	public void storeMessages(BlockChainBuilder blockchain) {
				blockchain.getUniqueIdentifier(client);
				client_msg = String.valueOf(client);	
				blockchain.messages.add(client_msg + "\n");
		}
	
	public String verifyingData(BlockChainBuilder blockchain) {
		data = "";
		for(String message:blockchain.messages) {
			data += message;
		}
		
		return data;
	}

}
