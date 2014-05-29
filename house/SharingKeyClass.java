package pesimistic.house;

public class SharingKeyClass {
	
	private static HouseKey key;
	
	public static void main(String args[]){
		
		key = HouseKey.getInstance();
		
		Thread p1 = new Person("John");
		Thread p2 = new Person("Steve");
		Thread p3 = new Person("Peter");
		Thread p4 = new Person("Anna");
		Thread p5 = new Person("Gaile");
		
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}

}
