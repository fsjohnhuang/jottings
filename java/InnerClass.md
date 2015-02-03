�ڲ����Ǳ������ṩ�����ԣ��������Ὣ�ڲ�������Ϊ�������࣬Ȼ��JVM�Ὣ�ڲ��൱����ͨ��������<br/>

**1. ��Ա�ڲ���**<br/>
  ������һ������ڲ���<br/>
````
import java.io.*;
class Main{
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
class MemberCls{
	private int val = 1;

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
}
````
���Ϊ:
````
1
1
2
2
3
3
````
������MemberCls.class��MemberCls$Inner.class�������ļ���<br/>
���ڳ�Ա�ڲ����������ⲿ��ʵ������˴����ڲ���ʵ��ʱҪ�ȴ����ⲿ��ʵ����Ȼ��ͨ������������ʽ�������ڲ���ʵ����<br/>
````
// ��ʽһ
�ڲ��� �ڲ���ʵ�� = �ⲿ��ʵ��.new �ڲ���();

// ��ʽ��
�ⲿ��{
  �ڲ���{}
  �ڲ��� get�ڲ���(){
    return new �ڲ���();
  }
}
````
�ڲ�����Է����ⲿ����������͵��ֶκͷ���������private����<br/>
ע�⣺<br/>
1. ����Ա�ڲ���ӵ�����ⲿ��ͬ���ĳ�Ա�����򷽷�ʱ��Ĭ����ʹ�ó�Ա�ڲ���ĳ�Ա����Ҫ�����ⲿ���ͬ����Ա������Ҫ�������²�����<Br/>
````
�ⲿ��.this.��Ա����;
�ⲿ��.this.��Ա����;
````
2. ����ͬһ���ⲿ��ʵ���������ڲ���ʵ������Щ�ڲ���ʵ��������ͬһ���ⲿʵ��������������������������ͬһ��val�ֶΡ�<br/>
��Դ���֪��MemberCls�ڲ�JVM�Զ�����������̬����
````
/** �ȼ���
 * static int setVal(MemberCls outer, int val){
 * 	outer.val = val;
 *      return val;
 * }
 */
static int access$002(MemberCls, int);
    flags: ACC_STATIC, ACC_SYNTHETIC
    Code:
      stack=3, locals=2, args_size=2
         0: aload_0       
         1: iload_1       
         2: dup_x1        
         3: putfield      #1                  // Field val:I
         6: ireturn       
      LineNumberTable:
        line 2: 0

/** �ȼ���
 * static int getVal(MemberCls outer){
 *      return outer.val;
 * }
 */
  static int access$000(MemberCls);
    flags: ACC_STATIC, ACC_SYNTHETIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0       
         1: getfield      #1                  // Field val:I
         4: ireturn       
      LineNumberTable:
        line 2: 0
````
���ڲ����ڵ����ⲿ��ʵ����˽�г�Ա������
````
/** �ȼ���
 * void setVal(int val){
 * 	MemberClsʵ��.setVal(val);
 * }
 */
void setVal(int);
    flags:
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0       
         1: getfield      #1                  // Field this$0:LMemberCls;
         4: iload_1       
         5: invokestatic  #3                  // Method MemberCls.access$002:(LMemberCls;I)I
         8: pop           
         9: return        
      LineNumberTable:
        line 7: 0
        line 8: 9

/** �ȼ���
 * int getVal(int val){
 * 	MemberClsʵ��.getVal();
 * }
 */
  int getVal();
    flags:
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0       
         1: getfield      #1                  // Field this$0:LMemberCls;
         4: invokestatic  #4                  // Method MemberCls.access$000:(LMemberCls;)I
         7: ireturn       
      LineNumberTable:
        line 10: 0
````
��������ⲿ�����Ĭ�Ϻ�public���ַ������η����ԣ���Ա�ڲ������Ĭ�ϡ�private��proteced��public���ַ������η���Ч�����Ա�ֶκͷ�����һ����<br/>
**2. �ֲ��ڲ��ࣻ**
 �ֲ��ڲ��ඨ���ڷ�����ĳ�����������棬���ҽ����ڷ����͸��������ڷ��ʡ�<br/>
 ע�⣺<br/>
   1. �ֲ��ڲ����н��ܷ��ʷ������������ڵĳ��������ʱ��������ʱ�����<br/>
   2. ������public��protected��private��static������<br/>
````
import java.io.*;

class Main{
	public static void main(String[] args) throws IOException{
		LocalCls outer = new LocalCls();
		outer.print();

		System.in.read();
	}
}

class LocalCls{
	private int val = 1;

	void print(){
		final String name = "fsjohnhuang";
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
	}
}
````
���Ϊ��<br/>
````
1
2
fsjohnhuang
````
������LocalCls.class��LocalCls$1Inner.class�������ļ���<br/>
��ʵ���ɵ�class�ļ����Ա�ڲ���ļ���һ������ô����˵�ڲ������÷�Χ��������ʵ�Ǳ����������ƣ�������JVM�������ˡ�<br/>
����Ϊʲô�ֲ��ڲ����н��ܷ��ʷ������������ڵĳ�����<br/>
������Է��ʾֲ���������ôҪ���ǵ���������õ��ֲ�������<br/>
���Ⱦֲ������Ǵ����JVMջ֡�еľֲ��������У����ҵ�����ִ����ջ֡Ҳ��֮������Ҳ����˵�ֲ�������ռ���ڴ�ռ��Ƕ��ݵģ����ȶ�����<br/>
����ֲ�����A�ǻ������͵Ļ�����ô����ֱ�Ӿʹ���ھֲ�����������Ӧ��Slots�У�����ִ�����û�ˡ��Ǿֲ��ڲ����������ʵľֲ�����A������ʲô���޴ӵ�֪�ˣ�<br/>
����ֲ�����A��String���ͻ����������ͣ���ô�ֲ��ڲ����з��ʵľֲ�����Aʱ�������ַ�ʽ�ˣ���һ���Ƿ���String�������и��ַ����ĵ�ַ���ڶ�����ָ��ֲ�����A�ĵ�ַ��Ȼ��ͨ������Aȥ����String�������и��ַ�����<br/>
��������Щ����������ʱ���ܾ����ġ�����ʱ�޷���ȷ�ر�����������<br/>
�����������ڷ�������HotSpot JVM�пɳ�Ϊ���ô����У�����ʱ�Ѿ�����׼ȷ������������JVM�ڴ���ʱҲʮ�ּ򵥸�Ч��<br/>
````
java.lang.String getName();
    flags:
    Code:
      stack=1, locals=1, args_size=1
         0: ldc           #5                  // String fsjohnhuang
         2: areturn       
      LineNumberTable:
        line 23: 0
````



**3. �����ڲ��ࣻ**<br/>
�����ڲ�����ʵ�Ǿֲ��ڲ����������ʽ��һ���������¼�������������ϡ�Androidʾ����
````
class Outer{
  public void subs(){ 
	  scan_bt.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			 
		    }
		});
  }
}
````
��������������һ���̳�OnClickListener��������ڲ��࣬Ȼ��ʵ�����������һ��ʵ����Ȼ���Ը�ʵ����Ϊ��������setOnClickListener������<br/>
������һ��Outer.class��Outer$1.class���ļ���<br/>

**4. ��̬�ڲ���**<br/>
��̬�ڲ��ඨ�������£�ֻ�������˸��ؼ���static����̬�ڲ���ֻ�ܷ����ⲿ��ľ�̬�ֶκ;�̬������<br/>
��ʵ������̬�ڲ���ʱֻ��`new �ⲿ��.��̬�ڲ���()`��<br/>
