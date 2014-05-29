package pesimistic.house;

public class HouseKey {
	
	public static HouseKey instance = null;
	
	private HouseKey(){}
	
	public static HouseKey getInstance(){
		if (instance == null) {
            instance = new HouseKey();
        }
		return instance;
	}
	
	private boolean isUsed = false;
	private String ownerName = null;
	
	protected String getOwnerName(){
		return ownerName;
	}
	
	protected void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}
	
	protected boolean getKey(){
		return isUsed;
	}

	protected void setKey(boolean isUsed){
		this.isUsed = isUsed;
	}
}
