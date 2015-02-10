class Main{
	public static void main(String[] args) throws ClassNotFoundException{
		//ClassLoader.getSystemClassLoader().loadClass("Test");
		Class.forName("Test", true, ClassLoader.getSystemClassLoader());
	}
}
