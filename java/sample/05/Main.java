import java.util.*;
class Main{
	public static void main(String[] args){
		Fruit<S> f = new Fruit<S>();
	}

	static class P{}
	static class S extends P{}

	static class Fruit<T>{
	  private T fruit;
	  T getFruit(){
	    return fruit;
	  }
	  void setFruit(T fruit){
	    this.fruit = fruit;
	  }
	  private List<T> fruits;
	  void setFruits(List<? extends T> lst){
	    fruits = (List<T>)lst;
	  }
	  void setFruits2(List<T> lst){
	    fruits = lst;
	  }

	  // 构造函数不用带泛型
	  Fruit(){
	    fruits = new ArrayList<T>();
	  }
	}
}
