### 日期 ###
1. 日期字段类型使用`DateTime`
2. 日期格式为`ISO 8601`标准字符串格式，`C#`要通过`DateTime.ToString("s")`来输出该格式
```C#
	DateTime now = DateTime.Now;
	string iso8601Time = now.ToString("s"); // iso8601Time格式形如：2013-02-1T12:11:12
	// 然后再插入SQLite数据表中，否则在查询时会报非合法日期格式的字符串
```
3. 获取当前时间`datetime(CURRENT_TIMESTAMP,'localtime')`