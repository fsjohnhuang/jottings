import java.util.*;
class M{
	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("test");
		List<?>[] lsa = new ArrayList<?>[10];
		lsa[0] = list;
		System.out.println(lsa[0].get(0));
	}
}
