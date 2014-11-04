## �̼�����
  ����ÿ�����Է���ִ��ǰ�ͺ�ִ�еķ����������Զ���ʼ���ͻ�����Դ�ȹ�����ͨ��`@Before`ע���ע���Է���ִ��ǰ���õĹ̼����ԣ�ͨ��`@After`ע���ע���Է���ִ�к���õĹ̼����ԡ�<br/>
  ����Ĺ̼����Ի�������Ĺ̼�����ǰ�����á�<Br/>
  �����ͨ��ע��`@BeforeClass`��`@AfterClass`��עĳЩstatic����Ϊ���Կ�ʼǰ�ͽ����󱻵��ã������Զ���ʼ���ͻ�����Դ�ȹ�����ע��ͨ��ע��`@BeforeClass`��`@AfterClass`��ע�ķ���һ�β��Թ��̽�������һ�ζ��ѡ�ʾ�����£�
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

// ������
init
invokeBefore1
testMyMethod1
invokeAfter1
invokeBefore2
testMyMethod2
invokeAfter2
destroy
````

## ���Բ��Է���
ͨ��ע��`@Ignore("not run")`���Ա�ע��������ԵĲ��Է�������ȻҲ����ͨ��ȥ��ע��`@Test`���ﵽ���Ŀ�ģ���ȥ��ע��`@Test`���eclipse��JUnit View���޷���ʾ�ò��Է�����<br/>


## �쳣����
ͨ��ע��`@Test(expected=Class����)`����ע�ڴ����Է���ִ��ʱ�׳������쳣���������Է������׳��쳣���쳣�������ڴ����쳣������ͬ�����ʧ�ܡ�<Br/>
````
@Test(expected=ArithmeticException.class) public void calc(){
  int i = 1/0;
}
````

## ��ʱ����
ͨ��ע��`@Test(timeout=������)`����ע�ڴ�ִ�в��Է���������ʱ������ʱ�����ʧ�ܡ�<Br/>
````
@Test(timeout=1000) public void wait(){
  while(true){}
}
````

## ����������
����ִ��JUnit�����еĲ��Է�����JUnitΪ��Ԫ�����ṩĬ�ϵĲ����������������ǿ����Զ��壬�Զ���Ĳ�������������̳�`org.junit.runner.Runner`��Ȼ��ͨ����ע��`@RunWith(CustomTestRunner.class)`��ָ���ò��ԵĲ�����������<br/>
���õĲ�����������<br/>
1. `org.junit.runners.Suite`<br/>
2. `org.junit.runners.Parameterized`<br/>


## ����������
����ģ���Բ�ͬ�Ĳ���������Է������ж�β��ԡ�����ʹ�ò��������ԣ��ò��Է�����N����ͬ�Ĳ�����ϣ�����ҪдN�����Է��������ԡ�<br/>
````
// ��Ҫʹ��Parameterized�����������ſ���
@RunWith(Parameterized.class)
public class MyUT{
  // ��Ա���������ڴ�Ų��������ݺͲ�������ֵ
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
   * �������ݺͲ�������ֵ���ṩ����
   * ������ע��@Parameters��ע
   * ���뷵��Collection��������
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

## �׼�����
��ҵ���߼�����������з��飬������Ϊ��λִ�е�Ԫ���ԡ�<br/>
````
// ������1
public class MyUT1{
  @Test public void testMyMehthod1(){
    assertEquals(1,1);
  }
}
// ������2
public class MyUT2{
  @Test public void testMyMehthod2(){
    assertEquals(2,2);
  }
}
// �׼�������
@RunWith(Suite.class)
@SuiteClass({MyUT1.class, MyUT2.class})
public class SuiteTest{
  // ������һ��public���޲����Ĺ��캯����ʹ��Ĭ�ϵĹ��캯��Ҳ����
  public SuiteTest(){}
}
````



## `assertThat����`
���Hamcrest���Կ�ܵ�ƥ�������ʵ�ּ������в��ԡ���Hamcrest��ƥ���������Ȼ���ԣ������˼������ȷ<br/>
JUnit4.4��ʼ����Hamcrest��ܣ�JUnit4.4���µİ汾��Ҫ���`hamcrest-core.jar`��`hamcrest-library.jar`��<br/>
````
/* assertThat�﷨
 * assertThat(T actual, Matcher<T> matcher);
 * assertThat(String reason, T actual, Matcher<T> matcher);
 * ���actualΪʵ��ֵ�����matcherΪ�ڴ�ֵ��ƥ���
 */

//���Ա����Ƿ����ָ��ֵ
assertThat(1, greaterThan(50));
//���Ա����Ƿ�С��ָ��ֵ
assertThat(1, lessThan(100));
//���Ա����Ƿ���ڵ���ָ��ֵ
assertThat(1, greaterThanOrEqualTo(50));
//���Ա����Ƿ�С�ڵ���ָ��ֵ
assertThat(1, lessThanOrEqualTo(100));
                  
//�������������������
assertThat(1, allOf(greaterThan(50),lessThan(100)));
//����ֻҪ��һ����������
assertThat(1, anyOf(greaterThanOrEqualTo(50), lessThanOrEqualTo(100)));
//��������ʲô��������(��û�������������ʲô��˼)
assertThat(1, anything());
//���Ա���ֵ����ָ��ֵ
assertThat(1, is(100));
//���Ա���������ָ��ֵ
assertThat(1, not(50));

/**�ַ���**/
String url = "http://www.taobao.com";
//���Ա����Ƿ����ָ���ַ�
assertThat(url, containsString("taobao"));
//���Ա����Ƿ���ָ���ַ�����ͷ
assertThat(url, startsWith("http://"));
//���Ա����Ƿ���ָ���ַ�����β
assertThat(url, endsWith(".com"));
//���Ա����Ƿ����ָ���ַ���
assertThat(url, equalTo("http://www.taobao.com"));
//���Ա����ٺ��Դ�Сд��������Ƿ����ָ���ַ���
assertThat(url, equalToIgnoringCase("http://www.taobao.com"));
//���Ա����ٺ���ͷβ����ո��������Ƿ����ָ���ַ���
assertThat(url, equalToIgnoringWhiteSpace("http://www.taobao.com"));

/**����**/
List<User> user = new ArrayList<User>();
user.add(test1);
user.add(test2);
                  
//���Լ������Ƿ���ָ��Ԫ��
assertThat(user, hasItem(test1));
assertThat(user, hasItem(test2));
  
/**Mapƥ��**/
Map<String,User> userMap = new HashMap<String,User>();
userMap.put(test1.getUsername(), test1);
userMap.put(test2.getUsername(), test2);
                  
//����map���Ƿ���ָ����ֵ��
assertThat(userMap, hasEntry(test1.getUsername(), test1));
//����map���Ƿ���ָ����
assertThat(userMap, hasKey(test2.getUsername()));
//����map���Ƿ���ָ��ֵ
assertThat(userMap, hasValue(test2));
````

## `assumeThat�������`
λ��`org.junit.Assume��`�£�ͬ��������Hamcrest��ܵġ����ڼ��赱��������ʱ�Ż�ִ�к����Ĵ��룬����������ʱ�ǲ���Ӱ����Խ����<br/>
````
assumeThat(1, is(0));
System.out.println("I'm here"); // ��䲻�ᱻִ��
````


## �ο�
http://blog.csdn.net/skyie53101517/article/details/8739126


