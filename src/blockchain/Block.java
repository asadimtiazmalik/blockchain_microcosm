package blockchain;
import java.io.Serializable;
import java.security.MessageDigest;




class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
public class Block implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private long timestamp;
	private String hash_of_the_previous_block;
	private String hash_of_the_block;
	private String magicNumber;
	private long timeElapsed;
	private String created_by;
	private int numOfzeros;
	private int init_reward;
	private String blockData;
	
	
	public int getInit_reward() {
		return init_reward;
	}

	public void setInit_reward(int init_reward) {
		this.init_reward = init_reward;
	}

	public String getBlockData() {
		return blockData;
	}

	public void setBlockData(String blockData) {
		this.blockData = blockData;
	}

	public int getNumOfzeros() {
		return numOfzeros;
	}

	public void setNumOfzeros(int numOfzeros) {
		this.numOfzeros = numOfzeros;
	}

	public String getCreatedBy() {
		return created_by;
	}

	public void setCreatedBy(String creationTime) {
		this.created_by = creationTime;
	}

	//	
	public String getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(String magicNumber) {
		this.magicNumber = magicNumber;
	}


	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getHash_of_the_previous_block() {
		return hash_of_the_previous_block;
	}
	public void setHash_of_the_previous_block(String hash_of_the_previous_block) {
		this.hash_of_the_previous_block = hash_of_the_previous_block;
	}
	public String getHash_of_the_block() {
		return hash_of_the_block;
	}
	public void setHash_of_the_block(String hash_of_the_block) {
		this.hash_of_the_block = hash_of_the_block;
	}

	public long getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	

	@Override
	public String toString() {
		return "Block [id=" + id + ", hash_of_the_previous_block="
				+ hash_of_the_previous_block + ", magicNumber="
				+ magicNumber + "]";
	}

	
	
	
}
