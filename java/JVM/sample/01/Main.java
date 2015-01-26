public class Main{
	String msg1 = "msg1";
	private String msg2 = "msg2";
	protected String msg3;
	public String msg4;
	static String msg = "msg";

	public static void main(String[] args){
		Main main = new Main();
		main.msg3 = main.getMsg3(true);
		main.msg4 = "msg4";

		System.out.println(msg);
		System.out.println(main.msg1);
		System.out.println(main.msg3);
		System.out.println(main.msg4);
	}

	String getMsg3(boolean bool){
		return "msg3";
	}
}
