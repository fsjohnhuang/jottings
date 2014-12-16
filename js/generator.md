## 迭代器
1. FF独有的迭代器`Iterator`<br/>
````
var parent = function(){
	this.a = 1
	this.b = 2
}
parent.prototype.p = 3
var orig = new parent()
var iterator = Iterator(orig)
// 不会显示原型链上的属性
for (var item in iterator){
	console.log(item.toSource())
	// 控制台显示
	// ["a", 1]
	// ["b", 2]
}
````
2. 自定义迭代器<br/>
````
var iterable = function(orig){
	var target = orig
	if (Object.prototype.toString.call(target) === '[object Object]'){
		target = []
		for (var p in orig) if (!orig.hasOwnProperty || orig.hasOwnProperty(p))
			target.push([p,orig[p]])
	}
	var i = -1, len = target.length
	var iterator = {
		next: function(){
			if (i > len) throw new Error('StopIteration')

			var item = target[++i]
			if (Object.prototype.toString.call(target) === '[object Array]')
				return item
			return [i, item]
		}
	}
}
````
3. 实现Python中的range函数，在FF下<br/>
````
var RangeIterator = function(start,end,scan){
	this.start = arguments.length >= 2 ? start : 0	
	this.end = end == undefined ? start : end
	this.scan = scan || 1
	this.idx = this.start
}
RangeIterator.prototype.next = function(){
	if (this.idx > this.end) throw StopIteration

	var ret = this.idx
	this.idx += this.scan
	return ret
}
var range = function(start, end, scan){
	return {
		__iterator__: function(){
			return new RangeIterator(start, end, scan)
		},
		toString: function(){
			var array = []
			for (var p in this) if(typeof p === 'number')
				array.push(p)
			return array + ''
		}	
	}
}
var r = range(3, 5);
for (var i in r)
	console.log(i)
````

ES6的迭代器(Generator), 不是一种新语法或内置对象，而是一种协议。只要拥有next函数，并该函数返回`{value:val, done:true/false}`就是迭代器<br/>
1. 自定义迭代器生成器<br/>
````
var iterable = function(orig){
	var i = -1, len = orig.length
		,iterator = {
		next: function(){
			return ++i < len ? {
					value: orig[i],
					done: false 
				} : {
					done: true
				}
		}
	}

	return iterator
}	
````


## 生成器(ES6的GeneratorFunction)
````
function * test(src){
    // 第一次调用next执行的部分
	console.log(src)
	var data = yield getData(src)
	// 第二次调用next执行的部分
	var tpl = yield getTpl(data.tpl)
	render(tpl, data)
}
var iterator = test('dummy.json') // 或 new test('dummy.json')
iterator.next();
//  控制台显示 dummy.json，然后进入getData(src)函数
// 当getData函数内调用iterator.next(arg)，然后将arg赋值给data，然后开始执行var tpl = yield getTpl(data.tpl)，继续上述步骤。
````

## C# 迭代器
## Java 迭代器