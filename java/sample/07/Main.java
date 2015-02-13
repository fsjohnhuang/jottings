class Main{
	public static void main(String[] args){
		Fruit<? extends P> fruit = null;
	}
	static class P{}
	static class S extends P{}
	static class Fruit<T extends P>{}

}
