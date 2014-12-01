异步编程的三大方法（回调，事件监听，发布/订阅）。<Br/>
Promise是CommonJS工作组制定的一种机制，为异步编程提供统一接口。<br/>
Promise的状态：等待(pending)、完成(fulfilled)和否定(rejected)<br/>

ES6的new Promise API, jQuery的$.deferred()

## Promise/A规范
A promise is defined as an object that has a function as the value for property then: then(fulfilledHandler, errorHandler, progressHandler)<Br/>
then函数会返回一个新的promise对象<Br/>
但调用then函数时，promise的状态不是unfulfilled或pending时，则马上执行回到函数。<br/>
Promise有三种状态：pending,fulfilled,rejected。<br/>
状态转换：<br/>
pending->fulfilled<Br/>
unfulfilled->rejected<Br/>

对fulfilledHandler、errorHandler和progeressHandler的描述:<br/>
This function should return a new promise thas is fulfilled when the given fulfilledHandler or errorHandler callback is finished.This allows promise operation to be chained together.The vlaue returned from the callback handler is the fulfillment value for the returned promise.If the callback throws an error,the returned promise will be moved to failed state.<br/>
1. fulfilledHandler、errorHandler均要返回一个promise对象，并且该对象的状态为非unfulfilled。<br/>

通过监测回调函数所返回的promise对象的状态，触发下一个回调函数。<br/>
````
promise.then(sh1,eh1).then(sh2,eh2);
````
假如promise状态为failed，则会执行eh1，若eh1返回的promise状态为fulfilled则执行sh2。<br/>
````
promise.then(sh1).then(sh2,eh2);
````
假如promise状态为failed，则会执行eh2。<br/>
理论：回调函数的选择只根据最近一个promise状态而定。<br/>

若回调函数返回的不是promise，则直接调用下一组的fulfilledHandler<Br/>

定义：
`new Promise(fu(fulfill, reject))`, 构造函数<br/>
`实例方法done(onfulfilled, onrejected)`, promise链的终结<br/>
`实例方法then(onfulfilled, onrejected)`, 订阅promise实例状态变化事件<br/>
`静态方法Promise.resolve(val)`,若val为非promise实例，则转换为promise实例;若val为promise实例，则返回新promise实例，新promise的状态随val实例的变化而变化<br/>
````
var val = 10;
var promise4Val = Promise.resolve(val);
// 等价于下列代码
var promise4Val = new Promise(function(fulfill, reject){
  fulfill(val);
});

var thenable = {then:function(fulfill, reject){
  fulfill('hello');
}};
var promise4Thenable = Promise.resolve(thenable);
// 等价于下列代码
var promise4Thenable = new Promise(function(fulfill, reject){
  promise4Thenable.then(fulfill, reject);
});

var promise = new Promise();
var promise4Promise = Promise.resolve(promise);
// 等价于下列代码
var promise4Promise = new Promise(function(fulfill, reject){
  promise.then(fulfill, reject);
});
````
`静态方法Promise.reject(error)`,在异步模式下抛出同步模式的异常不是一个好的选择，所以通过该方法将异常封装为一个promise对象.<br/>
````
var promise4Error = Promise.reject(new Error('what'));
// 等价于下列代码
var promise4Error = new Promise(function(fulfill, reject){
  reject(new Error('what'));
});
````
`静态方法Promise.all([iterable])`
`静态方法Promise.race([iterable])`


Promise/A+规范

## 同步模式
1. 函数可以有返回值；<br/>
2. 一套统一的异常处理可对应一系列函数调用。<br/>

## 异步模式
1. 回调函数没有返回值;<br/>
2. 每个回调函数必须有自己一套独立的异常处理机制。<br/>

四种方法：<Br/>
1. 回调函数<br/>
假设有任务f1和f2，f2需要等待f1的执行结果而f1耗时较长，这时就可以通过回调函数的方式`f1(f2)`。<br/>
````
function f1(cb){
  setTimeout(function(){
	cb();
  }, 1000);
}
````
优点：简单、容易理解和部署<br/>
缺点：一个回调函数插槽仅能对应一个回调函数；不利于代码的阅读和维护，各部分之间高度耦合<br/>
2. 事件监听<Br/>
任务的执行顺序不取决于代码顺序，而是取决于对应的事件是否发生。<br/>
````
function f1(cb){
  setTimeout(function(){
	f1.trigger('done');
  }, 1000);
}
f1.on('done', f2)
````
优点：容易理解，同一事件（回调函数插槽）可指定多个回调函数，去耦合。<br/>
缺点：程序的执行流程变得模糊。<Br/>
3. 发布/订阅<br/>
称为“发布/订阅模式(pub/sub pattern)”或“观察者模式(observer pattern)”<br/>
整体上与事件监听相似，但比事件监听模式更灵活。<Br>
采用信号作为任务执行的触发标识，且有统一的信号心中管理信号信息及为系统各模块提供信号的发布/订阅的API
````
var SignalCenter = {
  pub: function(signal){},
  sub: function(signal, cb){}
};

SignalCenter.sub('done', module1.done);
module2.exec = function(){
  // do... 
  SignalCenter.pub('done');
};
````
由于信号订阅者不用理会信号发布者是谁，并且信号本身没有信号的拥有者信息，因此耦合度更低，但程序的执行流程就更模糊了。因此需要信号心中对信号的发布/订阅进行记录、监控<Br/>
而事件监听模式，事件（与信号对应的概念）本身带有拥有者的信息，而且订阅者需要明确发布者（即事件拥有者）才能进行监听。<Br/>

4. 流程控制库(flow control library)<br/>
`Async.js`<br/>
````
async.waterfall([
  function(cb){
    settimeout(function(){
      cb(null, 1);
    }, 1000);
  },
  function(records, cb){
    settimeout(function(){
      cb(null, 2);
    }, 1000);
  },
  function(records, cb){
     alert(records);
  }
]);
````
https://github.com/caolan/async
## 参考
http://jishu.zol.com.cn/210138.html
http://javascript.ruanyifeng.com/advanced/asynchronous.html
http://sporto.github.io/blog/2012/12/09/callbacks-listeners-promises/
http://mweb.baidu.com/p/promise-introduction.html

## ES6的Promise
Chrome32和FF25开始支持。默认未开启，要通过`dom.promise.enabled`开启<br/>
构造函数:`Promise(fn(resolve, reject))`<Br/>
`then(fulfilledHandler, rejectedHandler)`<br/>
`catch(rejectedHandler)`,对应状态转换，pending->rejected<br/>
错误具有冒泡性，一直到被捕获为止。<br/>
`resolve(val)`,触发状态转换，pending->fulfilled<br/>
静态方法`Promise.resovle(val)`若val不是thenable实例，则返回一个状体为fulfilled的新promise实例;若val为thenable实例，则将其转换为promise实例<br/>
`reject(val)`,触发状态转换，pending->rejected<br/>
静态方法`Promise.reject(val)`若val不是thenable实例，则返回一个状体为rejected的新promise实例<br/>
`all(iterable)`,iterable为一组promise对象或thenable对象，当且仅当所有promise对象均resovle时才触发then，有一个promise对象为reject则触发catch。<Br/>
静态方法`Promise.all(iterable)`返回新promise实例<br/>
`race(interable)`,iterable为一组promise对象或thenable对象,只要有一个promise对象resolve就触发。<br/>
`cast(reason)`,功能与`resolve(val)`类似。但将promise实例传入`resolve(val)`则会创建一个与之同步的新promise实例，且状态为fulfilled。而`cast(reason)`则返回入参的promise实例而已。<br/>

##ES7的async函数
但traceur编译器已经实现了。<br/>
````
function timeout(ms){
  return new Promise((resolve) => {
    setTimeout(resolve, ms);
  });
}
async function asyncValue(value){
  await timeout(50);
  return value;
}
````
通过async关键字标识该函数内部发生异步操作，而await关键字标识后面的表达式将返回promise实例，且不再执行后续语句并马上返回，直到promise实例状态转为非pending，该函数再次被执行并且从await的下一行语句开始执行。<br/>

## ES6 generator特性
开启方式：
1. 通过Node0.11开启--harmony flag<br/>
2. 通过Goolge的Traceur(https://github.com/google/traceur-compiler)或Facebook的regenerator(https://github.com/facebook/regenerator)将ES6代码编译成ES5代码。<br/>

## 参考
http://www.zhihu.com/question/25413141/answer/30767780
http://es6.ruanyifeng.com/#docs/promise


##setImmediate
`setTimeout`存在精度问题，不同的浏览器使用不同的精度。IE5.5~8为15.6ms，IE9+和Chrome等则为4ms，因此`setTimeout(fn,0)`的实际执行时间大于0ms。而且IE9+会判断笔记本插电时为4ms，使用电池时为15.6ms。<br/>
而`var id = setImmediate(fn/* [,arg]* */)`则表示UI线程空闲时马上执行该方法，并且返回的id可通过`clearImmediate(id)`清除该延迟操作。<br/>
IE10+开始支持`msSetImmediate`.<Br/>

pollyfill库：https://github.com/yuzujs/setimmediate

## 参考
http://msdn.microsoft.com/library/ie/hh920766.aspx

