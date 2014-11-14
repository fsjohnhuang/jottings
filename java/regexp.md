#正则表达式
## 包和类
`java.util.regex.Pattern`：模式类，对正则表达式进行编译，效率高。<br/>
静态方法<br/>
````
/*
 * 对正则表达式进行编译，并返回Pattern实例
 * 入参flag作为表达式属性，启用多个表达式属性时，采用管道符（|）连接多个表达式属性。除了通过入参的方式设置表达式属性，还可以使用嵌入式标识来设置表达式属性，格式为：(?表达式属性1表达式属性2)正则表达式，示例——不区分大小写和全局匹配abcd：(?ig)abcd
 */
Pattern compile(String regex);
Pattern compile(String regex, int flag);

// 字符串完整匹配表达式的快捷方式，内部依然是
// Pattern p = Pattern.compile(regex);
// p.matcher(input).matches();
boolean matches(String regex, CharSequence input);

// 返回可以配置入参s的字面量模式。注意格式为\\Q表达式\\E。表达式中的元字符将当作普通字符处理
String quote(String s);
````
表达式属性<br/>
````
// 以\n作为换行符，内嵌为(?d)
Pattern.UNIX_LINES

// US-ASCII编码字符不区分大小写，内嵌为(?i)
Pattern.CASE_INSENSITIVE

// 忽略空格和注释（注释为以#开头直到出现换行符），内嵌为(?x)
Pattern.COMMENTS

// 启动多行模式，^和$匹配换行符或字符串起始位置。默认为单行模式，^和$仅匹配字符串起始位置。内嵌为(?m)
Pattern.MULTILINE

// 字面量模式，将元字符当作普通字符处理，没有内嵌方式，但可以通过"\\Q正则表达式\\E"的方式实现
Pattern.LITERAL

// 元字符.将匹配换行符。默认情况下，元字符.不匹配换行符。内嵌为(?s)
Pattern.DOTALL

// UNICODE编码字符不区分大小写，内嵌为(?u)
Pattern.UNICODE_CASE

// 当且仅当正则分解匹配时才配置成功。
Pattern.CANON_EQ

// 启用Unicode版本的预定义字符类和POSIX字符类，内嵌为(?U)
Pattern.UNICODE_CHARACTER_CLASS
````
**实例方法**<br/>
````
// 返回正则表达式
String pattern();

// 使用正则表达式匹配的字符串切割入参input
// 入参limit用于设置返回数组长度的最大值，设置为0时则不限制最大值。
String[] split(CharSequence input);
String[] split(CharSequence input, int limit);

// 获取匹配类
Matcher matcher(CharSequence input);
````
<br/>
`java.util.regex.Matcher`：匹配类，为模式实例匹配某字符串后所产生的结果。<br/>
静态方法<br/>
````
// 将入参s中的\和$元字符转换为普通字符，并返回处理后的s字符串。
String quoteReplacement(String s);
````
实例方法<Br/>
````
// 获取匹配子字符串的起始索引
int start();
// 获取匹配子字符串的结束索引
int end();

// 从字符串的end+1位置开始搜索下一个匹配的字符串
boolean find();
boolean find(int start);

// 通过分组索引获取分组内容，若入参group超出分组数量则抛异常
String group();
String group(int group);
// 通过分组名称获取分组内容，若没有相应的分组则返回null
String group(String name);

// 重置匹配实例内部的状态属性
Matacher reset();
// 重置匹配实例内部的状态属性，并重置被匹配的字符串
Matacher reset(CharSequence input);
// 重置模式实例，这导致group信息丢失，但注意：start等信息依旧保留不变。
Matcher usePattern(Pattern newPattern);

// 从字符串起始位开始将匹配成功的子字符串均用入参replacement替换掉
String replaceAll(String replacement);
// 从字符串起始位开始将第一个匹配成功的子字符串均用入参replacement替换掉
String replaceFirst(String replacement);
// 将从字符串起始位开始到最后一匹配的子字符串最后一个字符的位置的字符串复制到sb中，并用入参replacement替换sb中匹配的内容
String appendReplace(StringBuffer sb, String replacement);
// 将剩余的子字符串复制到sb中
String appendTail(StringBuffer sb);
// 示例: sb为one dog two dog
Matcher m = p.matcher("one cat two cats in the yard");
StringBuffer sb = new StringBuffer();
while (m.find()) {
  m.appendReplacement(sb, "dog");
}

// 字符串从头到尾匹配表达式
boolean matches();
// 从字符串起始位置开始匹配表达式，但不要字符串从头到尾匹配表达式
boolean lookingAt();
```` 
<Br/>
`java.lang.String`
实例方法<Br/>
````
String replaceAll(String replacement);
String replaceFirst(String replacement);
String[] split(String regex);
String[] split(String regex, int limit);
boolean matches(String regex);
````

## 正则表达式
1. **集合关系**<br/>
````
// a、b或c
[abc]
// 除a、b和c外的其他字符
[^abc]
// 所有英文字符
[a-zA-Z]
// 并集，a到d或m到p的字符
[a-d[m-p]]
// 交集，字符d
[a-d&&[def]]
````


## 第三方增强
Jakarta-ORO<br/>
