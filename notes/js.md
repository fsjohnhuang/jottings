## 目录 ##
语法

内置函数

设计模式


### 资源 ###
http://openmymind.net/
http://37signals.com/svn/posts/3112-how-basecamp-next-got-to-be-so-damn-fast-without-using-much-client-side-ui
[javascript 秘密花园](http://bonsaiden.github.io/JavaScript-Garden/zh/#object.hasownproperty "秘密花园")
[js忍者书简略教程](http://ejohn.org/apps/learn/ "js忍者书简略教程")
[understanding delete](http://perfectionkills.com/understanding-delete/#comment-166660 "understanding delete")

### 数据类型 ###
1. 原生类型（primitive type）：number、string、boolean、null、undefined（仅声明变量时，变量默认值为undefined）
  1.1. number
	1.1.1. NaN：表示非数字类型，typeof NaN === 'number'。
	>NaN永远不等于其他数据和自身，`NaN == NaN`永远返回false

	1.1.2. Infinity和-Infinity：表示正无穷大和负无穷大，typeof Infinity === 'number'。
	>Infinity永远等于自身，`Infinity == Infinity`永远返回true
	>若某数据类型变量大于无穷大或小于无穷小，则变量值自动被修改为Infinity和-Infinity
	>1/0结果为Infinity
  1.2. null：表示不该有值
  1.3. undefine：表示应该赋值但未赋值
2. 对象类型：Object,function，Date，Array，RexExp等
  2.1. 包装类：Number，String,Boolean（除null和undefined外，其他原生类型均有对应的包装类）
	显式使用：
	```javascript
	var strObj = new String("A");
	var numObj = new Number(1);
	var boolObj = new Boolean(true);
	```
	隐式使用：
	```javascript
	var str = 'abc';
	str.len = 3;
	console.log(str.len); // 显示undefined
	console.log(str.lenght); // 显示3
	```
	数字字面值隐式转换为包装对象：
	```javascript
	1.toString(); 
	// 上面这句由于解析器的BUG，所以这里为报SyntaxError。因为解析器会将点操作符当作浮点数字字面值的一部分来解析。
    // 要通过下面的方式来完成隐式转换为包装对象
	1..toString();
    (1).toString();
    1 .toString();
	```
>隐式使用过程如下：str为原始类型的string，当执行str.len = 3时会先创建一个string的包装对象，然后将3赋值给该包装对象的len属性，然后就会销毁该包装对象，所以当调用console.log(str.len)时，会再创建一个string的包装对象,然后获取该包装对象的len属性，而该新创建的包装对象并没有len属性，所以返回undefined。

	2.2. Date对象类型
	```javascript
	// 获取后端封装的json格式的日期数据
	var dummyData = { "createTime": "\/Date(1391141532000)\/" }; // Date内的数字表示距离1970年1月1日的毫秒数
	var millisecond = parseInt(dummyData.createTime.replace(/\D/igm, ""));
	var date = new Date(millisecond);
	```
	2.3. Array对象类型
	```javascript
	// 构造函数初始化（比较少用）
	var arr1 = new Array(1,2,3); // 当有多个入参时，就是数组的内容；arr1 == [1,2,3]
	var arr2 = new Array(3); // 当只有一个入参时，就是设置数组的长度；arr2 == [], arr2.length为3

	// 字面量初始化（常用）
	var arr3 = [1,2,3];

	// push：向数组追加元素
	arr3.push(4); // arr3 == [1,2,3,4]
	arr3.push(5,6,7); // arr3 == [1,2,3,4,5,6,7]
	
	// pop：访问数组最后一位元素，并删除该元素
	var last = arr3.pop(); // last == 7, arr3 == [1,2,3,4,5,6]

	// unshift：向数组头部插入元素
	arr3.unshift(0); // arr3 == [0,1,2,3,4,5,6]
	arr3.unshift(-2,-1); // arr3 == [-2,-1,0,1,2,3,4,5,6]

	// shift：访问数组第一个元素，并删除该元素
	var first = arr3.shift(); // first == -2, arr3 == [-1,0,1,2,3,4,5,6]

	// sort：排序,修改数组自身
	arr3.sort(function(a, b){
		// 返回-1、0或1
		// 若a在b前，则返回小于零的数字
		// 若a等于b，则返回等于零的数字
		// 若a在b后，则返回大于零的数字
	});

	// reduce和reduceRight
	// reduce会从左到右遍历数组，reduceRight会从右到左遍历数组
	数组.reduce(function(previousVal, currentVal, index, array){}, inintialVal);
	previousVal是上一个回调函数的返回值，currentVal是数组当前元素，index是数组当前元素的索引，array是数组本身，inintialVal是previousVal初始值
	var objArr = [{name: 'John'}, {name: 'Mary'}];
	var rs = objArr.reduce(function(p, c, i, a){
		return p + ' ' + c.name;
	}, ''); // rs == 'John Mary';

	// slice：1. 数组的浅复制；2. 将类数组转换为数组。
	// 接受两个参数，第一个参数是起始索引，第二个参数是结束索引。
	```
	**有洞数组**
	1. 空数组。空数组调用`filter`、`map`等方法时会出现一个方法跳过空项，另一个方法处理空项的情况
	```javascript
	var a = [,,,,];
	console.log(a.length); // 输出4，因为浏览器(除了ie678外)会自动去除最后一个空项
	a = new Array(5);
	console.log(a.length); // 输出5
	```
	2. undefined元素数组。调用`filter`、`map`等方法时行为一致，都处理undefined元素
	```javascript
	var a = Array.apply(null, new Array(5));
	console.log(a.length); // 输出5
	```

	2.4.String对象类型
	```javascript
	// replace：替换内容
	var str = 'john huang john';
	var rs1 = str.replace('john', 'yaya'); // rs1为yaya huang john
	var rs2 = str.replace(/john/g, 'yaya'); // rs2为yaya huang yaya
	var rs3 = str.replace(/john/g, function(match, index, input){
		// match是匹配成功的字符串, 这里就是john
		// index是匹配成功的字符串的索引
		// input是原来字符串，这里就是john huang john
		return 'yaya';
	}); // rs2为yaya huang yaya

	xss攻击的用法
	将原来的
	<script>somevariableUnifiltered=""</script>
	改成
	<script>somevariableUnifiltered="alert(1)".replace(/.+/, eval)//"</script>
	即可随心所欲执行任何js脚本了
	```
	String是类数组类型，在ie67下不能用下标来访问数组中的字符（如'1'[0]将返回undefined），在ie8或以上才能这样访问（'1'[0]将返回'1'）

	2.5. Object对象，其键只支持字符串，其他数据类型的会自动转为String类型后才作为键使用。
	```javascript
		// 以undefined、false等保留字作为键
		var map = {};
		map[undefined] = 1;
		console.log(map[undefined]); // 输出1
		console.log(map.undefined); // ie678会抛异常，ie9及以上（支持ES5）的会输出1
		map[false] = 2;
		console.log(map[false]); // 输出2
		console.log(map.false); // ie678会抛异常，ie9及以上（支持ES5）的会输出2
		
		// 对象作为键
		map[{}] = 3;
		console.log(map[{}]); // 输出3
		console.log(map.{}); // 所有浏览器均抛异常
		map[{x:1}] = 4;
		console.log(map[{}]); // 输出4，因为使用对象作为键时，会自动调用toString()得到[object Object]字符串，因此实际上是用[object Object]字符串作为键来保存数据。对于false、undefined均会进行类型变换。
	```

### 字面值（literal） ###
1. 不是通过构造函数创建的实例，如'string',true,1,2.1,{name: 'john', age: 1},[1,2,'a'],null, undefined。
2. 字面量赋值：
```javascript
var a = {name: 'john'};
```
3. 操作字面量：
```javascript
'string'.split(''); // 操作字面量
var name = 'john'; // 字面量赋值
name.split(''); // 操作变量，而不是操作字面量
```

### toString和valueOf方法 ###
1. 对象类型转换为字符串：如果对象有toString方法，则先调用toString方法，若返回基本类型值则js会将基本类型转换字符串在返回；若没有toString方法，或toString返回非基本类型值，则调用对象的valueOf方法，若返回基本类型值则js会将基本类型转换字符串再返回；若没有valueOf方法，或valueOf返回非基本类型值,则报错误。
2. 对象类型转换为数字：如果对象有valueOf方法，则先调用valueOf方法，若返回基本类型值则js会将基本类型转换数字在返回；若没有valueOf方法，或valueOf返回非基本类型值，则调用对象的toString方法，若返回基本类型值则js会将基本类型转换数字再返回；若没有toString方法，或toString返回非基本类型值,则报错误。

### 类数组 ###
特征：
1. 拥有length属性；
2. 通过索引来访问元素;

实例：
1. 函数内部的`arguments`变量
2. 通过`document.getElementsByTagName`和`document.getElementsByName`等获得的DOM集合（IE下为`HTMLCollection`类型，Chrome下为`NodeList`类型）
3. 通过`DOM对象.childNodes`获取的`NodeList`类型集合
4. 通过`DOM对象.attributes`获取的`NamedNodeMap`类型集合
5. 字符串
6. `{'0':1,'1':2, length: 2}`的对象

类数组转换为数组
```javascript
	var likeArray = {'0':1,'1':2, length: 2};
	var array = Array.prototype.slice.call(likeArray); // array == [1,2] 
```

### 逗号运算符 ###
逗号两边必须是*表达式*，从左至右每个表达式均进行计算，然后返回最后一个表达式的运算结果。
```javascript
var a = (b = 1, c = 2, d = 3);
console.log(a); // 显示3
console.log(b); // 显示1
console.log(c); // 显示2
console.log(d); // 显示3
```
上述代码片段执行过程就是将1赋值给b，将2赋值给c，将3赋值给d，然后将d赋值给a。其中b,c,d均为window属性。
```javascript
var a = (var b = 1, 2);
console.log(a);
console.log(b);
```
上述代码片段将报SyntaxError: unexpected token var。因为var操作符只能出现在函数预编译阶段，而(var b = 1, 2)是函数的执行阶段运行的。
```javascript
var a = 1, b = 2, c = 3, d = 4;
```
上述代码片段执行过程就是将1赋值给b，将2赋值给c，将3赋值给d，然后将d赋值给a。其中a,b,c,d均为变量。

### 标记语句(label statement) ###
用于与break和continue结合，控制流程。
注意：1.若出现重复的标记，则报SyntaxError；2.先声明后使用。
```javascript
// 一般用法
function test(){
  outerLoop:{
	for(var i = 0, len = 10; i < len; ++i){
		innerLoop:{
			for(var j = 0, jLen = 5; j < jLen; ++j){
				if (j === 3) continue innerLoop; // 当j为3时，就跳过本次的内部循环
				if (j === 5) continue outerLoop; // 当j为5时，就跳过本次的外部循环
			}
		}
	}
  }
}

// 特殊用法
a:1; // 返回1,并声明a这个标记
{b:2}; //返回2,并声明b这个标记
{c:3} //返回3,并声明c这个标记
```

### 语句优先原则 ###
1. 首先要理解语句和表达式的区别：
	1. 语句：
	2. 表达式：有数字、运算符、数字分组符号（如括号）、自由变量和约束变量等以能求得数值的有意义排列方式所得的组合。约束变量就是已被指定数值；自由变量就是在表达式外另行赋值的。一个表达式代表一个函数，其输入为自由变量的定值，输出是为表达式所产生的数值。（wiki解释）
  对应javascript中就有以下几种表达式：
1.1. 原始表达式
```javascript
1; // 常量
a; // 变量，a在之前已经声明了
typeof 1; // 保留字
```
1.2. 赋值表达式
```javascript
var obj = {a:1, b:2};
var arr = [1,2,3];
var num = 1;
```
1.3. 函数定义表达式
```javascript
var func = function(){};
```
1.4. 属性访问表达式
```javascript
Math.abs;
```
1.5. 函数调用表达式
```javascript
alert('hello world!');
```
1.6. 对象创建表达式
```javascript
new Object();
```
2. 当没有明确该语句是表达式时，解析器会以语句的形式对其进行解析并执行。如：
```javascript
{a:1}; // 将当作语句解析为标记语句a:1
{a:1,2}; // 将当作语句解析为标记语句a:2
{}.toString(); // 报语法错误
({}.toString()); // 正常运行
```

### 分组运算符 ###
()是分组运算符，其内部的将作为表达式来解析执行。
```javascript
// 典型用法1：使用eval反序列化JSON字符串
var json1 = '{name: "john"}'; // 非标准JSON字符串
var obj1 = eval('(' + json1 + ')');

var json2 = '{"name": "john"}'; // 标准JSON字符串
var obj2 = eval('(' + json2 + ')');
```
1. 若不加上分组运算符，'{name: "john"}'会被当作语句来执行，相当于name: "John"(标签为name的标签语句),返回值为"john"；而加了分组运算符就会当作表达式执行，'{name: "john"}'就是表示对象字面量。
2. 若不加上分组运算符，'{"name": "john"}'会被当作语句来执行，会报SyntaxError:Unexpected token :的错误，因为解析为"name": "john"并不符合标签语句的语法，则抛错误；而加了分组运算符就会当作表达式执行，'{"name": "john"}'就是表示对象字面量。

```javascript
// 典型用法2：立即执行语句
(function(){//...函数内部语句})();
```
&nbsp;&nbsp;立即执行语句就是通过分组运算符使得内部的函数定义以表达式的形式进行解析执行，并返回函数对象本身，后紧接的()就是发起函数调用。相当于
```javascript
var func = function(){//...函数内部语句};
func();
```

### for..in ###
遍历对象自身和原型链上的所有可遍历的属性（即特性`enumerable`为`true`的属性）


### delete的用法 ###
1. delete用于删除对象的属性
```javascript
globalProp = 1;
delete globalProp;
console.log(globalProp); 
```
上述代码片段会抛出ReferenceError:globalProp is not defined。因为不使用var声明的全局变量实际就是window的属性，因此delete操作符就将window的globalProp删除了。
```javascript
var variable = 1;
delete variable;
console.log(variable);
```
上述代码在控制台正常显示1。因为使用var操作符表示变量声明（不管是全局还是局部变量），而delete对变量是不起效果的。
```javascript
function test(){
  delete arguments;
  return arguments[0];
}
console.log(test(1,2));
```
上述代码在控制台正常显示1。因为函数内部的arguments为变量，而不是属性，所以删除失败。
2. 删除对象属性必须用delete，而不是设置为null或undefined
```javascript
var obj = { name: "john", sex: true, age: 1 };
delete name;
obj.sex = null;
obj.age = undefined;
for(var p in obj){
	console.log(p); // 输出sex和age
}
```
3. 删除数组元素时不用delete
```javascript
var arr = [1,2,3];
delete arr[0];
arr[1] = null;
arr[2] = undefined;
for(var i, len = arr.length; i < len; ++i){
	console.log(arr[i]); // 输出undefined,null,undefined 
}
console.log(arr.length); // 输出3
```
4. 不能删除如`Function.prototype`、`
5. .prototype`等内置属性。


### switch中的强类型 ###
  js是弱语言类型，但在switch的case中是强类型。
```javascript
var cond = '1';
switch(cond){
	case 1:
		// 不经过
		break;
	case '1':
		// 经过
		break;
}
```

## 函数 ##
### 函数的定义方式 ###
1. 函数声明
函数声明在预编译阶段被解析，称为hoisted
```javascript
function func(){
//...............
}
```
1.1.  该方式定义函数时，就没有先声明后调用的规定了。
2. <font style="color:red;">**函数声明与变量声明的关系**</font>
```javascript
	function x(){
		alert('fn');
	}
	var x;
	typeof x; // 输出function
```
因为在实例化执行上下文对象时，先对变量定义执行实现，然后才对函数定义执行实现，所以上面的`x`为函数；
```javascript
	function x(){
		alert('fn');
	}
	var x = 12;
	typeof x; // 输出function
```
因为`var x = 12;`执行了变量的初始化，所以`x`指向存放12的栈空间，类型为`Numeric`。
2. 函数表达式
函数表达式在执行阶段被解析并运行
```javascript
// 匿名函数表达式
var func1 = function(){};
// 命名函数表达式
var func2 = function fn(){
	return typeof fn;
};
// 特殊形式表达式
(function fn(){
	return typeof fn;
});
```
2.1. 将一个匿名函数赋值给func1变量
2.2. 将一个fn的命名函数赋值给func2变量
>命名函数表达式会在一个指向自身的变量添加到执行上下文的variable
>中，供函数内部来调用。

 2.3. 通过分组运算符强制以表达式的方式解析并执行内部的函数定义

### 函数的调用方式 ###
1. 构造函数
使用new关键字调用函数
```javascript
function Obj(){ this.name = 'john'; }
var obj = new Obj();
obj instanceof Obj; // 返回true
obj.name === 'john'; // 返回true
```
```javascript
	// 防止写少new运算符
	var Obj = function(arg1, arg2){
		if (!(this instanceof Obj))
			return new Obj(arg1, arg2);
		this.arg1 = arg1;
		this.arg2 = arg2;
	};
	var obj1 = new Obj(1,2);
	var obj2 = Obj(1,2);
```
>使用构造函数方式创建对象时，函数体内的this指向函数实例。
>使用构造函数方式创建对象时，若函数返回值为this、原始数据类型或没有返回值时，则返回函数实例；若返回其他类型的对象时，就返回该对象。
2. 函数调用
```javascript
	var name = 'john';
	function test(){
		return this.name;
	}
	test(); //　函数调用，返回john
```
>this指向全局对象window
3. 方法调用
```javascript
	var name = 'global';
	var obj = {
		name: 'john',
		test: function(){
			return this.name;
		}
	};
	obj.test(); //　方法调用，返回john
```
>this指向方法所属对象本身
4. Function.prototype.apply、Function.prototype.call和Function.prototype.bind方式调用
4.1. Function.prototype.apply(this, {Array|ArrayLike})：函数调用时，绑定this
```javascript
	注意：
	Function.prototype.apply函数内的this应该为某函数指针，表示是调用某个函数A上的apply函数。
	而入参(this,[])为某个函数A内部执行上下文的this指针
```
4.2. Function.prototype.call(this, arg1[,arg2...])：函数调用时，绑定this
```javascript
	注意：
	Function.prototype.call函数内的this应该为某函数指针，表示是调用某个函数A上的call函数。
	而入参(this,arg1[,arg2...])为某个函数A内部执行上下文的this指针
```
4.3. Function.prototype.bind(this)：返回绑定了this的同功能函数,ie9以上支持
```javascript
	var ctx = { name: 'John' };
	var func = function(){
		return this.name;
	};
	func(); // 返回undefined;
	var funcWithBind = func.bind(ctx);
	funcWithBind(); // 返回John
	funcWithBind.call(window); // 返回John
	funcWithBind.apply(window); // 返回John 

	注意：
	var fnCall = Function.prototype.call.bind(func);
	fnCall({name: 'call'}); // 返回call，效果和直接调用func.call(name: 'call'})一样。

	var fnApply = Function.prototype.apply.bind(func);
	fnApply({name: 'apply'}); // 返回apply，效果和直接调用func.apply({name: 'apply'})一样。
```
```javascript
	// shim
	if (!Function.prototype.bind){
		Function.prototype.bind = function(context){
			var fn = this, args = Array.prototype.slice.call(arguments, 1);
			return function(){
				return fn.apply(context, args.concat(Array.prototype.slice.call(arguments));
			};
		};
	}
```

### 函数的arguments ###
1. arguments是一个含有length和索引项属性的对象，不是数组类型，所以没有slice，push等属性和方法。可通过下列方式转换为数组
```javascript
	// 性能有点低
	function test(){
		var arr = Array.prototype.slice.call(arguments);
	}
```
2. 取消内联函数
```javascript
	function test(i){
		console.log(i);
	}
	for(var i = 0, len = 10; i < len; ++i){
		test(i); // 解析器会将该句用console.log(i)；来代替，从而提高性能
	}
```
而使用arguments.callee（获取方法本身）和arguments.caller（获取方法的调用者）时就会拒绝解析器使用内联方式来解析，从而降低性能。加上这样使得方法依赖于某个特定的环境，形成紧耦合。在ES5中已经废弃arguments.callee方法。

### 函数的length ###
1. 可通过函数的length属性获取函数的形参个数
```javascript
	var doStuff1 = function(a){
	};
	var doStuff2 = function(a, b){
	};
	console.log(doStuff1.length); // 输出1
	console.log(doStuff2.length); // 输出2
```
2. 通过函数的length来实现函数重载
```javascript
	var overload = function(context, name, fn){
		var old = context[name];
		context[name] = funciton(){
			if (arguments.length === fn.length){
				fn.apply(this, arguments);
			}
			else if (typeof old === 'function'){
				old.apply(this, arguments);
			}
		};
	};
	overload(window, 'doStuff', function(a){
		console.log(1 + a);
	});
	overload(window, 'doStuff', function(a, b){
		console.log(2 + a + b);
	});
	doStuff(1);
	doStuff(1, 2);
```

### 原型链 ###
1. 每个函数都有一个`prototype`属性，该属性指向一个名为*原型对象*，而*原型对象*含`constructor`属性指向函数本身。默认情况下原型对象为Object实例，而该Object实例的`__proto__`属性指向一个另一个`__proto__`属性为null的Object实例。
```javascript
	function Test(){}
	Test.prototype.constructor === Test; // 返回true	
	Test.prototype instanceof Object; // 返回true

	// Test的原型链结构
	Test[Test函数]
		Test.prototype[默认的Object实例]
			Test.prototype.__proto__[Object实例]
				Test.prototype.__proto__.__proto__[null]
```
2. 当以构造函数的方式调用函数时，创建的实例会默认隐式创建一个隐式属性`__proto__`指向*原型对象*。虽然可直接访问`__proto__`，但由于它为内部属性，所以编码时不能依赖它。
```javascript
function test(){}
var instance = new test();
instance.__proto__ === test.prototype; // 返回true
```
3. 自定义原型对象
```javascript
	function Test(){}
	Test.prototype = {
		name: 'custProto',
		constructor: Test
	};
	var test = new Test();
	console.log(test.name); // 输出custProto

	// Test的原型链结构
	Test[Test函数]
		Test.prototype[{name: 'custProto', constructor: Test}]
		Test.prototype.__proto__[默认Object实例]
			Test.prototype.__proto__.__proto__[Object实例]
				Test.prototype.__proto__.__proto__.__proto__[null]
```
4. 原型链和原型对象
原型链就是一个个原型对象串联而成，通过构造函数方式创建的函数实例共享同一个原型链。
	4.1. 对象属性和方法的访问流程
	&emsp;当访问对象的属性和方法时，首先在对象本身上搜索同名属性和方法，若搜索失败，则搜索其`__proto__`的同名属性和方法，如此类推，直到搜索成功或`__proto__`为null为止。
	&emsp;所以访问不存在的属性和方法时非常耗时的操作。
	4.2. 修改原型对象影响相关的所有函数实例
```javascript
	function Test(){}
	Test.prototype.name = 'John';
	var test1 = new Test();
	var test2 = new Test();
	console.log(test1.name); // 输出John
	console.log(test2.name); // 输出John

	Test.prototype.name = 'yaya';
	console.log(test1.name); // 输出yaya
	console.log(test2.name); // 输出yaya
```
5. 继承
使用原型对象和原型链实现继承
```javascript
	// 推荐的方式
    // 父类
	function Parent(){
		this.name = 'Parent';
		this.age = '32';
		this.position = 'PM';
	}
	Parent.prototype.getName = function(){ return this.name; };
	Parent.prototype.getPosition = function(){ return this.position; };

	// 继承操作连接类：用于避免修改Parent函数实例的constructor属性
	function SonProto(){
		Parent.call(this); // 继承父类属性
		this.constructor = Son;
	}
	SonProto.prototype = Parent.prototype; // 继承父类方法
	
	// 子类
	function Son(){
		this.name = 'Son'; // 子类属性
		this.age = 12;
	}
	Son.prototype = new SonProto();
	Son.prototype.getAge = function(){ return this.age; }; // 子类方法

	var son = new Son();
	console.log(son.getName()); // 输出Son
	console.log(son.getAge()); // 输出12
	console.log(son.getPosition()); // 输出PM

	// Son的原型链结构
	Son[Son函数]
		Son.prototype[{ constructor: Son }]
		Son.prototype.__proto__[Parent函数实例的默认Object实例]
			Son.prototype.__proto__.__proto__[null]
```
	5.1. 子类的属性和方法会覆盖父类的同名属性和方法
	5.2. 原型对象的方法内部`this`变量始终指向初始对象`son`本身
	5.3. 构造函数和对象的属性会覆盖原型中的同名属性
```javascript
	// 另一种类实现方式
    // 父类
	function Parent(){
		this.name = 'Parent';
		this.age = '32';
		this.position = 'PM';
	}
	Parent.prototype.getName = function(){ return this.name; };
	Parent.prototype.getPosition = function(){ return this.position; };
	
	// 子类
	function Son(){
		this.name = 'Son';
		this.age = 12;
	}
	Son.prototype = new Parent();
	Son.prototype.constructor = Son; // Parent函数实例的constructor被修改为指向Son函数
	Son.prototype.getAge = function(){ return this.age; };

	var son = new Son();
	console.log(son.getName()); // 输出Son
	console.log(son.getAge()); // 输出12
	console.log(son.getPosition()); // 输出PM
```
6. in操作符
用于判断对象是否含有同名的属性和方法
```javascript
var obj = { name: 'john' };
console.log('name' in obj); // 输出true
console.log('age' in obj); // 输出false
```
`in`操作符会遍历对象的原型链来进行判断，若整条原型链遍历完毕后，仍没有同名属性或方法时才返回false。
7. for...in语句
用于遍历对象及其原型链上所有属性特性enumerable为true的属性和方法
```javascript
	var obj = { name: 'john' };
	for(var p in obj){
		console.log(p);
	}
```
8. Object.prototype.hasOwnProperty函数
用于判断该属性名或方法名是否为对象本身的属性和方法，而不是原型链上的同名属性和方法。返回值类型为Boolean
```javascript
	var obj = { name: 'john' };
	for(var p in obj){
		if (obj.hasOwnProperty(p)){
			console.log(p);
		}
	}
```


### parseInt的使用 ###
**作用：**将字符串转换为十进制数字
*入参1*：字符串
*入参2*：字符串使用的进制（2-36）
1. 当仅有一个入参时，0开头表示字符串使用8进制，0x开头表示字符串使用16进制，其他表示使用10进制，若以非数字开头则返回NaN。

### void运算符 ###
**作用：**后跟一个运算数或表达式的运算符，计算结果永远是`undefined`。无论是`void 0`、`void 1`、`void true`、`void 'test'`还是`void function(){}()`均返回`undefined`。一般用法`void 0`

### 书签应用 ###
```
	<a href="javascript:void function(){alert('bookmarklet');}()">拖放保存成书签，在其他页面点击书签时会自动执行函数</a>
```
1. `<a></a>`标签的`href`属性填写的不是`http`和`https`协议地址，而是`javascript`协议内容，由js作者Brendan Eich引入的，表示超链接用于执行某个脚本。
2. IE6下只支持内容长度为508以下的书签，而其他浏览器则支持长度为2000以下的书签，不过可以在书签脚本中动态插入`<script src="其他即时执行的脚本的URL"></script>`来突破书签内容长度的限制。
3. 书签应用容易引入XSS攻击。

### setTimeout,setInterval ###
若第一个入参是字符串，则字符串执行的scope为全局对象window
```javascript
	function doStuff(text){
		setTimeout('alert(text);', 1000);
	}
	doStuff('hello world'); // 删除undefined
```


### instanceof ###
用于判断对象的原型链上有没有指向某函数的原型对象。
```javascript
	function Parent(){}
	function Son(){}
	var son = new Son();
	son instanceof Parent; // 返回false

	Son.prototype = new Parent();
	son instanceof Parent; // 返回true
	son.__proto__ instanceof Parent; // 返回true
	son.__proto__.proto__ instanceof Parent; // 返回false
```

### isNaN函数 ###
**作用：**判断入参是否为非数字类型，true表示非数字类型，false表示数字类型。
```javascript
	isNaN(1); // 返回false
	isNaN('01'); // 返回false
	isNaN('0x1'); // 返回false
	isNaN('john'); // 返回true
	isNaN(NaN); // 返回true
```

### isFinite函数 ###
**作用：**判断入参是否为无穷大值，true表示为有限制，false表示为无穷大值
```javascript
	isFinite(NaN); // 返回false
	isFinite(-Infinity); // 返回false
	isFinite(Infinity); // 返回false
	isFinite('john'); // 返回false
	isFinite(1); // 返回true
```

### throw操作符 ###
```javascript
	try{
		throw errorMsg; // errorMsg可以为任何数据类型的变量
	}
	catch(e){
		// e就是errorMsg
	}	
```

### Error对象 ###
内置的Error类型有
`EvalError`：错误发生在eval中
`SyntaxError`：语法错误，因为js是动态语言，所以语法错误也要等到执行到该语句时才能发现
```javascript
	+alert(1)--;
	//因为alert(1)返回undefined，而--操作符的操作数必须是一个数字，所以在执行完alert(1)后才会抛出SyntaxError。
```
`RangeError`：数值超出范围
`ReferenceError`：引用不可用
`TypeError`：变量类型不是预期的
`URIError`：错误发生在encodeURI()或decodeURI()中

### js运行模式 ###
1. 正常模式
2. 严格模式（IE10+支持）
2.1. 目的
>消除js语法的不合理和怪异行为；
>消除代码的不安全因素；
>提高编译器效率；
>为新版本的js做铺垫。

2.2. 调用方式
```javascript
	// 1. 针对整个脚本文件
	// file.js
	"use strict"; // 必须放在文件的首行
	function doStuff(){}

	// 2. 针对单个函数
	// file.js
	function doStuff(){
		"use strict";
	}
```

2.3. 特性
>1. 不允许通过形如`a = 1`的方式定义全局变量
>2. 不允许使用`with`语句
>3. 作用域除了正常模式的全局作用域和函数作用域外，还增加了eval作用域
```javascript
	"use strict";
	var x = 2;
	console.log(eval("var x = 3; x;")); // 3
	console.log(x); // 2
```
>4. 禁用this关键字指向全局对象
```javascript
	"use strict";
	function doStuff(){
		console.log(this); // 输出undefined
	}
```
>5. 在函数体内遍历调用栈会抛异常
```javascript
	function f(){
		"use strict";
		// 下列操作均禁止
		// f.caller;
		// f.callee;
		// f.arguments;
		// arguments.caller;
		// arguments.callee;
	}
```
>6. 删除configurable为false的属性会抛异常
```javascript
	"use strict";
	var o = {};
	Object.defineProperty(o, 'x', { value: 1, configurable: false });
	console.log(o.b); // 输出1
	delete o.b; // 抛出异常
```
>7. 删除内置属性会抛异常
```javascript
	"use strict"
	delete Function.prototype;
```
>8. 修改只读属性会抛异常
```javascript
	"use strict";
	var a = {};
	Object.defineProperty(a, 'x', { value: 1, writable: false });
	a.x = 2; // 抛异常
	
	var b = {
		get x() {return 1;}
	};
	b.x = 2; // 抛异常
```
>9. 对禁止扩展的对象添加新属性会抛异常
```javascript
	"use strict";
	var o = {};
	Object.preventExtensions(o);
	o.a = 1; // 抛异常
```
>10. 对象属性不能重名
>11. 函数形参不能重名
>12. 禁止使用八进制表示法的数字
```javascript
	"use strict";
	var n = 010; // 抛异常，在正常模式下n为8
```
>13. 不追踪arguments的变化
```javascript
	// 正常模式下
	(function normal(a){
		console.log(arguments[0]); // 输出1
		a = 2;
		console.log(arguments[0]); // 输出2
	})(1);
	
	// 严格模式下
	(function normal(a){
		"use strict";
		console.log(arguments[0]); // 输出1
		a = 2;
		console.log(arguments[0]); // 输出1
	})(1);
```
>14. 函数声明必须放在顶层
```javascript
	"use strict";
	if (true){
		function doStuff(){} // 抛异常
	}

	// 放在顶层
	function doStuff(){}
```
>15. 新增了以下的保留字
`implements`,`interface`,`let`,`package`,`private`,`protected`,`public`,`static`,`yield`,`class`,`enum`,`export`,`import`,`super`

### Shim ###
```javascript
	Array.prototype.forEach = function(fn, context){
		for (var i = 0, len = this.length; i < len; ++i){
			fn.call(context, this[i], i, this);
		}
	};

	Object.prototype.keys = function(){
		var keys = [];
		for (key in this){
			keys.push(key);
		}
		return keys;
	}
```

### 异步编程Promise模式 ###
**适用的场景：**运行时间长并且禁止阻塞操作
promise表示为一个程序执行的承诺
promise模式含三种状态：未完成（unfulfilled）、已完成（resolved）和拒绝（rejected）。
*程序执行过程：*未执行，执行正常流程，执行异常流程。而promise状态中的已完成就会触发`执行正常流程`，拒绝会触发`执行异常流程`。