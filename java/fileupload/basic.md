## 表单ENCTYPE属性
`application/x-www-form-urlencoded`,默认编码方式,指处理表单域的value属性值.采用这种编码方式的表单会将表单域的值处理成URL编码方式.<Br/>
`multipart/form-data`,表单会以二进制流的方式处理表单数据,这种编码方式会把文件域指定的文件的内容也封装到请求参数中.<br/>
`text/plain`,适合用于直接将表单发送邮件的方式.<br/>

## 下载
1. 设置响应头Content-Type为`application/octet-stream`或`application/x-msdownload`等;
````
// JSP设置如下
HttpServletResponse#setContentType("Content-Type", "application/octet-stream");
````
2. 设置响应头Content-Disposition为`attachment;filename=文件名`
````
// JSP设置如下
HttpServletResponse#setContentType("Content-Disposition", "attachment;filename=test.txt");
````
3. 将文件写入响应流
````
// JSP设置如下
byte[] fileContent;
HttpServletResponse#getOutputStream().write(fileContent, 0, fileContent.length);
HttpServletResponse#getOutputStream().close();
````
