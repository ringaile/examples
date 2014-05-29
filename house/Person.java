package pesimistic.house;

import java.util.Random;

public class Person extends Thread {
	
    private static final int MAX_WAITING_TIME = 1000;
    private static final int MAX_HAVING_TIME = 800;
    private final Random randomize = new Random();
	private HouseKey key = HouseKey.getInstance();
    private String name;
    
    public enum State {
        HAVING, WAITING, RETURNED
    }
    
    public Person(String name) {
        System.out.println(name +" Started");
        this.name = name;
    }
    
    private void takeKey() throws InterruptedException{
    	synchronized(key){
    		if(!key.getKey()){
    			key.setKey(true);
    			key.setOwnerName(this.name);
    			this.setPersonState(Person.State.HAVING);
    		}else{
				Thread.sleep(1000);
    			this.setPersonState(Person.State.WAITING);
    		}
    	}
    }
    
    private void returnKey() throws InterruptedException{
    	synchronized(key){
    		if(key.getKey() && (key.getOwnerName().equals(this.name))){    			
    			key.setKey(false);
    			key.setOwnerName(null);
    			this.setPersonState(Person.State.RETURNED);
    			key.notifyAll();
    		}
    	}
    }  

	private void setPersonState(State state){
        System.out.println(System.currentTimeMillis() +":"+ name + " is " + state);
    }

    
    public void run(){
    	for(int i=0; i < 10; i++){
				try {
					takeKey();		
					Thread.sleep(randomize.nextInt(MAX_HAVING_TIME));
					returnKey();
					Thread.sleep(randomize.nextInt(MAX_WAITING_TIME));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
    }
}
