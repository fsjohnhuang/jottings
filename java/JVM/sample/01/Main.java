import java.io.IOException;
public class Main{
	String msg1 = "msg1";
	private String msg2 = "msg2";
	protected String msg3;
	public String msg4;
	static String msg = "msg";
	static final String msg6 = "msg6";
	final String msg5;
	{
		msg3 = "msg3";
	}

	public Main(){
		msg5 = "mmmmm";
	}

	public static void main(String[] args){
		int a = 1;
		a += 2;
		a = a + 4;
		int c = a++;
		c = ++a;

		Main main = new Main();
		main.msg3 = main.getMsg3(true);
		main.msg4 = "msg4";
			
		System.out.println(msg);
		System.out.println(main.msg1);
		System.out.println(main.msg3);
		System.out.println(main.msg4);
		System.out.println((float)12.1222224d);
		try{
			System.in.read();
		}
		catch(IOException e){}
	}

	String getMsg3(boolean bool){
		return "msg3";
	}
}
