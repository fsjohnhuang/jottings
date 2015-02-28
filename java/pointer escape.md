# 引用/指针逃逸
## 定义
当一个对象的指针被多个方法或线程引用时，则称这个指针发生了逃逸。
## 常见场景
1. 全局变量赋值
2. 方法返回值
3. 实例引用传递
````
class A{
  static B b;
  public void initStaticB(){
    b = new B();
  }

  public B getB(){
    return new B();
  }

  public void passB(){
    test(new B());
  }
}
class B{}
````
## this引用逃逸
属于实例引用传递的一种。会导致操作未初始化完成的实例。
````
class A{
  final int i;
  A(EventSource src){
    src.registerListener(new EventListener(){
    	@Override
	public void onEvent(Event e){
		// 实际上是调用A.this.test(e)
                // 因此在执行i = 45之前，其他线程可以就会触发事件进而执行A.this.test方法
		test(e);
        }
    });
    i = 45;
  }

  void test(){
    if (i != 45)
	System.out.println("race condition detected.");
  }
}
````
