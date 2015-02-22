http://yui.github.io/yuidoc/

### YUIDoc ###
1. 基于Nodejs
2. 安装
```npm
	npm -g install yuidocjs
```
3. 检查安装成功与否
```npm
	yuidoc -v
```
4. 一次性生成文档当前目录及其子目录下所有js的文档，默认情况下会在当前目录下生成out目录并将js文档存放在out目录下
```npm
	yuidoc .
```
指定输出目录
```npm
	yuidoc . -o <directory path>
	或
	yuidoc . --out <directory path>
```
5. 实时生成文档：默认开放3000端口监听当前目录文件变动，可通过http://127.0.0.1:3000/访问
```npm
	yuidoc --server [3000] .
```