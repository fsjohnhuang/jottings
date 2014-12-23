#NPM
nodejs的包管理器和分发，随nodejs sdk一同安装。<br/>
功能与Ruby的gem,Python的pypl或setuptools,PHP的pear一样。<br/>
包管理包括如下内容：<br/>
1. 依赖包下载；<br/>
2. 依赖包更新；<br/>
3. 包分发；<br/>
依赖包位于项目根目录下或npm项目下的node_modules文件夹中。<br/>
package.json文件则用于描述如项目名称、版本号、描述信息、作者等项目信息及项目所依赖的包信息（如同java的MANIFEST.MF文件）<br/>

##基本配置
查看配置信息：`npm config ls`(查看所有：`npm config ls -l`)<br/>
修改配置信息的三种方式：<br/>
1. 修改.npmrc文件<br/>
2. 通过`npm config set <config> <config-value>`命令<br/>
3. 临时修改，通过`npm install <package-name> --registry=<source-uri>`命令<br/>
设置npm源地址：`npm config set registry <source-uri>`<br/>
````
npm config set registry http://registry.npm.taobao.org
````
NPM资源库:<br/>
````
http://npm.hacknodejs.com/
http://registry.npm.taobao.org
````
设置npm网络代理服务器：`npm config set proxy <proxy>`<br/>
````
npm config set proxy http://fsproxy.fs.gmcc.net:8081/<br/>
````

查看局部包的安装路径：`npm root`<br/>
查看全局包的安装路径：`npm root -g`<br/>

搜索依赖包：`npm search <package-name>`<br/>

##操作已下载/远程的依赖包信息
查看包的package.json信息：`npm view <package-name>`<br/>
查看包的依赖关系：`npm view <package-name> dependencies`<br/>
查看包的源文件地址：`npm view <package-name> repository.url`<br/>
查看包所依赖的node版本号：`npm view <package-name> engines`<br/>

更新包：`npm update <package-name>`<br/>
卸载包：`npm uninstall <package-name>`<br/>
检查包是否过期：`npm outdated <package-name>`<br/>

`npm list`，查看当前项目的依赖包。<br/>
`npm list -g`，查看全局环境的依赖包。<br/>
列出过期的包：`npm outdated`<br/>

## 发布包<br/>
1. **创建npm registry账号**<br/>
`npm adduser`,然后根据提示输入账号、密码和邮箱地址。<br/>
2. **客户端保存npm registry账号信息**<br/>
`npm login`。<br/>
3. **发布**<br/>
建议发布的版本号从1.0.0开始。<br/>
`npm publish <update_type>`,
##版本升级规则
bug修复或者小修改则进行Patch发布。<br/>
向后兼容功能新增则进行Minor发布。<br/>
非向后兼容功能修改则进行Major发布。<br/>

## 依赖包的安装
**1. 本地安装**<br/>
`npm install <package-name>`，将依赖包下载到当前项目根目录下的node_modules目录下。<br/>
在项目中可通过`require`方法引入本地依赖包。<br/>
**2. 全局安装**<br/>
`npm install -g <package-name>`，将依赖包下载到npm根目录下的node_modules目录下。<br/>
将依赖包下载到全局环境中，作用是通过命令行界面(cmd或shell)可直接调用依赖包（如grunt-cli包下载到全局环境，则可以在cmd或shell中输入grunt）,无法在项目中通过`require`引用全局环境中的依赖包。<br/>
**安装特定版本的包**<br/>
`npm install <package-name>@<version>`<br/>


## package.json文件
通过`npm init`，在当前工作目录中创建package.json文件（创建过程中会引导用户输入项目名、版本号等信息）。<br/>
查看package.json内容说明：`npm help json`<br/>
通过`npm install`，读取当前工作目录的package.json文件，并根据文件中的devDependencies和dependencies属性下载依赖包到当前目录下的node_modules目录下。(`npm install -g`，则下载到npm目录下的node_modules目录下)<br/>
**属性讲解**<br/>
`name`：必选项，项目名称。命名时不要包含js、node等字样，并且不能包含url中需转义的字符，不能以.和_为开头。<br/>
`version`：必选项，包版本号。版本号由`主版本号(Major).副版本号(Minor).补丁版本号(Patch)[-prepatch]`构成<br/>
在engines、dependencies和devDependencies中可采用语义化版本号<br/>
`1.1.1`：精确下载安装1.1.1版本的包<br/>
`>、<=、>、>=1.1.1`：分别表示下载安装大于、小于等于、大于等于1.1.1版本的包<br/>
`1.0.1-1.1.1`：表示版本范围是包含1.0.1到1.1.1版本的包<br/>
`~1.1.1`：表示尽量采用靠近1.1.1版本的包，但可用的包版本范围是1.1.1-0到1.1.x-x版本的包<br/>
`~1.1`：表示下载安装1.1.x-x版本的包<br/>
`~1`：表示下载安装1.x.x-x版本的包<br/>
`^1.1.1`：表示包版本范围是1.1.1到1.x.x-x<br/>
`^0.1.1`：表示包版本范围是1.1.1到1.1.x-x<br/>
`^1.1`：表示包版本范围是1.1.x-x到1.x.x-x<br/>
`^1`：表示包版本范围是1.x.x-x<br/>
` 、x、*`：表示任意版本即可<br/>
两个版本选择器间，空格表示and关系，||表示or关系。<br/>
`description`：项目功能描述，显示在`npm search`中<br/>
`keywords`：项目关键字，显示在`npm search`中<br/>
`author`：作者信息<br/>
````
"author":{
	"name": "fsjohnhuang",
    "url": "http://fsjohnhuang.cnblogs.com/"
}
````
`engines`：依赖的node版本<br/>
````
"engines":{
	"node": ">= 0.8.0"
}
````
`dependencies`：程序运行时依赖的包，当通过`npm install`安装当前包时，会自动将dependencies下的依赖包都一同下载安装。<br/>
`devDependencies`：程序在开发阶段使用的依赖包，如grunt-contrib-copy等仅在程序开发阶段使用。当通过`npm install`安装当前包时，不会自动下载安装。需要通过`npm install <package-name> --dev`才会下载。<br/>
`main`：包入口文件。例如模块名为test-in-action,入口文件为index.js。<br/>
````
{
	"name": "test-in-action",
	"main": "index.js"
}
````
那么下载安装时就执行`npm install test-in-action`, 调用时则为`require('test-in-action')`。
`repository`：源码托管的地址。<br/>
````
"repository":{
	"type": "git",	
	"url": "http://github.com/npm/npm.git"
}
````
`scripts`：自定义调用`npm <script>`时执行的命令。npm已经为我们预设了npm脚本。<br/>

## 排除文件
默认会必定排除以下文件<br/>
````
.*.swp
.*.swp
._*
.DS_Store
.git
.hg
.lock-wscript
.svn
.wafpickle-*
CVS
npm-debug.log
````
默认排除node_modules的内容<br/>
若不存在`.npmignore`文件，则会使用`.gitignore`文件的内容。若不想受`.gitignore`影响则创建一空的`.npmignore`<br/>
无法排除package.json和README.*文件<br/>

##参考
http://www.cnblogs.com/linjiqin/p/3765772.html
http://www.infoq.com/cn/articles/nodejs-npm-install-config/
http://www.infoq.com/cn/articles/msh-using-npm-manage-node.js-dependence

