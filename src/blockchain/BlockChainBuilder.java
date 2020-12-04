package blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.*;

public class BlockChainBuilder implements Serializable {
	
	//list of blocks
	public  ArrayList<Block> blocks = new ArrayList<Block>();
	
	private List<Block> savedBlocks = new ArrayList<Block>();
	
	public  ArrayList<String> messages =  new ArrayList<String>();
	
	public  ArrayList<Miner> transactions = new ArrayList<Miner>();
	
	public ArrayList<Client> clientAccounts = new ArrayList<Client>();
	
	private static final long serialVersionUID = 1L;
	
	public volatile String prev_block = "0";
	
	//number of zeros to be added 
	public static int numofZeros = 0 ;
	
	//The ID of the block
	public volatile static int id_call = 0;
	
	//lottery or magic number counter
	private int lotNumber = 0;
	
	//measuring time
	private Duration duration;
	
	//file path
	private static final String filepath = "C:\\Users\\Asad Imtiaz\\Desktop\\obj\\data";
	
	//validation merit
	private boolean is_valid = true;

	//miner number
	public String miner;
	
	//Global message variable
	public String message = "\nNo transactions\n";
	
	//unique identifier
	public int uniqueID = 1;
	
	//adds message to the list, that were send to the blockchain during the
	//formation of the previous block
	public void updateList() {
		
		for(String msg: messages) {
				message += msg ; 
				
			}
		}
		
		
	//add new block
	public Block addnewBlock() {
		
		Instant first = Instant.now();
		
		Block block = new Block();
		
		id_call+=1;
		
		block.setId(id_call);	
		
		block.setTimestamp(new Date().getTime());
		
		block.setHash_of_the_previous_block(prev_block);
		
		block.setHash_of_the_block( proofofWork(prev_block) );
		
		block.setMagicNumber(String.valueOf(lotNumber));
		
		block.setBlockData(message);
		
		Instant second = Instant.now();
		
		duration = Duration.between(first, second);
		
		block.setTimeElapsed(duration.getSeconds());
		
		prev_block = block.getHash_of_the_block();
	
		messages.clear();
		
		message = "\n";
		
		return block;
	}
	
	//Regulate the number of zeros "N"
	private void regulator(Block block) {
		if ((block.getTimeElapsed())<=10) {
			numofZeros +=1;
			block.setNumOfzeros(numofZeros);
			
		

		}
		else if(((block.getTimeElapsed())>10)&&((block.getTimeElapsed()) < 60)) {
			
			block.setNumOfzeros(numofZeros);
			
		
			
	}
		else if((block.getTimeElapsed()) >= 60) {
			numofZeros = numofZeros - 1;
			block.setNumOfzeros(numofZeros);
			
			
		}
	}
	
	//adding the blocks created by miners to the block chain
	public void addBlockToChain(Miner miner, Block block){
		int id = block.getId();
		String prev_hash = block.getHash_of_the_previous_block();
		
		if(block.getHash_of_the_block().equals(prev_block)) {
			
			if(validateBlock(block)) {
				miner.deposit(100);
				block.setInit_reward(100);
				blocks.add(block);
				
			}
			else {
				Block newBlock;
				newBlock = addnewBlock();
				newBlock.setCreatedBy(miner.getName());
				newBlock.setId(id);
				newBlock.setHash_of_the_previous_block(prev_hash);
				addBlockToChain(miner, newBlock);
			
			}
		
		}
		
		else is_valid = false;	
	}
	
	//validate the block
	private boolean validateBlock(Block block) {
	
		boolean is_equal = false;
		String str = block.getHash_of_the_block();
			
				if(str.startsWith("0".repeat(numofZeros)) && ! str.startsWith("0".repeat(numofZeros+1))) {
					regulator(block);
					is_equal = true;
					}
				

			
			
		return is_equal;
	}
		
	//generate unique identifier 
	public void getUniqueIdentifier(Client client) {
			int temp;
			
			client.setMsgId(uniqueID);
			uniqueID +=1;
			temp = client.getMsgId();
			if(temp>uniqueID||temp==uniqueID) {
				client.setMessage("Message Rejected");
			}
			
		}
	
	//accounts of all miners
	public void checkAccounts() {

		System.out.println("\n");
		System.out.println("ACCOUNT DATA MINERS");
		System.out.println("\n");
		for(Miner miner: transactions) {
			System.out.println(miner.getName() + " Account Info: " + miner.getAccount() + " VC");
		}
		
		System.out.println("\n");
		System.out.println("ACCOUNT DATA CLIENTS");
		System.out.println("\n");
		for(Client client: clientAccounts) {
			System.out.println(client.getName() + " Account Info: " + client.getAcc_balance() + " VC");
		}
	}
	
	
	//validation operation for uniqueness of each block
	private boolean validateBlockChain(List<Block> blocks_validation) {
		
		int count = 0;
		boolean is_not_unique = false ;
		
		//For checking if each  block has a unique "Hash of the Block".
		for(Block block : blocks) {
			
			//This is done using a nested for loop, the value of count increases
			//each time a similar entry appears. For 10 elements in a list the value
			//count goes to 10 or would be equal to the number of items in the list.
			//If count increases this value means the list of blocks is not unique.
			
			for (Block block_validate :  blocks_validation) {
				if(block.getHash_of_the_block()==block_validate.getHash_of_the_block()) {
					count++;
					
				}
				
				if(count > blocks_validation.size()) {
					is_not_unique = true;
					break;
				}
			}
			if(is_not_unique) {
				is_valid = false;
				break;
			}
			
		}
		return is_valid;
	}
	
	
	
	//For proof of work
	public String proofofWork(String hashOfprevBlock) {
		Random random = new Random();
		
		StringBuilder difficulty = new StringBuilder();
		String proof_of_work;
		
		for(int i=0; i < numofZeros; i++) {
			difficulty.append("0");
		}
		
		
		while(true) {
			
			String hash = StringUtil.applySha256(hashOfprevBlock+String.valueOf(lotNumber)+String.valueOf(id_call) + message);
			lotNumber = random.nextInt(Integer.MAX_VALUE);
			hashOfprevBlock = hash;
			
			//difficulty is a string builder object which needs to be converted to a String 
			//hence we use String.valueOf() method.
			if((hashOfprevBlock.substring(0, numofZeros)).equals(String.valueOf(difficulty))) {
				proof_of_work = hashOfprevBlock;
				return proof_of_work;
			}
			
		}
		
	}
	
	
	
	
	//Writing block chain to the file
	public void WriteObjectToFile(Object serObj) {
		 
        try {
 
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            
            System.out.println("The Object was succesfully written to a file");
 
        } catch (Exception e) {
        	System.out.println("The Object was not written to a file");
            
        }
	}
	
	
	//Reading the block chain from the memory
	public void ReadObjectFromFile() {
		
		BlockChainBuilder block_chain;
		 try
	        {
	            FileInputStream fis = new FileInputStream(filepath);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            block_chain = (BlockChainBuilder)ois.readObject();
	            System.out.println("File read successfully.");
	            ois.close();
	            fis.close();
	            
	            
	            savedBlocks = block_chain.blocks;
	            System.out.println(block_chain);
	           

	        } 
		 
		 
		 //implements the file not found exception in case no such file exists in memory
	        catch (IOException ioe) 
	        {
	        	System.out.println("No such file exists in memory");
	            return;
	        } 
	        catch (ClassNotFoundException c) 
	        {
	            System.out.println("Class not found");
	            return;
	        }
		 
	        //Verify list data
	        for (Block aBlock : block_chain.blocks) {
	            System.out.println(aBlock);
	        }
	    }
		
	
	//To print the chain of blocks to the screen
	@Override
	public String toString() {
		
	    StringBuilder blockStringBuilder = new StringBuilder();
	   
	    if(is_valid) {
	    	
			    for(Block block : blocks) {
			    	blockStringBuilder.append("Block: ");
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Created by: " + block.getCreatedBy());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append(block.getCreatedBy()+ " gets " + block.getInit_reward() + " VC");
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Id: " + block.getId());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Timestamp: " + block.getTimestamp());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Magic number: " + block.getMagicNumber());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Hash of the previous block: " +"\n"+ block.getHash_of_the_previous_block());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Hash of the block: "+ "\n" + block.getHash_of_the_block());
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("Block data: " + block.getBlockData());
			    	blockStringBuilder.append("Block was generating for "+ block.getTimeElapsed()+" seconds");
			    	blockStringBuilder.append("\n");
			    	if ((block.getTimeElapsed())<=10){
			    		blockStringBuilder.append("N was increased to "+ block.getNumOfzeros());
			     	}
			    	else if(((block.getTimeElapsed())>10)&&((block.getTimeElapsed()) < 60)) {
			    		blockStringBuilder.append("N stays the same ");
			    	}
			    	
			    	else if((block.getTimeElapsed()) >= 60){
			    		
			    		blockStringBuilder.append("N was decreased by 1 ");
			    		
			    	}
			    	blockStringBuilder.append("\n");
			    	blockStringBuilder.append("\n");
			    	
			    }
			    
			   validateBlockChain(blocks);
			    
	    }   
		    
		    else blockStringBuilder.append("The blockchain is invalid. Hash codes aren't unique");
	    	
	    		
	 
	    return blockStringBuilder.toString();
}
	
	
	
	
	

}

