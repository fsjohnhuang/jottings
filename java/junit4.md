## 固件测试
  就是每个测试方法执行前和后都执行的方法，用于自动初始化和回收资源等工作。通过`@Before`注解标注测试方法执行前调用的固件测试，通过`@After`注解标注测试方法执行后调用的固件测试。<br/>
  父类的固件测试会在子类的固件测试前被调用。<Br/>
  另外可通过注解`@BeforeClass`和`@AfterClass`标注某些static方法为测试开始前和结束后被调用，用于自动初始化和回收资源等工作。注意通过注解`@BeforeClass`和`@AfterClass`标注的方法一次测试过程仅被调用一次而已。示例如下：
````
public class MyUT{
  int i, j;
  @BeforeClass public void static init(){
    System.out.println("init");
    i = 0;
    j = 0;
  }
  
  @Before public void invokeBefore(){
    System.out.println("invokeBefore" + ++i);
  }

  @Test public void testMyMethod1(){
    System.out.println("testMyMethod1");
  }

  @Test public void testMyMethod2(){
    System.out.println("testMyMethod2");
  }

  @After public void invokeAfter(){
    System.out.println("invokeAfter" + ++j);
  }

  @AfterClass public void static destroy(){
    System.out.println("destroy");
    i = 0;
    j = 0;
  }
}

// 输出结果
init
invokeBefore1
testMyMethod1
invokeAfter1
invokeBefore2
testMyMethod2
invokeAfter2
destroy
````

## 忽略测试方法
通过注解`@Ignore("not run")`可以标注不参与测试的测试方法。当然也可以通过去除注解`@Test`来达到这个目的，但去除注解`@Test`会令到eclipse的JUnit View中无法显示该测试方法。<br/>


## 异常测试
通过注解`@Test(expected=Class类型)`来标注期待测试方法执行时抛出哪种异常对象，若测试方法不抛出异常或异常对象与期待的异常对象不相同则测试失败。<Br/>
````
@Test(expected=ArithmeticException.class) public void calc(){
  int i = 1/0;
}
````

## 超时测试
通过注解`@Test(timeout=毫秒数)`来标注期待执行测试方法的最大耗时，若超时则测试失败。<Br/>
````
@Test(timeout=1000) public void wait(){
  while(true){}
}
````

## 测试运行器
用于执行JUnit中所有的测试方法。JUnit为单元测试提供默认的测试运行器，但我们可以自定义，自定义的测试运行器必须继承`org.junit.runner.Runner`。然后通过类注解`@RunWith(CustomTestRunner.class)`来指定该测试的测试运行器。<br/>
内置的测试运行器：<br/>
1. `org.junit.runners.Suite`<br/>
2. `org.junit.runners.Parameterized`<br/>


## 参数化测试
用于模拟以不同的参数组合来对方法进行多次测试。若不使用参数化测试，该测试方法有N个不同的参数组合，则需要写N个测试方法来测试。<br/>
````
// 需要使用Parameterized测试运行器才可以
@RunWith(Parameterized.class)
public class MyUT{
  // 成员变量，用于存放测试用数据和测试期望值
  int orig;
  int expected;
  public MyUT(int orig, int expected){
    this.orig = orig;
    this.expected = expected;
  }
  
  @Test public void testMyMethod(){
      assertEquals(expected, orig + 1);
  }

  /**
   * 测试数据和测试期望值的提供方法
   * 必须用注解@Parameters标注
   * 必须返回Collection类型数据
   */
  @Parameters public Collection dataFeed(){
    return Arrays.asList(new Object[][]{
	  {1, 2},
	  {2, 3},
	  {3, 4}
	});
  }
}
````

## 套件测试
按业务逻辑将测试类进行分组，并以组为单位执行单元测试。<br/>
````
// 测试类1
public class MyUT1{
  @Test public void testMyMehthod1(){
    assertEquals(1,1);
  }
}
// 测试类2
public class MyUT2{
  @Test public void testMyMehthod2(){
    assertEquals(2,2);
  }
}
// 套件测试类
@RunWith(Suite.class)
@SuiteClass({MyUT1.class, MyUT2.class})
public class SuiteTest{
  // 必须有一个public，无参数的构造函数。使用默认的构造函数也可以
  public SuiteTest(){}
}
````



## `assertThat断言`
结合Hamcrest测试框架的匹配符，可实现几乎所有测试。而Hamcrest的匹配符贴近自然语言，因此意思表达更明确<br/>
JUnit4.4开始内置Hamcrest框架，JUnit4.4以下的版本需要添加`hamcrest-core.jar`和`hamcrest-library.jar`。<br/>
````
/* assertThat语法
 * assertThat(T actual, Matcher<T> matcher);
 * assertThat(String reason, T actual, Matcher<T> matcher);
 * 入参actual为实际值，入参matcher为期待值的匹配符
 */

//测试变量是否大于指定值
assertThat(1, greaterThan(50));
//测试变量是否小于指定值
assertThat(1, lessThan(100));
//测试变量是否大于等于指定值
assertThat(1, greaterThanOrEqualTo(50));
//测试变量是否小于等于指定值
assertThat(1, lessThanOrEqualTo(100));
                  
//测试所有条件必须成立
assertThat(1, allOf(greaterThan(50),lessThan(100)));
//测试只要有一个条件成立
assertThat(1, anyOf(greaterThanOrEqualTo(50), lessThanOrEqualTo(100)));
//测试无论什么条件成立(还没明白这个到底是什么意思)
assertThat(1, anything());
//测试变量值等于指定值
assertThat(1, is(100));
//测试变量不等于指定值
assertThat(1, not(50));

/**字符串**/
String url = "http://www.taobao.com";
//测试变量是否包含指定字符
assertThat(url, containsString("taobao"));
//测试变量是否已指定字符串开头
assertThat(url, startsWith("http://"));
//测试变量是否以指定字符串结尾
assertThat(url, endsWith(".com"));
//测试变量是否等于指定字符串
assertThat(url, equalTo("http://www.taobao.com"));
//测试变量再忽略大小写的情况下是否等于指定字符串
assertThat(url, equalToIgnoringCase("http://www.taobao.com"));
//测试变量再忽略头尾任意空格的情况下是否等于指定字符串
assertThat(url, equalToIgnoringWhiteSpace("http://www.taobao.com"));

/**集合**/
List<User> user = new ArrayList<User>();
user.add(test1);
user.add(test2);
                  
//测试集合中是否还有指定元素
assertThat(user, hasItem(test1));
assertThat(user, hasItem(test2));
  
/**Map匹配**/
Map<String,User> userMap = new HashMap<String,User>();
userMap.put(test1.getUsername(), test1);
userMap.put(test2.getUsername(), test2);
                  
//测试map中是否还有指定键值对
assertThat(userMap, hasEntry(test1.getUsername(), test1));
//测试map中是否还有指定键
assertThat(userMap, hasKey(test2.getUsername()));
//测试map中是否还有指定值
assertThat(userMap, hasValue(test2));
````

## `assumeThat假设断言`
位于`org.junit.Assume类`下，同样是属于Hamcrest框架的。用于假设当条件成立时才会执行后续的代码，条件不成立时是不会影响测试结果。<br/>
````
assumeThat(1, is(0));
System.out.println("I'm here"); // 这句不会被执行
````


## 参考
http://blog.csdn.net/skyie53101517/article/details/8739126


