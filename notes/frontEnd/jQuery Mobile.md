### 手机浏览器模拟器 ###
1. Windows Phone Emulator
2. VS11 Page Inspector
3. Opera Mobile Emulator（http://www.opera.com/developer/tools/mobile/）
4. Mobilizer(IPhone模拟器，要先安装Adobe Air。http://www.springbox.com/mobilizer/)
5. Ripple Mobile Environment Emulator(Chrome)
6. 在线模拟器(iphone4simulator.com,www.testiphone.com)

### 快速开发界面与样式的工具 ###
1. www.codiqa.com/builder
2. tiggzi.com
3. jquerymobile.com/themeroller

### 实例 ###
index.html
```HTML
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>jQuery Mobile Test</title>
    <link rel="stylesheet" type="text/css" href="./jquery.mobile-1.4.0/jquery.mobile-1.4.0.css"/>
    <script src="./jquery.js"></script>
    <script type="text/javascript" charset="utf-8">
      // 基础配置
      $(document).bind('mobileinit', function(evt){
            $.mobile.loadingMessage = '正在加载...';
            $.mobile.pageLoadErrorMessage = '404';
            });
      // 自定义404
      $(document).bind('pageloadfailed', function(evt, data){
            evt.preventDefault();
            data.deferred.reject(data.absUrl, data.options);

            $.mobile.changePage('404页面地址', {transition: 'pop', role: "dialog"});
          });
    </script>
    <script src="./jquery.mobile-1.4.0/jquery.mobile-1.4.0.js"></script>
  </head>
  <body>
    <div id="page1" data-role="page">
      <div data-role="header">Page1</div>
      <div data-role="content">
        This is content part
        <!--
        data-prefetch会预加载页面内容
        也可通过脚本$.mobile.loadPage('brief.html', {showLoadMsg: false});预加载
          若showLoadMsg为true，那么就会出现模态的加载动画
        -->
        <a href="brief.html" data-prefetch>Go 2 Brief</a>
        <!--
        对话框
        -->
        <a data-role="button" href="./detail.html" data-rel="dialog">browse detail in Dialog</a>
      </div>
      <div data-role="footer">
        <!--
        页内视图跳转：
          href指定page的id
          data-transition指定场景切换动画,slide|slideup|slidedown|pop|fade|flip
        -->
        <a href="#page2" data-transition="flip">next</a>
        <!--
        页面间视图跳转
          data-ajax默认是true
        -->
        <a href="./detail.html" data-ajax="true" data-transition="pop">open</a>
      </div>
    </div>
    
    <!--data-title指定浏览器标题，默认使用title标签内容-->
    <!--
    data-dom-cache:true指示缓存本视图
    可通过$('#page2').page({domCache: true})来缓存本视图
    也可通过$.mobile.page.prototype.options.domCache = true;来设置缓存所有视图
    -->
    <div id="page2" data-role="page" data-title="Page2" data-dom-cache="true">
      <div data-role="header">page2
      </div>
      <div data-role="content">
        <!--
        data-role为listview时为列表
        data-filter会出现查询框，根据列表已有内容筛选记录
        data-inset表示圆角
        当更新了listview的内容后，要调用$(#'listview元素的id').listview('refresh')，这样才会在刷新UI
         -->
         <ul data-role="listview" data-inset="true" data-filter="true" data-filter-placeholder="seach">
          <li data-role="list-divider">
            我的好友<span class="ui-li-count">3</span>
          </li>
          <li>
            王力宏<p class="ui-li-aside">我终于结婚了！</p>
          </li>
          <li>
            <a>陈奕迅<p class="ui-li-aside">结婚了！</p></a>
          </li>
          <li>
            <a>李云迪<p class="ui-li-aside">我只好结婚了！</p></a>
            <a></a>
          </li>
        </ul>
      </div>
      <div data-role="footer">
        <!--
          页内视图回退:
            data-rel:back, 以相同的场景切换动画回退到上一个视图
        -->
        <a data-role="button" href="#" data-rel="back">back</a>
      </div>
    </div>

    <script type="text/javascript" charset="utf-8">
      $('#page1').live('pageinit', function(evt){
            // 页面初始化设置
          });
    </script>
  </body>
</html>
```
detail.html
```html
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>jQuery Mobile Test Detail</title>
    <link rel="stylesheet" type="text/css" href="./jquery.mobile-1.4.0/jquery.mobile-1.4.0.css"/>
    <script src="./jquery.js"></script>
    <script src="./jquery.mobile-1.4.0/jquery.mobile-1.4.0.js"></script>
  </head>
  <body>
    <div id="page3" data-role="page">
      <div data-role="header">Page3</div>
      <div data-role="content">
        <!--电话号码，分机号码-->
        <a href="tel:888888888,123">88888888</a>
        <!--发邮件-->
        <a href="mailto:fsjohnhuang@hotmial.com?cc=addr1&bcc=addr2&subject=hello&body=hi">发邮件</a>
      </div>
      <div data-role="footer">
        <!--
        页面间视图返回：
          data-direction="reverse"，返回上一个页面的href指向的视图
        -->
        <a href="#page1" data-direction="reverse" data-transition="flip">back</a>
      </div>
    </div>
  </body>
</html>

```
