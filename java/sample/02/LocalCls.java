import java.io.*;

class LocalCls{
	public static void main(String[] args) throws IOException{
		LocalCls outer = new LocalCls();
		outer.print();

		System.in.read();
	}

	private int val = 1;


	void print(){
		final String name = new String("fsjohnhuang");
		class Inner{
			int getVal(){
				return val;
			}	
			void setVal(int val){
				LocalCls.this.val = val;
			}
			String getName(){
				return name;
			}
		}

		Inner inner = new Inner();
		System.out.println(inner.getVal());
		inner.setVal(2);
		System.out.println(inner.getVal());
		System.out.println(inner.getName());

		System.out.println(inner.getName() == name);
	}
}
