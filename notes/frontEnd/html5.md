### 全屏API ###
控制某个元素或整个页面全屏显示
```javascript
	// 封装统一的进入全屏模式接口
	var requestFullscreen = function(el){
		var fns = ['requestFullscreen'
				, 'mozRequestFullScreen'
				, 'webkitRequestFullscreen'
				, 'msRequestFullscreen']
			, carryOn = true;
		for(var i = fns.length; carryOn && --i > 0;){
 		   carrayOn = !el[fns[i]];
			carrayOn || el[fns[i]]();
 		} 
	};

	// 全屏显示整个文档
	requestFullscreen(document.documentElement);
	// 全屏显示某个元素
	requestFullscreen(document.getElementById('id'));
```
*注意：*用户可拒绝全屏模式
```javascript
	// 封装统一的退出全屏模式接口
	var exitFullscreen = function(){
		var fns = ['exitFullscreen'
				, 'mozCancelFullScreen'
				, 'webkitExitFullscreen'
				, 'msExitFullscreen']
			, carryOn = true;
		for(var i = fns.length; carryOn && --i > 0;){
 		   carrayOn = !document[fns[i]];
			carrayOn || document[fns[i]]();
 		} 
	};
```
属性和事件
```javascript
	// 获取当前全屏显示的对象
	var getFullscreenEl = function(){
		return document.fullscreenElement 
			|| document.mozFullScreenElement 
			|| document.webkitFullscreenElment 
			|| document.msFullscreenElement;
	};
	// 判断是否启用全屏
	var getFullscreenEnabled = function(){
		return document.fullscreenEnabled 
			|| document.mozFullScreenEnabled 
			|| document.webkitFullscreenEnabled 
			|| document.msFullscreenEnabled;
	};
	// 事件
	document.onfullscreenchange = function(){};
```
