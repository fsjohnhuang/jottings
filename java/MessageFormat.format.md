#MessageFormat.format
##方法定义
`java.text.MessageFormat.format(String pattern, Object...args)`<br/>
## MessageFormat模式
格式：`{ArgumentIndex, FormatType, FormatStyle}`<Br/>
ArgumentIndex是从0开始的索引。<br/>
##FormatType的值
````
number,调用NumberFormat进行格式化
date,调用DateFormat进行格式化
time,调用DateFormat进行格式化
choice,调用ChoiceFormat进行格式化
````
## FormatStyle
````
short
medium
long
full
integer
currency
percent
SubformtPattern(子格式模式)
````
FormatType和FormatStyle均主要用于格式化日期时间、数字、百分比等。<br/>

## MessageFormat模式的注意点
1.两个单引号才表示一个单引号，仅写一个单引号将会被忽略。<Br/>
2.单引号会使其后面的占位符均失效，导致直接输出占位符。<br/>
````
MessageFormat.format("{0}{1}", 1, 2); // 结果12
MessageFormat.format("'{0}{1}", 1, 2); // 结果{0}{1}
MessageFormat.format("'{0}'{1}", 1, 2); // 结果{0}2
````
因此可以用于输出左花括号(单写左花括号会报错，而单写右花括号将正常输出)<br/>
````
MessageFormat.format("'{'{0}}", 2); // 结果{2}
````

## java.text.Format根抽象类
Format子类：DateFormat，MessageFormat，NumberFormat<br/>
DateFormat子类：SimpleDateFormat<Br/>
NumberFormat子类：ChoiceFormat,DecimalFormat<br/>


## 参考
http://zqc-0101.iteye.com/blog/1140140
