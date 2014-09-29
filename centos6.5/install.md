##Linux安装文件
**种类**<br/>
1. 打包活压缩文件(.tar,.tar.gz等)<br/>
  一般是解压后即可使用，或解压后运行sh文件,或手动编译安装<br/>
**.tar.gz后缀**<br/>
````
// 解压
tar -zxvf 文件路径.tar.gz
````
**.tar.bz2或.tar.bz后缀**<br/>
````
// 解压
tar -jxvf 文件路径.tar.bz2
````
**手动编译安装**<br/>
有三种编译构建文件，configure、Makefile和Imake。<br/>
软件的安装位置会在安装前的说明那提示，或在README中注明。<br/>
a.configure文件<br/>
````
// 配置
# ./configure
// 编译
# make
// 将可执行的二进制文件复制到$PATH中
# make install
// 删除安装时产生的临时文件
# make clean
````
b.Makefile文件<br/>
````
# make
# make install
````
c.Imake文件<br/>
````
# xmkmf
# make
# make install
````
若执行上命令失败，需安装gcc,make,autoconf等工具，和源码所依赖的链接库。<br/>

卸载<br/>
````
# make uninstall
````
若没有提供该功能，则要手动删除文件，但由于文件散落在系统的各个目录下，难以清理。因此在安装前要自行修改配置文件。

2. 配备管理工具的安装包(.deb,.rpm等)<br/>
  一般通过apt安装.deb，通过yum或rpm工具安装.rpm文件<br/>
**rpm包**<br/>
内部包含可以立即在特定机器体系结构上安装和运行的Linux软件。<br/>
内部结构为：<br/>
.spec | 安装前脚本 | 二进制文件 | ...... | 二进制文件 | 安装后脚本<br/>
````
// 安装
rpm -ivh rpm包路径

// 升级软件
rpm -Uvh rpm包路径

// 卸载
rpm -e 软件
// 当有其他程序依赖该程序时，上述方式则执行失败
// 强制卸载
rpm -ivh --force rpm包路径

// 查看包信息
rpm -qpi rpm包路径

// 查看包会向系统写入哪些文件
rpm -qpl rpm包名
````
**.src.rpm文件**
此类文件需要手工编译生成.rpm文件才能安装。<br/>
````
// 编译,一般会编译到/usr/src/redflag/RPMS/子目录下
rpm --rebuild 文件路径.src.rpm
````

**deb包**<br/>
deb包则通过dpkg操作,优点是自动处理程序的依赖关系<br/>
````
// 安装
dpkg -i deb包路径

// 卸载
dpkg -e 软件
````


3. .bin文件<br/>
  .bin文件其实就是sh和zip一同打包，或者sh和rpm一同打包的问题，运行bin文件时实际上就是通过bin文件中的sh来解压bin中的zip、tar等文件或安装rpm文件的过程。<br/>
  优点:<br/>
   1. 安装包就是一个.bin文件，简单;<br/>
   2. 可以直接在linux上运行，不依赖像apt等管理工具;<br/>
   3. 可是在sh文件中添加协议信息，用户同意后才执行安装。<br/>

