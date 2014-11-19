## 消息提示
**alert**<br/>
````
/**
 * icon的取值有效范围为error、question、info和warning,若设置为其他值则不显示图标
 * fn是点击确认后被调用 
 */
$.messager.alert({DOM String} title
  , {DOM String} msg
  , {DOM String} icon
  , {Function} fn);
````
**confirm**<br/>
````
/**
 * fn(r)是点击确定或取消按钮后被调用，入参{Boolean} r表示是否点击确定按钮
 */
$.messager.confirm({DOM String} title
  , {DOM String} msg
  , {Function} fn(r));
````
**prompt**<br/>
````
/**
 * fn(r)是点击确定或取消按钮后被调用，入参{DOM String} r表示用户输入的内容
 */
$.messager.prompt({DOM String} title
  , {DOM String} msg
  , {Function} fn(r));
````
**process**<br/>
````
/** 显示进度条
 * 入参为对象
 */
$.messager.progress({
  title: {DOM String} title, // 标题
  msg: {DOM String} msg, // 进度条上的文字内容
  text: {DOM String} text, // 进度条中的文字内容
  interval: {Integer} interval=300 // 间隔N毫秒，进度条前进一次
});

/** 获取进度条的对象
 */
var progress = $.messager.progress('bar');

/** 关闭进度条
 */
$.messager.progress('close');
````
**通知提示**<Br/>
默认情况下，出现在浏览器可视区域的右下方。(可通过style属性对其进行定位)<br/>
````
$.messager.show({
  title: {DOM String} title,
  msg: {DOM String} msg,
  showType: {DOM String} showType='slide', // 出现方式，有效取值范围null、slide、fade和show
  showSpeed: {Integer} showSpeed=600, // 出现动画时间（毫秒）
  width: {Integer} width=250,
  height: {Integer} height=100,
  timeout: {Integer} timeout=4, // 通知提示停留n秒后自动消失，若设置0则不会自动消息
  style: {Object} style // 样式对象
});
````

## 对话框
**通过HTML Tag方式创建**<br/>
````
<div class="easyui-dialog"
  data-options="title:'标题'">
</div>
````
**通过JS方式创建**<br/>
````
<div id="dd"></div>
$('#dd').dialog({
  title: '标题'
});
````
**data-options**<br/>
1.`toolbar`<br/>
位于对话框上方的工具栏。<br/>
````
// 属性值为{DOM String} selector时，则使用外部HTML Tag定义工具栏。
// 特点：工具栏的元素自由
toolbar: '#tb'
<div id="tb">
  <a href="javascript:void 0"
	class="easyui-linkbutton"
	data-options="plain:true">按钮</a>
</div>

// 属性值为{object Array} linkbuttons时，数组元素为linkbutton的配置属性
// 特点：工具栏的元素只能是linkbutton组件 
toolbar: [{
  text: '按钮',
  iconCls: 'icon-add',
  handler: function(){
    // 响应点击事件
  }
}]
````
2. `buttons`<br/>
位于对话框下方的按钮栏<br/>
````
// 属性值为{DOM String} selector时，则使用外部HTML Tag定义工具栏。
// 特点：工具栏的元素自由
buttons: '#tb'
<div id="tb">
  <a href="javascript:void 0"
	class="easyui-linkbutton"
	data-options="plain:true">按钮</a>
</div>

// 属性值为{object Array} linkbuttons时，数组元素为linkbutton的配置属性
// 特点：工具栏的元素只能是linkbutton组件 
buttons: [{
  text: '按钮',
  iconCls: 'icon-add',
  handler: function(){
    // 响应点击事件
  }
}]
````
3. 从父类window继承过来重要的属性<br/>
````
modal: {Booelean}modal，是否启动模态模式,默认为false
shadow: {Boolean}shadow, 是否显示阴影, 默认为true
````
4. 从父类panel继承过来重要的属性<Br/>
fit: {Boolean}fit,是否自动适应父容器大小，默认值为false
tools: {String}selector|{Array}tools, 自定义工具按钮，设置方式与toolbar一样，但显示在对话框的左上角。
href: {String}href,对话框内容通过href加载来。注意：由于是通过XHR请求的，因此需要遵守同源策略。
cache: {Boolean}cache,是否缓冲通过href加载来的内容，默认值为true
queryParams: {Object}queryParams，内容加载的href的querystring键值对
method: {DOM String}请求方式，默认为get
loader: {Function}loader(param, success(data),error())，内容加载器，可重写该方法实现自定义的内容加载方式，而不使用XHR来请求内容
````
**获取对话框对象**<br/>
````
var dialog = $('#dd').dialog('dialog');
````

## 布局
1. **layout**<br/>
````
<div class="easyui-layout">
  <div data-options="region:'north'">可选</div>
  <div data-options="region:'west'">可选</div>
  <div data-options="region:'center'">必须</div>
  <div data-options="region:'east'">可选</div>
  <div data-options="region:'south'">可选</div>
</div>
````
2. **accordion**<br/>
````
<div class="easyui-accordion">
  <div data-options="title:'title1'"></div>
  <div data-options="title:'title2',selected:true"></div>
</div>
````
3. **tabs**<br/>
````
<div class="easyui-tabs">
  <div data-options="title:'title1'"></div>
  <div data-options="title:'title2',selected:true"></div>
</div>
````
tabs布局的属性<br/>
````
tools: {String} selector|{Array} tools, 容器右上方或左上方的工具栏
toolPosition: {String} position, 工具栏位置，有效取值范围left|right
tabPosition: {String} position, tab按钮位置，有效取值范围top|left|right|bottom
````

## 菜单

## 树

## 表格

## 表单

## 拖拽，移动，伸缩
