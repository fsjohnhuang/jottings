import java.util.*;
class Main{
	public static void main(String[] args)
		throws InstantiationException, IllegalAccessException{
	  //Integer ret = s.handle(1, 1);
	  //s.handle(new ArrayList<Integer>());
	}
	public static <T extends P> T getP1(Class<T> clazz){
	  T ret = null;
	  try{
	    ret = clazz.newInstance();
	  }
	  catch(InstantiationException|IllegalAccessException e){}
	  return ret;
	}

	public static <T> T getP2(Class<? extends P> clazz){
	  T ret = null;
	  try{
	    ret = (T)clazz.newInstance();
	  }
	  catch(InstantiationException|IllegalAccessException e){}
	  return ret;
	}

	static class P{}
	static class S extends P{
		  /*public <T extends Number> T handle(T arg1, T arg2){
			return arg1;
		  }*/
		  public void handle(List<? extends Number> arg){
		  }
		  /*public <T extends Number> T say(T msg, T tt){
		    System.out.println("<T extends Object> Generic Type Method!");
		    return msg;
		  }*/
	}
}
