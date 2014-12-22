#NPM
nodejs的包管理器，随nodejs sdk一同安装。<br/>
包管理包括如下内容：<br/>
1. 依赖包下载；<br/>
2. 依赖包更新；<br/>
3. 包分发；<br/>
依赖包位于项目根目录下或npm项目下的node_modules文件夹中。<br/>
package.json文件则用于描述如项目名称、版本号、描述信息、作者等项目信息及项目所依赖的包信息（如同java的MANIFEST.MF文件）<br/>

`npm list`，查看当前项目的依赖包。<br/>
`npm list -g`，查看全局环境的依赖包。<br/>

## 依赖包的安装
**1. 本地安装**<br/>
`npm install <package-name>`，将依赖包下载到当前项目根目录下的node_modules目录下。<br/>
在项目中可通过`require`方法引入本地依赖包。<br/>
**2. 全局安装**<br/>
`npm install <package-name> -g`，将依赖包下载到npm根目录下的node_modules目录下。<br/>
将依赖包下载到全局环境中，作用是通过命令行界面(cmd或shell)可直接调用依赖包（如grunt-cli包下载到全局环境，则可以在cmd或shell中输入grunt）,无法在项目中通过`require`引用全局环境中的依赖包。<br/>


## package.json文件
通过`npm init`，在当前工作目录中创建package.json文件（创建过程中会引导用户输入项目名、版本号等信息）。<br/>
通过`npm install`，读取当前工作目录的package.json文件，并根据文件中的devDependencies和dependencies属性下载依赖包到当前目录下的node_modules目录下。(`npm install -g`，则下载到npm目录下的node_modules目录下)<br/>

##参考
http://www.cnblogs.com/linjiqin/p/3765772.html
http://www.infoq.com/cn/articles/nodejs-npm-install-config/
http://www.infoq.com/cn/articles/msh-using-npm-manage-node.js-dependence

