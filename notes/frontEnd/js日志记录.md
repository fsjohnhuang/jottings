### log4javascript ###
1. **资源**：https://github.com/toushay/log4javascript
2. **概要**
```javascript
	var defaultLog = log4javascriptr.getDefaultLogger(); // 获取默认logger，使用PopUpAppender

	var log = log4javascript.getLogger('main'); // 获取名为main的logger
	/* 设置Appender */
	// 独立console，当输出第一条日志时就会弹出console
	var popUpAppender = new log4javascript.PopUpAppender();
	popUpAppender.setFocusPopUp(true); // 设置输入日志信息时，焦点附加到console
	popUpAppender.setNewestMessageAtTop(true); // 设置新的日志信息插入到console的最顶位置 
	
 	// 内嵌console 
	var inPageAppender = new log4javascript.InPageAppender();

	// 异步发送日志信息到服务端
	var ajaxAppender = new log4javascript.AjaxAppender(URL);
	// 设置Appender的日志等级
	ajaxAppender.setThreshold(log4javascript.Level.ERROR);

	/* 设置Layout */
	var layout = new log4javascript.PatternLayout('[%-5p] %m');
	popUpAppender.setLayout(layout);

	log.addAppender(popUpAppender);
	// log.addAppender(inPageAppender);

	// 日志信息输出,msg可以是任何数据类型
	log.trace(msg[,msg2,...][,exception]);
	log.debug(msg[,msg2,...][,exception]);
	log.info(msg[,msg2,...][,exception]);
	log.warn(msg[,msg2,...][,exception]);
	log.error(msg[,msg2,...][,exception]);
	log.fatal(msg[,msg2,...][,exception]);

	// 断言
	log.assert(逻辑判断表达式);

	// 启动和禁用日志
	log.setEnabled(true|false);

	// 设置日志组，仅PopUpAppender和InPageAppender有效
	log.group(组名);
	调用log.info等日志信息输出函数
	log.groupEnd();

	// 计时器
	log.time(计时器名,[日志等级，默认是INFO]);
	log.timeEnd(计时器名);
```
3. **日志级别，有低到高**
若Logger和Appender设置了某日志级别，那么记录等于和高于该级别的日志信息。
```javascript
	log4javascript.Level.ALL
	log4javascript.Level.TRACE
	log4javascript.Level.DEBUG (logger默认的日志等级)
	log4javascript.Level.INFO
	log4javascript.Level.WARN
	log4javascript.Level.ERROR
	log4javascript.Level.FATAL
	log4javascript.Level.OFF
```
4. **日志记录发送到服务端**
使用`AjaxAppender`就可以将日志记录发送到服务端，Layout默认使用`HttpPostDataLayout`。
`HttpPostDataLayout`是一条日志信息就向服务端发送一条异步请求，参数键为data。
`JsonLayout`和`XmlLayout`支持批量向服务端发送一条异步或同步请求，参数键默认为data（可通过`setProValueName`修改）
5. **Layout**
有`NullLayout`、`SimpleLayout`、`PatternLayout`、`XmlLayout`、`JsonLayout`和`HttpPostDataLayout`。