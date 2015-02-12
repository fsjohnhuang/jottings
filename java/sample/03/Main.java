import java.util.*;
class Main{
	public static void main(String[] args){
		/*Object[] objs = new String[1];
		objs[0] = "test";
		System.out.println(objs[0]);*/

		/*List<Object> lst = new ArrayList<Object>();
		lst.add("test");
		System.out.println(lst.get(0));*/

		/*Object[] objs = new String[1];
		objs[0] = "test";
		System.out.println(objs[0].getClass().toString());*/
		
		/*List<String> lst = new ArrayList<String>();
		lst.add("test");
		List<?> l = lst;
		System.out.println(l.get(0));*/

		/*Main main = new Main();
		Integer[] array = {1,2,3};
		Integer sum = main.sum(0, array);
		System.out.println(sum);*/

		//List<? super S> lst = new ArrayList<P>();
		//lst.add(new P());
		//lst.add(new Long(1));
		
		Main main = new Main();
		P p = Main.get(S.class);
		System.out.println(p.i);
	}

	/*static <T> T get(Class<? extends P> clazz){
		T ret = null;
		try{
		  ret = (T)clazz.newInstance();	
		}
		catch(InstantiationException|IllegalAccessException e){}
		return ret;
	}

	static <T extends P> T get(Class<T> clazz){
		T ret = null;
		try{
		  ret = clazz.newInstance();	
		}
		catch(InstantiationException|IllegalAccessException e){}
		return ret;
	}*/

	static <T> T get(Class<?> clazz){
		T ret = null;
		try{
		  ret = (T)clazz.newInstance();	
		}
		catch(InstantiationException|IllegalAccessException e){}
		return ret;
	}

	static class P{
		public int i = 1;
	}
	static class S extends P{}

	/*<T> T sum(T orig, T[] a){
	  T ret = orig;
	  for (T o : a)
	    ret += o;
	  return ret;
	}*/
}
