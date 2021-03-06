﻿# Web Component
## HTML IMPORT
**作用:**在HTML文档中引入其他HTML文档对象<br/>
**使用方式：**通过链接类型`import`<br/>
具体示例<br/>
````
<link rel="import" href="other.html" async="true">
````
布尔属性`async`,true表示异步加载，false（默认值）表示等待该资源加载并渲染后再渲染后面的文档内容。<br/>
注意：`link标签`不受`media属性`所影响，始终会被获取及应用。<br/>
**HTMLLinkElement扩展**<br/>
Document? import属性,通过该属性获取被导入的文档对象。<br/>
import属性所指向的document对象，在调用open,write,close方法时会抛InvalidStateError，因为通过import方式导入的文档对象是不能调用这三个方法的。
**通过onload, onerror事件通知文档加载完成**<br/>

**注意：**
1. 多次引入同一个文档，该文档仅加载一次而已。<br/>
2. 在被引入的文档中调用document对象，该document对象指向主文档的document对象，而不是被引入的文档对象<br/>
3. 样式将根据导入的顺序执行<br/>
**参考**<br/>
http://www.tudou.com/listplay/r0pA0z77CgM/9UZ7gHj8fuM.html

## Custom Elements
作用：定义个标签，和Extjs等UI框架的组件功能相似。
标签、属性、事件
自定义UI在通过js动态创建时会缺少UI事件，要另外再封装一个初始化函数来初始化UI组件事件。
1. 标签名必须含连字符


生命周期<br/>
自定义元素 被创建早于定义被注册<br/>
自定义元素 的定义被注册<br/>
自定义元素 定义被注册后才被创建<br/>
自定义元素 被插入文档<br/>
自定义元素 从当前文档移除<br/>
自定义元素 的属性被创建、移除和修改<br/>
生命周期的各个阶段均有对应的回调函数，称为生命周期回调函数。<br/>
自定义元素  定义被注册后才被创建——createdCallback
自定义元素 被插入文档——attachedCallback
自定义元素 从当前文档移除——detachedCallback
自定义元素 的属性被创建、移除和修改——attributeChangedCallback


定义被注册
被创建

````
var proto = Object.create(HTMLElement.prototype, {
  shown: {
    get: function(){
      return this.querySelector('menu').style.display === 'block';
    },
    set: function(v){
      this.querySelector('menu').style.display = v ? 'block' : 'none';
    }
  },
  attachedCallback: {
    value: function(){
      var self = this;
      this.querySelector('button').onclick = function(){
 	self.shown =!self.shown;
      };
      this.querySelector('menu').onclick = function(){
 	self.shown = false;
      };
    }
  }
});
// 定义被注册
document.registerElement('dropdown-menu', { prototype: proto });
````






**参考**<br/>
http://www.tudou.com/listplay/r0pA0z77CgM/F7oHc5GVG08.html

