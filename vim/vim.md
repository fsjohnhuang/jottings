## 配置文件 在网上列举了以下几个路径和文件<br/> ~/.vim，用户插件的存放目录<br/>
~/.vim，用户插件的存放目录<br/>
~/.vim/after，用户插件的小修改<br/>
~/.vimrc，用户配置文件<br/>
~/.exrc, 用户exrc文件<br/> /etc/vimrc，系统配置文件<br/> /usr/share/vim，系统插件存放的根目录<br/>
/usr/share/vim/vim72，VIM自带的默认插件文件存放目录<br/>
/usr/share/vim/vimfiles,系统插件文件存放目录，用户下载插件存放在这里就可以全局生效了<br/>
/usr/share/vim/vimfiles/after，对已有的全局设置进行小修改和覆盖<br/>
1. **查看配置文件位置**<br/>
进入vim的命令模式并输入`:version`<br/>
(a).`VIM - Vi IMproved 7.2 (2008 Aug 9, compiled Apr  5 2012 10:17:30)`表示当前VIM的版本号，而/usr/share/vim/vim72就是表示该版本的默认插件文件存放路径。不建议将用户下载的插件文件存放在该路径下，因为在升级VIM时很可能会VIM自带的插件会覆盖该目录下的同名插件。<br/>
进入vim的命令模式并输入`:echo $VIMRUNTIME`同样都会返回/usr/share/vim/vim72这个路径。<br/>
该路径下插件目录结构为标准结构，/usr/share/vim/vimfiles和~/.vim均因以其作为模板来建立插件目录结构<br/>
>\+ \<Directory\> <br/>
&emsp;\+  colors<br/>
&emsp;\+  compiler<br/>
&emsp;\+  doc<br/>
&emsp;\+  etc<br/>
&emsp;\+  ftdetect<br/>
&emsp;\+  ftplugin<br/>
&emsp;\+  indent<br/>
&emsp;\+  keymap<br/>
&emsp;\+  plugin<br/>
&emsp;\+  syntax<br/>

(b).另外还有以下信息<br/>
````	
系统 vimrc 文件: "/etc/vimrc"
用户 vimrc 文件: "$HOME/.vimrc"
用户 exrc 文件: "$HOME/.exrc"
$VIM 预设值: "/usr/share/vim"
````
2. **相关的全局变量,和VIM命令**<br/> 
`$VIMRUNTIME`:<br/>
`$VIM`:<br/>
`$HOME`:<br/>
`:scriptname`:<br/>
`:set rtp`:<br/>
3. **ex命令**<br/>
3.1. 引用外部配置信息`source <外部配置信息绝对路径.vim>`<br/>
&emsp;示例：`source /etc/basic.vim`<br/>
3.2. 一般设置<br/>
````
// 设置默认解码
set fenc=utf-8
set fencs=utf-8,usc-bom,euc-jp,gb18030,gbk,gb2312,cp936
````

