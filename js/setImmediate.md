JavaScript的设计者Brendan Eich<Br/>

## 多任务处理的手段
1. 任务排队<Br/>
2. 新建进程，通过fork命令新建进程处理<br/>
3. 在同一进程中新建线程<br/>
## Event Loop(事件循环)
Wikipedia定义：<Br/>
Event Loop是一个程序结构，用于等待和发送消息和事件。<br/>
实现方式为:<br/>
进程中含两个线程，一个称为“主线程”负责程序本身的运行；另一个成为“Event Loop线程”负责主线程与其他进程（主要为各种I/O操作）的通信。<br/>


JavaScript执行环境（Runtime）：各种浏览器、Node、Ringo等<br/>
JavaScript执行引擎（Engine）：Node和Chrome的是V8，Safari的是JavaScript Core，FF的是SpiderMonkey。<br/>
Engine仅实现ECMAScript标准，不关心event loop。<br/>
而event loop是Runtime的运行机制，而不是JavaScript语言的运行机制。<br/>

**事件和回调函数**<Br/>
事件驱动，就是将一切抽象为事件。如IO操作完成为一个事件，用户点击鼠标是一个事件。<br/>
当事件被触发后，就会被放进事件队列中，等待被处理。<br/>
event loop在进程启动后开始执行，不等从事件队列中读取事件，对于出队的事件则检查是否有相应的事件处理函数（回调函数），有则调用。<br/>

setTimeout和setInterval产生事件的过程是，有event loop不停检查系统时间，若到达设置的时间则产生一个事件加入到事件队列的末尾。
而其他如鼠标点击的事件，则是由底层系统或线程池等产生事件并加入到事件队列的末尾。<br/>
不同的事件类型会对应不同的watcher（观察者）和事件队列，event loop会依次检查所有watcher并处理各watcher对应的事件队列上的事件，当处理完一个watcher的所有事件后，就处理下一个watcher。当处理完所有watcher后则进入下一个循环。<br/>
setTimeout、setInterval产生的事件将放入定时事件队列，IO操作的事件将放入IO事件队列。<br/>
那么watcher的顺序是？<br/>


操作DOM尤其是设计渲染时使用requestAnimationFrame比setTimeout更好<br/>

HTML5的Web Worker标准，允许JS创建多个线程，子线程完全受主线程控制，且不得操作DOM。<br/>

当进程正在处理IO操作时，操作系统多半自动将CPU切换到其他进程。<br/>
````
var worker = new Worker('my_task.js');
workder.onmessage = function(event){
	// This event handler will be called when the worker calls its own postMessage() function
};
worker.postMessage();// start the worker
worker.terminate(); // terminate a running worker
````
同步、异步?<Br/>
操作分为：发出调用和得到结果两步。<br/>
同步：发出调用之后一直等待，直到返回结果。<br/>
异步：发出调用之后不等待返回结果，而是通过一系列手段获取结果。在调用后和获取结果之间的时间段中可以介入其他任务。这一系列手段包括：event loop、轮询、事件等<br/>
阻塞、非阻塞?<Br/>

requestAnimationFrame
Memoization技术<Br/>
Duff's device(达夫设备)
````
/**
 * Duff’s Device
 * http://home.earthlink.net/~kendrasg/info/js_opt/jsOptMain.html#duffsdevice
 */
var n = iterations / 8;
var caseTest = iterations % 8;
do {
    switch (caseTest) {
        case 0:
            testVal++;
        case 7:
            testVal++;
        case 6:
            testVal++;
        case 5:
            testVal++;
        case 4:
            testVal++;
        case 3:
            testVal++;
        case 2:
            testVal++;
        case 1:
            testVal++;
    }
    caseTest = 0;
} while (--n > 0 );
````

##setImmediate
会监视UI线程的调用栈是否为空，若为空则将函数压栈。<br/>



## 参考
http://shenxueliang.iteye.com/blog/1677194
http://www.ruanyifeng.com/blog/2013/10/event_loop.html
http://www.ruanyifeng.com/blog/2014/10/event-loop.html
https://github.com/RubyLouvre/avalon/commit/4d601f27a527e8a0cba262f7a6242bd343933233
http://www.pjhome.net/article/Javascript/setImmediate_requestAnimationFrame.html
http://tid.tenpay.com/?p=2062
http://www.tuicool.com/articles/EfQbqy6
http://www.nczonline.net/blog/2011/09/19/script-yielding-with-setimmediate/
