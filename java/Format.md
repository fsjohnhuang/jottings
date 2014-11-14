#java.text.Format子类
##DecimalFormat
用于格式化十进制实数。通过格式字符串来自定义格式化类型，舍入方式为half-even(四舍五入)。
**格式字符串**<br/>
`正数子模式;负数子模式`<br/>
````
0.00 等效于 0.00;-0.00
````
子模式的占位符<br/>
`0`: 代表该为位为数字，若不存在则用0填充<br/>
`#`: 代表该为位为数字<br/>
`,`: 代表分隔符, 如模式为#,#，那么格式化10时会返回1,0<br/>

##ChoiceFormat
相当于以数字为键，字符串为值的键值对。分别使用一组double类型的数组作为键，一组String类型的数组作为值，两数组相同索引值的元素作为一对。<br/>
示例——基本用法<br/>
````
double[] limit = {0,1,3};
String[] format = {"hello0", "hello1", "hello3"};
ChoiceFormat cf = new ChoiceFormat(limit, format);
for(int i = 0; i < 4; ++i){
  System.out.println(cf.format(i));
}
/* 输出
 * hello0
 * hello1
 * hello0
 * hello3
 */
````
当找不到对应的键值对时，则使用第一或最后一对键值对。<br/>
示例——结合MessageFormat使用<Br/>
````
double[] limit = {0, 1};
String[] format = {"Hello0", "Hello1{1}"};
ChoiceFormat cf = new ChoiceFormat(limit, format);
MessageFormat mf = new MessageFormat("{0}");
mf.setFormatByArgumentIndex(0, cf);
for (int i = 0; i < 2; ++i){
	System.out.println(mf.format(new Object[]{new Integer(i), new Integer(i+1)}));
}
/* 输出
 * Hello0
 * Hello12
 */
````


