import java.io.*;
class MemberCls{
	private int val = 1;

	static int get(MemberCls self){
		return self.val;
	}

	class Inner{
		void setVal(int val){
			MemberCls.this.val = val;
		}	
		int getVal(){
			return val;
		}
	}

	Inner getInner(){
		return new Inner();
	}

	public static void main(String[] args) throws IOException{
		MemberCls outer = new MemberCls();
		Inner inner1 = outer.new Inner();
		Inner inner2 = outer.getInner();

		System.out.println(inner1.getVal());
		System.out.println(inner2.getVal());
		inner1.setVal(2);
		System.out.println(inner1.getVal());
		System.out.println(inner2.getVal());
		inner2.setVal(3);
		System.out.println(inner1.getVal());
		System.out.println(inner2.getVal());

		System.in.read();
	}
}
