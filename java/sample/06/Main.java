import java.util.*;
class Main{
  public static void main(String[] args){

  }
  static class t<T extends Object & Collection>{
  }

  static class Fruit<T>{
	  // ���Ͳ���ռλ����Ϊʵ���ֶε�����
	  private T fruit;

	  // ���Ͳ���ռλ����Ϊʵ�������ķ���ֵ����
	  T getFruit(){
	    return fruit;
	  }
	  // ���Ͳ���ռλ����Ϊʵ���������������
	  void setFruit(T fruit){
	    this.fruit = fruit;
	  }
	  private List<T> fruits;
	  // ���Ͳ���ռλ����Ϊ�߽�ͨ�������������
	  void setFruits(List<? extends T> lst){
	    fruits = (List<T>)lst;
	  }
	  // ���Ͳ���ռλ����Ϊʵ��������������͵����Ͳ���
	  void setFruits2(List<T> lst){
	    fruits = lst;
	  }

	  // ���캯�����ô�����
	  Fruit(){
	    // ���Ͳ���ռλ����Ϊ�ֲ�����������
	    fruits = new ArrayList<T>();
	    T fruit = null;
	  }
  }
}
