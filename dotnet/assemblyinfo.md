##AssemblyInfo.cs文件
**作用**<br/>
用于设置程序集(dll文件)的常规信息<br/>
````
// 程序集标题
[assembly:AssemblyTitle("程序集标题")]
// 程序集描述
[assembly:AssemblyDescription("程序集描述")]
// 产品名称
[assembly:AssemblyProduct("产品名称")]
// 公司
[assembly:AssemblyCompany("公司名称")]
// 合法商标
[assembly:AssemblyTrademark("合法商标")]
// 产品的内部名称
[assembly:AssemblyCulture("产品的内部名称")]
// 版权
[assembly:AssemblyCopyright("Copyright 2014")]

// 程序集的版本信息由下面四个值组成:
//
//      主版本
//      次版本 
//      内部版本号
//      修订号
//
// 可以指定所有这些值，也可以使用“内部版本号”和“修订号”的默认值，
// 方法是按如下所示使用“*”:
[assembly:AssemblyVersion("1.0.0.0")]

// 控制程序集中所有类型对COM的可访问性
[assembly:ComVisible(false)]
// 配置文件
[assembly:AssemblyConfiguration("")]
````
对生成的dll文件右键点击属性，即可查看到上述信息
**具体说明**<br/>




## 参考
http://www.itwis.com/html/net/aspnet/20091211/7123.html
