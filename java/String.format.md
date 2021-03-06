﻿# 文本格式化
## `String.format`
````
// 2个重载方法
String String.format(String fmt, Object... args);
String String.format(Locale locale, String fmt, Object... args);
````
**使用格式转换符作为占位符**<Br/>
````
%s，字符串类型
%c，字符类型。实参必须可以转换为字符类型，否则会报IllegalFormatConversionException异常
%b，布尔类型。实参为false以外的值均转换为true
%d，整数类型（十进制）
%x，整数类型（十六进制）
%o，整数类型（八进制）
%f，浮点数型（十进制）。显示9位有效数字，且会进行四舍五入。如99.99
%a，浮点数型（十六进制）
%e，指数类型。如9.38e+5
%g，浮点数型（比%f，%a长度短些，显示6位有效数字，且会进行四舍五入）
%%
%n，平台独立的换行符, 也可通过System.getProperty("line.separator")获取
%tx，日期和时间类型，x代表另外的处理日期和时间格式的转换符。
````
````
double num = 123.4567899;
System.out.print(String.format("%f %n", num)); // 123.456790 
System.out.print(String.format("%a %n", num)); // 0x1.edd3c0bb46929p6 
System.out.print(String.format("%g %n", num)); // 123.457 
````
**日期时间转换符%tx**<br/>
下列转换符均是替换x后使用，如24小时制的小时的转换符为H,那么实际应用时为%tH<br/>
**日期的转换符**<Br/>
````
c，星期六 十月 27 14:21:20 CST 2007
F，2007-10-27
D，10/27/07
r，02:25:51 下午
T，14:28:16
R，14:28
b, 月份简称
B, 月份全称
a, 星期简称
A, 星期全称
C, 年前两位（不足两位补零）
y, 年后两位（不足两位补零）
j, 当年的第几天
m, 月份（不足两位补零）
d, 日期（不足两位补零）
e, 日期（不足两位不补零）
````
**时间的转换符**<br/>
````
H, 24小时制的小时（不足两位补零）
k, 24小时制的小时（不足两位不补零）
I, 12小时制的小时（不足两位补零）
i, 12小时制的小时（不足两位不补零）
M, 分钟（不足两位补零）
S, 秒（不足两位补零）
L, 毫秒（不足三位补零）
N, 毫秒（不足9位补零）
p, 小写字母的上午或下午标记，如中文为“下午”，英文为pm
z, 相对于GMT的时区偏移量，如+0800
Z, 时区缩写，如CST
s, 自1970-1-1 00:00:00起经过的秒数
Q, 自1970-1-1 00:00:00起经过的豪秒数
````

**转换符的标识**<br/>
用于增强转换符的格式化能力。<Br/>
对整数进行格式化：`%[index$][标识]*[最小宽度][转换方式]`<br/>
`[index$]`的索引值以1开始。<br/>
示例——将1显示为0001：<br/>
````
int num = 1;
String str = String.format("%04d", num);
````
示例——将-1000显示为(1,000)：<br/>
````
int num = -1000;
String str = String.format("%(,d", num);
````
标识：<br/>
````
-，在最小宽度内左对齐,不可以与0标识一起使用。
0，若内容长度不足最小宽度，则在左边用0来填充。
#，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
+，结果总包含一个+或-号。
空格，正数前加空格，负数前加-号。
,，只用与十进制，每3位数字间用,分隔。
(，若结果为负数，则用括号括住，且不显示符号。
````
对浮点数进行格式化：`%[index$][标识]*[最小宽度][.精度][转换方式]`br/>
标识：<br/>
````
-，在最小宽度内左对齐,不可以与0标识一起使用。
0，若内容长度不足最小宽度，则在左边用0来填充。
#，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
+，结果总包含一个+或-号。
空格，正数前加空格，负数前加-号。
,，只用与十进制，每3位数字间用,分隔。
(，若结果为负数，则用括号括住，且不显示符号。
````
对字符进行格式化：`%[index$][标识][最小宽度][转换方式]`<Br/>
标识：<br/>
````
-，在最小宽度内左对齐。
````
其他标识：<br/>
````
<，格式化前一个转换符所描述的参数
````
````
int num = 1000;
String str = String.format("%d %<,d", num);
// 结果"1000 1,000"
````
