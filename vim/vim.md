## 配置文件
在网上列举了以下几个路径和文件<br/>
~/.vim，用户插件的存放目录<br/>
~/.vimrc，用户配置文件<br/>
~/.exrc, 用户exrc文件<br/>
/etc/vimrc，系统配置文件<br/>
/usr/share/vim，系统插件存放的根目录<br/>
/usr/share/vim/vim72，VIM自带的默认插件文件存放目录<br/>
/usr/share/vim/vimfiles,系统插件文件存放目录，用户下载插件存放在这里就可以全局生效了<br/>
1. 查看配置文件位置
进入vim的命令模式并输入`:version`
\[a\].`VIM - Vi IMproved 7.2 (2008 Aug 9, compiled Apr  5 2012 10:17:30)`表示当前VIM的版本号，而/usr/share/vim/vim72就是表示该版本的默认插件文件存放路径。不建议将用户下载的插件文件存放在该路径下，因为在升级VIM时很可能会VIM自带的插件会覆盖该目录下的同名插件。<br/>
进入vim的命令模式并输入`:echo $VIMRUNTIME`同样都会返回/usr/share/vim/vim72这个路径。<br/>
该路径下插件目录结构为标准结构，/usr/share/vim/vimfiles和~/.vim均因以其作为模板来建立插件目录结构<br/>
>\+ \<Directory\> <br/>
 	\+  colors<br/>
    \+  compiler<br/>
    \+  doc<br/>
    \+  etc<br/>
    \+  ftdetect<br/>
    \+  ftplugin<br/>
    \+  indent<br/>
    \+  keymap<br/>
    \+  plugin<br/>
    \+  syntax<br/>
