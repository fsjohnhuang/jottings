## 配置文件
在网上列举了以下几个路径和文件<br/>
~/.vim<br/>
~/.vimrc<br/>
~/.exrc<br/>
/etc/vimrc<br/>
/usr/share/vim<br/>
/usr/share/vim/vim72<br/>
/usr/share/vim/vimfiles<br/>
到底它们有什么作用呢？
1. 查看配置文件位置
进入vim的命令模式并输入`:version`
\[a\].`VIM - Vi IMproved 7.2 (2008 Aug 9, compiled Apr  5 2012 10:17:30)`表示当前VIM的版本号，而/usr/share/vim/vim72就是表示该版本的默认插件文件存放路径。不建议将用户下载的插件文件存放在该路径下，因为在升级VIM时很可能会VIM自带的插件会覆盖该目录下的同名插件。<br/>
进入vim的命令模式并输入`:echo $VIMRUNTIME`同样都会返回/usr/share/vim/vim72这个路径。<br/>
该路径下插件目录结构为标准结构，/usr/share/vim/vimfiles和~/.vim均因以其作为模板来建立插件目录结构<br/>
>\+ \<Directory\>
 	\+  colors
    \+  compiler
    \+  doc
    \+  etc
    \+  ftdetect
    \+  ftplugin
    \+  indent
    \+  keymap
    \+  plugin
    \+  syntax
