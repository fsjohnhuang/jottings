class Main{
	static abstract class Human{}
	static class Man extends Human{}

	static void say(Human man){
		System.out.println("human");
	}
	static void say(Man man){
		System.out.println("man");
	}

	public static void main(String[] args){
		Human man = new Man();
		say(man);
		say((Man)man);
	}
}
