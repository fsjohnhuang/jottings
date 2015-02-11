# 泛型(类型参数化)


## 通配符`?`

如果Foo是Bar的子类型,而G是带泛型的类型,则G<Foo>不是G<Bar>的子类.
````
List<Apple> apples = new ArrayList<Apple>();
List<Fruit> fruits = new ArrayList<Fruit>();
fruits.add(new Apple()); // 抛出异常
fruits = apples; // 抛出异常
````
通配符`?`用于标识类型为未知类型,因此可赋予任何类型参数的实际类型给它.
````
List<?> fruits = new ArrayList<Fruit>();
fruits = new ArrayList<Apple>();
````
当集合的类型参数为`?`时则只能添加`null`到集合中,因为null是所有类的子类.
````
Collection<?> c = new ArrayList<String>();
c.add(null);
c.add(new Object()); // 抛出异常
````
单纯使用通配符和不使用泛型的效果是一样的.
````
// 不使用泛型的情景
void forEach(List lst){
  for (Object item : lst){
    System.out.println((String)item);
  }
}
List lst = new ArrayList();
lst.add("Test");
forEach(lst);

// 使用通配符的情景
void forEach2(List<?> lst){
  for (Object item : lst){
    System.out.println((String)item);
  }
}
List<String> lst = new ArrayList<String>();
lst.add("Test2");
forEach2(lst);
````

### 边界通配符`? extends`
用于限制参数类型必须为哪个类的子类.
````
void forEach(List<? extends Fruit> lst){
  for (Fruit item : lst){
    System.out.println(item.getName());
  }
}
List<Apple> lst = new ArrayList<Apple>();
forEach(lst);

List<? extends Fruit> olst = new ArrayList<Apple>();
olst.add(new Apple());
````

### 边界通配符`? super`
用于限制参数类型必须为哪个类的超类.
对于集合而言,则集合元素限制为这个类的子类型实例.因为无法得知父类是什么,更无法知道是否向上兼容.
````
void forEach(List<? extends Apple> lst){
  for (Fruit item : lst){
    System.out.println(item.getName());
  }
}
List<Fruit> lst = new ArrayList<Fruit>();
forEach(lst);

List<? super Apple> olst = new ArrayList<Fruit>();
olst.add(new Apple());
olst.add(new Fruit()); // 将抛出异常
````

## 泛型方法
````
<T> void fromArray2Lst(T[] a, List<T> b){
  for (T o : a)
    b.add(o);
}
Integer[] array = {1,2,3};
List<Integer> lst = new ArrayList<Integer>>();
fromArray2Lst(array, lst);

List<Number> lst = new ArrayList<Number>();
fromArray2Lst(array, lst);
````
类型参数`<T>`必须放在返回值类型前.T的实际类型可以由由T类型的实参的数据类型隐式决定.
但当有重载的情况时则需要显式指明.
````
class Main{
	<T> void go(T t){
	  System.out.println("generic function");
	}
	void go(String str){
	  System.out.println("normal function");
	}

	public static void main(String[] args){
          Main main = new Main();
          main.go("haha"); // 输出normal function
          main.<String>go("haha"); // 输出generic function
 	} 
}
````

### JDK1.6开始支持泛型方法重载,但需要返回值不同才行
````
void go(List<String> lst){}
int go(List<int> lst){}
````

### 采用擦写的方式导致以下效果
````
List<String> l1 = new ArrayList<String>();
List<Integer> l2 = new ArrayList<Integer>();
System.out.println(l1.getClass() == l2.getClass()); // true
````

### 不能對泛型(不带通配符)使用instanceof
````
List lst = new ArrayList<String>();
if (lst instanceof List<String>){} // 抛异常
if (lst instanceof List<?>){}
````

### 泛型数组
只能使用通配符来创建泛型数组
````
List<?>[] lsa = new ArrayList<?>[10];
````
