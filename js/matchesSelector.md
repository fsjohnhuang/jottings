## 作用
  用于判断当前dom元素是否可通过入参的选择器搜索出来。相当于jQuery的`is`方法<br/>
## 接口规范
````
el.matchesSelector({DOMString} selector)
````
## 接口草案
(dom2标准)[http://www.w3.org/TR/selectors-api2/#matchtesting]<br/>
````
module dom {
[Supplemental]
interface Element {
boolean matchesSelector(in DOMString selectors, in optional Element refElement);
boolean matchesSelector(in DOMString selectors, in sequence<Node> refNodes);
};
};
````
(FF接口规范)[https://developer.mozilla.org/en/DOM/Node.mozMatchesSelector]<br/>
````
boolean matchesSelector(in DOMString selectors)
````

## 浏览器支持
IE9+支持msMatchesSelector<br/>
FF支持mozMatchesSelector<br/>
Chrome支持webkitMatchesSelector<br/>

## jQuery的实现

##QWrap实现
http://www.cnblogs.com/jkisjk/archive/2011/05/23/qwrap_selector_matchselector.html

