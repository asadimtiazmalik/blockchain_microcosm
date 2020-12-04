package blockchain;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
	private static final String publicKey = "C:\\Users\\Asad Imtiaz\\Desktop\\obj\\KeyPair\\publicKey";
	private static final String privateKey = "C:\\Users\\Asad Imtiaz\\Desktop\\obj\\KeyPair\\privateKey";
	private static final String SignedData = "C:\\Users\\Asad Imtiaz\\Desktop\\obj\\MyData\\SignedData.txt";
	
	public static void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
	    while(true) {
	        // Acquire locks
	        
	        boolean gotFirstLock = false;
	        boolean gotSecondLock = false;
	       
	        
	        try {
	            gotFirstLock = firstLock.tryLock();
	            gotSecondLock = secondLock.tryLock();
	           
	        }
	        finally {
	            if(gotFirstLock && gotSecondLock) {
	                return;
	            }
	            
	            if(gotFirstLock) {
	                firstLock.unlock();
	            }
	            
	            if(gotSecondLock) {
	                secondLock.unlock();
	            }
	            
	         
	        }
	        
	        // Locks not acquired
	        Thread.sleep(1);
	    }
	}

	public static void main(String[] args) throws Exception {
		
		//Locks for thread synchronisation
		Lock lock1 = new ReentrantLock();
		Lock lock2 = new ReentrantLock();		
		 
		//Block Chain Class
		BlockChainBuilder blockchain =  new BlockChainBuilder();
		
		//Database Object
		ClientDAO dao = new ClientDAO();
		
		//Transaction Object
		Transaction trans = new Transaction(dao, blockchain);
		
		
		//Public Private key pairs
		GenerateKeys gk;
		
		//Generate Key Pairs Public-Private
		try {
			gk = new GenerateKeys(1024);
			gk.createKeys();
			gk.writeToFile(publicKey, gk.getPublicKey().getEncoded());
			gk.writeToFile(privateKey, gk.getPrivateKey().getEncoded());
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		
        Thread t1 = new Thread(new Runnable() {
            public void run() {
            	
            	
            	
                try {
                		//Acquiring Locks
                		acquireLocks(lock1,lock2);
                		
                		//Thread Naming
                		Thread.currentThread().setName("miner1");
                		
                		//Block creation by miners
                		Block block = new Block();
                		block = blockchain.addnewBlock();
                		blockchain.addBlockToChain(trans.miner1,block);
                		block.setCreatedBy(Thread.currentThread().getName());
                		
                		//Transactions
                		trans.transferVC_miners(trans.miner6, trans.miner1, 30);          		
                		trans.transferFunds(trans.miner1, trans.alice, 30);
                		trans.transferFunds(trans.miner3, trans.carshop, 50);
                		trans.transferFunds(trans.miner4, trans.fastfood, 15);
                		trans.transferVC_clients(trans.carshop, trans.worker1, 10);
                		
                		//Verifying Messages
                		dao.verifyingData(blockchain);
                		
                		//Writing to the file
                		new Message(dao.data, privateKey).writeToFile(SignedData);
                		//Verifying data from the file
                		new VerifyMessage(SignedData, publicKey);
                		
                		//Adding messages to the block
                		blockchain.updateList();
                		
                		
                		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally {
                	lock1.unlock();
                	lock2.unlock();
                	
                }
            }
        });
        
        
       
        Thread t2 = new Thread(new Runnable() {
            public void run() {
            	
            
                try {
                	  
                		//Acquiring Locks
                		acquireLocks(lock1,lock2);
                
                		//Thread Naming      
                		Thread.currentThread().setName("miner2");
                		
                		//Block creation by Miner
                		Block block = new Block();
                		block = blockchain.addnewBlock();
                		blockchain.addBlockToChain(trans.miner2,block);
                		block.setCreatedBy(Thread.currentThread().getName());
                		
                		//Transactions
                		trans.transferVC_miners(trans.miner2, trans.miner1, 25); 
                		trans.transferVC_miners(trans.miner4, trans.miner2, 25); 
                		trans.transferVC_clients(trans.carshop, trans.worker2, 5);
                		
                		//Verifying Messages
                		dao.verifyingData(blockchain);
                		
                		//Writing to the file
                		new Message(dao.data, privateKey).writeToFile(SignedData);
                		//Verifying data from the file
                		new VerifyMessage(SignedData, publicKey);
                		
                		//Adding messages to the block
                		blockchain.updateList();
                		
                		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally {
                	lock1.unlock();
                	lock2.unlock();
                	
                }
            }
        });
      
       Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                  
                		//Acquiring Locks
                		acquireLocks(lock1,lock2);
                  		
                		//Thread Naming
                  		Thread.currentThread().setName("miner3");
                  		
                  		
                  		//Block Creation by Miner
                  		Block block = new Block();
                		block = blockchain.addnewBlock();
                		blockchain.addBlockToChain(trans.miner3,block);
                		block.setCreatedBy(Thread.currentThread().getName());
                  		                  		
                  		//Transactions
                		trans.transferVC_miners(trans.miner2, trans.miner3, 50);
                		trans.transferVC_miners(trans.miner6, trans.miner7, 11); 
                		trans.transferVC_clients(trans.carshop, trans.worker3, 10);
                		
                		//Verifying  Messages
                  		dao.verifyingData(blockchain);
                		
                  		//Writing to the file
                		new Message(dao.data, privateKey).writeToFile(SignedData);
                		//Verifying data from the file
                		new VerifyMessage(SignedData, publicKey);
                		
                		//Adding messages to the block
                  		blockchain.updateList();
                  		
                	
                  	
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally {
                	lock1.unlock();
                	lock2.unlock();
                	
                }
            }
        });
       
       
       Thread t4 = new Thread(new Runnable() {
            public void run() {
                try {
                  		//Acquiring Locks
                		acquireLocks(lock1,lock2);
                		
                		//Thread Naming
                  		Thread.currentThread().setName("miner4");
                  		
                  		//Block Creation by Miner
                  		Block block = new Block();
                		block = blockchain.addnewBlock();
                		blockchain.addBlockToChain(trans.miner4, block);
                		block.setCreatedBy(Thread.currentThread().getName());
                  		
                  		//Transactions
                		
                		trans.transferFunds(trans.miner6, trans.nick, 30);
                 		trans.transferVC_clients(trans.nick, trans.carshop, 15);
                		trans.transferFunds(trans.miner2, trans.fastfood, 15);
                		trans.transferVC_clients(trans.shoeshop, trans.fastfood, 5);
                		
                  		//Verifying Messages and Creating Signatures
                  		dao.verifyingData(blockchain);
                  		
                		//Writing to the file
                		new Message(dao.data, privateKey).writeToFile(SignedData);
                		
                		//Verifying data from the file
                		new VerifyMessage(SignedData, publicKey);
                		
                		//Adding Messages to the new block
                  		blockchain.updateList();
                  		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            }
        });
       Thread t5 = new Thread(new Runnable() {
            public void run() {
                try {
                   	//Acquiring Locks
                	acquireLocks(lock1,lock2);
                	
                	//Thread Naming
                	Thread.currentThread().setName("miner5");
            		       		      		
            
            		//Block creation by Miner
            		Block block = new Block();
            		block = blockchain.addnewBlock();
            		blockchain.addBlockToChain(trans.miner5,block);
            		block.setCreatedBy(Thread.currentThread().getName());
            		
            		//Transactions
            		
            		trans.transferVC_miners(trans.miner3, trans.miner5, 3);  
              		trans.transferFunds(trans.miner5, trans.alice, 45);
            		trans.transferVC_clients(trans.nick, trans.alice, 2); 
               
            		
            		//Verifying Messages
            		dao.verifyingData(blockchain);
            		
            		//Writing to the file
            		new Message(dao.data, privateKey).writeToFile(SignedData);
            		
            		//Verifying data from the file
            		new VerifyMessage(SignedData, publicKey);
            		
            		//Adding Messages to the new block
            		blockchain.updateList();
            		
                	
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            }
        });
        
        Thread t6 = new Thread(new Runnable() {
            public void run() {
                try {
                  	//Acquiring Locks
                	acquireLocks(lock1,lock2);
            		
            		
            		//Thread Naming
            		Thread.currentThread().setName("miner6");
            		
            		//Block creation by Miner
            		Block block = new Block();
            		block = blockchain.addnewBlock();
            		blockchain.addBlockToChain(trans.miner6,block);
            		block.setCreatedBy(Thread.currentThread().getName());
            		
            		//Transactions
            		trans.transferVC_miners(trans.miner6, trans.miner2, 20); 
            		trans.transferFunds(trans.miner7, trans.carshop, 20);
            		trans.transferVC_clients(trans.nick, trans.bob, 5); 
                	trans.transferFunds(trans.miner9, trans.bob, 8);
                	trans.transferFunds(trans.miner4, trans.fastfood, 15);
            		
            		//Verifying Messages
            		dao.verifyingData(blockchain);
            		
            		//Writing to the file
            		new Message(dao.data, privateKey).writeToFile(SignedData);
            		
            		//Verifying data from the file
            		new VerifyMessage(SignedData, publicKey);
            		
            		//Adding Messages to the new block
            		blockchain.updateList();
            		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            }
        });
        Thread t7 = new Thread(new Runnable() {
            public void run() {
              
            		
            		
            	try {	
            		 acquireLocks(lock1,lock2);
            		Thread.currentThread().setName("miner7");
            		
            		
            		//Block creation by Miner
            		Block block = new Block();
            		block = blockchain.addnewBlock();
            		blockchain.addBlockToChain(trans.miner7,block);
            		block.setCreatedBy(Thread.currentThread().getName());
            		
            		//Transactions
            		trans.transferVC_miners(trans.miner4, trans.miner8, 25); 
            		trans.transferFunds(trans.miner1, trans.nick, 10);
            		trans.transferVC_clients(trans.nick, trans.bob, 25); 
                	trans.transferFunds(trans.miner8, trans.bob, 8);
                	
            		
            		
            		//Verifying Messages
            		dao.verifyingData(blockchain);
            		
            		//Writing to the file
            		new Message(dao.data, privateKey).writeToFile(SignedData);
            		
            		//Verifying data from the file
            		new VerifyMessage(SignedData, publicKey);
            		
            		//Adding Messages to the new block
            		blockchain.updateList();
            		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            	
            }
            
        });
        Thread t8 = new Thread(new Runnable() {
            public void run() {
            	try {
            		//Acquiring Locks
            		acquireLocks(lock1,lock2);
            		
            		//Thread Naming
            		Thread.currentThread().setName("miner8");
            		
            		//Block creation by Miner
            		Block block = new Block();
            		block = blockchain.addnewBlock();
            		blockchain.addBlockToChain(trans.miner8,block);
            		block.setCreatedBy(Thread.currentThread().getName());
            		
            		//Transactions
            		trans.transferVC_miners(trans.miner8, trans.miner1, 10); 
            		trans.transferFunds(trans.miner7, trans.alice, 20);
            		trans.transferVC_clients(trans.alice, trans.nick, 25); 
                	trans.transferFunds(trans.miner3, trans.bob, 8);
                	trans.transferFunds(trans.miner1, trans.fastfood, 15);
            		
            		//Verifying Messages
            		dao.verifyingData(blockchain);
            		
            		//Writing to the file
            		new Message(dao.data, privateKey).writeToFile(SignedData);
            		
            		//Verifying data from the file
            		new VerifyMessage(SignedData, publicKey);
            		
            		//Adding Messages to the block
            		blockchain.updateList();
            		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            }
        });
        /*
        Thread t9 = new Thread(new Runnable() {
            public void run() {
                try {
                
                	//Acquiring Locks
            		acquireLocks(lock1,lock2);
            		
            		
            		//Thread Naming 
            		Thread.currentThread().setName("miner9");
            		
            		//Block creation by Miner 
            		Block block = new Block();
            		block = blockchain.addnewBlock();
            		blockchain.addBlockToChain(trans.miner9,block);
            		block.setCreatedBy(Thread.currentThread().getName());
            		
            		//Transactions
            		trans.transferVC_miners(trans.miner1, trans.miner9, 10); 
            		trans.transferFunds(trans.miner3, trans.alice, 20);
            		trans.transferVC_miners(trans.miner4, trans.miner9, 25); 
                	trans.transferFunds(trans.miner9, trans.bob, 8);
                	
            		
            		//Verifying Messages
            		dao.verifyingData(blockchain);
            		
            		//Writing to the file
            		new Message(dao.data, privateKey).writeToFile(SignedData);
            		//Verifying data from the file
            		new VerifyMessage(SignedData, publicKey);
            		
            		//Adding messages to the blockchain
            		blockchain.updateList();
            		
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                	lock1.unlock();
                	lock2.unlock();
                }
            }
        });*/

       
        t1.start();
       	t2.start();
       	t3.start();
      	t4.start();
      	t5.start();
      	t6.start();
        t7.start();
        t8.start();
        /*
        t9.start();
*/
       
        t1.join();
       	t2.join();
       	t3.join();
      	t4.join();
     	t5.join();
        t6.join();
        t7.join();
        t8.join();
        /*
        t9.join();
       	*/
       	
     
       //print the Block Chain 
       System.out.println(blockchain);
       
       //Writing the block chain object to the file, this overwrite the existing file
       blockchain.WriteObjectToFile(blockchain);
       
       
       //Uncomment this to check accounts
       /*
       //print all accounts data
       trans.printAllAccounts(blockchain);
       */
      
      

       
    }
}
		
		
		


		
	



