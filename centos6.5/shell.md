## 文件权限
**用户类型**<br/>
1. owner(所有者)
2. group(所属组)
3. other(其他用户)
**权限分类**<br/>
1. 基本权限,rwx<br/>
2. 特殊权限,SUID,SGID,SBIT<br/>
3. 隐藏权限，共有13种,仅在文件系统格式为Ext2+才有效<br/>
**基本权限类型**<br/>
1. r(读):对于普通文件就是查看的权限;对于目录就是列出目录下的文档内容(`ls 目录`)<br/>
2. w(写):对普通文件就是修改、删除的权限，需要配置r权限一起使用;对于目录配合x权限就可以添加、删除和移动内部文件，而删除目录和复制目录内文件则需要配合r和x权限。<br/>
3. x(执行):对于可执行文件就是可执行的权限。对于shell脚本文件则需要配置r权限才可执行；对于目录则是可以进入该目录和对其下文件作操作(`cd 目录`)<br/>
**特殊权限??**<br/>
用户在调用某程序时，该程序会以该用户的用户标识来执行，也就是继承用户的文件权限,倘若程序要操作某个用户没有权限操作的文件，那么将被拒绝。那么有没有让程序以其他用户标识在执行呢？特殊权限就可以了。
1. SUID:仅对二进制可执行文件，拥有SUID权限的用户在执行程序时，程序可获得程序所有者的权限支援。<br/>
2. SGID:对二进制可执行文件，拥有SGID权限的用户在执行程序时，程序可获得程序所属组的权限支援;对于目录而言，则在其下创建的文件、目录的所属组将不是所有者的群组,而是该目录的所属组<br/>
3. SBIT:stick bit, 由于用户只需拥有目录的wx权限，则可以删除、移动目录下任意目录和文件（即使该用户对于这些文件的权限为---）。通过为目录设置粘滞位SBIT，那么仅目录的所有者、目录下文件或目录的所有者和root用户才能删除、移动该目录下的目录和文件，需要配置w，x权限。<br/>
数字表示法：<br/>
SUID SGID SBIT = 111<br/>
因此可使用数字表示法设置特殊权限<Br/>
````
// 启动SUID
chmod 4000 文件名

// 启动SUID和SGID
chmod 6000 文件名

// 无法通过数字模式来关闭特殊权限
chmod 0000 文件名
// SUID和SGID依然有效
````
字符表示法：<br/>
SUID=--s-----, SGID=-----s---, SBIT=--------t<br/>
示例:<br/>
假设程序文件原来的权限为`rwxrwxr-x root wheel`,而程序要修改文件config.txt且其权限设置为`rwxrwx--- root wheel`,而现在以dummy用户标识运行程序，那么在程序是不予许修改文件。<br/>
当程序文件的权限修改为`rwsrwxr-x root wheel`或`rwxrwsr-x root wheel`那么程序就可以修改文件config.txt了<br/>
注意：如果同时有用x权限和SUID权限，则用s表示，若没有x权限则用S表示。
修改权限<br/>
````
// SUID
chmod u+s 文件名
chmod u-s 文件名

// SGID
chmod g+s 文件名
chmod g-s 文件名

// SBIT
chmod o+t 文件名
chmod o-t 文件名
````

**查看文件权限**<br/>
`ls -l`得到如下结果:<br/>
-rwxr-x-wx 1 root wheel 430540 Dec 20 18:27 /bin/bash<br/>
说明：<br/>
1. 首字符表示文件类型<br/>
`-`，普通文件<br/>
`d`，目录<br/>
`l`，符号链接<br/>
`c`，字符设备文件<br/>
`b`，块设备文件<br/>
`p`，先进先出文件<br/>
`s`, 套接字文件<br/>
2. 第2到10个字符则表示所有者、所属组和其他用户的权限<br/>
`rwx`，表示所有者拥有读、写和执行的权限<br/>
`r-x`，表示所属组拥有读、执行的权限<br/>
`-wx`，表示其他用户拥有写、执行的权限<br/>
3. 1表示该文件拥有一个硬链接<br/>
4. root为文件的所有者<br/>
5. wheel为文件的所属组<br/>
6. 430540为文件大小，以block为单位，若要以KB等作单位，则输入`ls -lh`<br/>
7. Dec 20 18:27 最后修改日期<br/>
8. /bin/bash为文件的绝对路径<br/>

**查看用户信息**<br/>
````
// 查看当前用户
whoami

// 查看当前用户所属组
groups

// 查看其他用户所属组
groups 用户1 用户2
````
**改变所有者和所属组**<br/>
注意：当前用户为root才能执行下列操作。<br/>
````
// 改变所有者
chown 用户名 文件路径

// 改变所属组
chgrp 用户组 文件路径

// 改变所有者和所属组
chown 用户名.用户组 文件路径

// 递归改变所有者和所属组
chown -R 用户名.用户组 目录路径
````
**改变权限**<br/>
有两种模式来设置权限，分别是符号模式和数字模式。<br/>
1. 符号模式<br/>
````
// 为所有者、所属组和其他用户添加执行的权限
chmod +x 文件路径
// 为所有者、所属组和其他用户删除执行的权限
chmod -x 文件路径

// 为所有者添加执行的权限
chmod u+x 文件路径
// 为所属组添加执行的权限
chmod g+x 文件路径
// 为其他用户添加执行的权限
chmod o+x 文件路径
// 为所有者、所属组添加执行的权限
chmod ug+x 文件路径

// 为所有者、所属组和其他用户添加写、执行的权限，取消读权限
chmod =wx 文件路径
// 为所有者、所属组添加写、执行的权限，取消读权限
chmod ug=wx 文件路径
````
2. 数字模式<br/>
权限三元组使用三位二进制数表示，且使用1表示有权限，0表示无权限。那么有rwx权限时，则表示为111；无权限时表示为000<br/>
而使用`chmod`设置时，不能直接使用三位二进制数表示权限，需要将二进制数转为八进制数才是有效入参。<br/>
权限与数字模式映射<br/>
````
rwx 7
rw- 6
r-x 5
r-- 4
-wx 3
-w- 2
--x 1
--- 0
````
````
// 为所有者、所属组和其他用户添加写、执行的权限，取消读权限
chmod 333 文件路径
````
**unmask**<br/>
当通过进程创建文件时，系统会参考unmask提供的权限来设置文件的权限。<br/>
unmask是用于告诉操作系统禁止用户类型的哪些权限，示例：<br/>
````
// 查看unmask值
# unmask
// 回显结果为022，转为符号模式就是----w--w-，意思是禁止所属组、其他用户的写权限（而不是添加写权限）
````
**用户、用户组信息文件**<br/>
`/etc/passwd`： 任何用户都可以读取该文件的内容。存放各个用户的用户名称、加密后的密码（若操作系统采用了shadow技术，则用加密密码在/etc/shadow文件中，这里用x或\*表示)、用户ID（一个用户ID可以对应多个用户名，每个用户名拥有独立的口令、家目录和shell，但系统会认为这些用户名是同一个用户。0是超级用户，1~99系统保留，100开始是普通用户，默认从500建普通用户。整体取值范围是0~65535）、用户组ID（对应/etc/group中的一条记录）、注释性描述字符串、家目录、shell程序路径<br/>
伪用户(psuedousers)：shell程序路径为空或为/sbin/nologin，主要用于方便系统管理、满足相应系统进程对文件属性的要求。伪用户如下：<br/>
bin，拥有可执行的用户命令文件<br/>
sys，拥有系统文件<br/>
adm，拥有账号文件<br/>
uucp，UUCP使用<br/>
lp，lp或lpd子系统使用<br/>
nobody，NFS使用<br/>

可通过设置"shell程序路径"为某程序，限制某用户进入系统后仅能执行该程序，程序执行结束则退出系统。<br/>

`/etc/shadow`：仅超级用户可以查看和修改，由pwconv命令根据/etc/passwd中的数据自动生成，记录为一对一关系。记录格式为：用户名、加密后的密码（固定长度为13个字符，空则表示没有密码，若含\./0-9A-Za-z的字符则无法登录）、最后修改密码的时间（以距离1970年1月1日的天数来计算）、最小时间间隔（修改密码的最小天数）、最大时间间隔（密码有效天数）、警告时间（从系统开始警告用户修改密码到无法登录的天数）、不活动时间（用户没有登录活动，但账号仍保持有效的最大天数）、失效时间（用户名的有效天数）<br/>
`/etc/group`：存放用户组名称、用户组加密后的密码（空、\*或x表示没有密码）、用户组ID、成员用户名称（成员用户名称间用,隔开）<br/>

**管理用户**<br/>
1. **添加用户**<br/>
命令：`useradd [-u uid] [-g group] [-d home] [-s shell] 用户名`<br/>
&emsp;`-u`,用户ID，用户ID是给系统使用的，而用户名是给用户使用的，两者是一一对应的关系<br/>
&emsp;`-g`,用户所属的用户组标识<br/>
&emsp;`-d`,以已存在的目录作为用户的家目录<br/>
&emsp;`-s`,定义shell<br/>
示例:<br/>
````
// 添加用户标识 fsjohnhuang 
# useradd fsjohnhuang
````
通过`/etc/passwd`、`/etc/shadow`和`/etc/group`文件内容，检查用户是否创建成功。<br/>
![](./passwd_shadow_group.jpg)<br/>
在创建用户时系统会根据`/etc/login.defs`和`/etc/default/useradd`文件的内容对新用户做基本设置。<br/>
`/etc/login.defs`文件<br/>
![](./etc_login_defs.jpg)<br/>
![](./etc_login_defs2.jpg)<br/>
`/etc/default/useradd`文件<br/>
![](./etc_default_useradd.jpg)<br/>
SKEL选项是指定用户家目录内容是从`/etc/skel`复制过来的。<br/>
2.**设置密码**<br/>
命令：`passwd 用户名 密码`<br/>
3. **启用、禁用帐号**<br/>
````
// 禁用
# usermod -L 用户名
// 启用 
# usermod -U 用户名
````
启用禁用实质就是在`/etc/shadow`文件中该用户名对应的记录上，在用户密码前删除、添加!（感叹号）来实现。<br/>
4. **改变用户的用户组**<br/>
````
# usermod -g 用户组名 用户名
````
5. **将用户添加到用户组**<br/>
````
# usermod -G 用户组名 用户名
````
6. **修改用户名**<br/>
````
usermod -l 新用户名 旧用户名
````
7. **删除用户**<br/>
````
# userdel 用户名
````

**管理用户组**<br/>
1. **添加用户组**<br/>
命令：`groupadd [-g gid [-o]] [-r] [-f] 用户组名`<br/>
2. **修改用户组名称**<br/>
````
# groupmod -n 新用户组名 旧用户组名
````
3. **删除用户组**<br/>
````
# groupdel 用户组名
````

**参考**<br/>
http://blog.csdn.net/fan_zhen_hua/article/details/2050009
http://blog.csdn.net/xsz0606/article/details/5256719
http://blog.chinaunix.net/uid-20671208-id-3488852.html
http://www.cnblogs.com/huangzhen/archive/2011/08/22/2149300.html
http://blog.csdn.net/liqfyiyi/article/details/7742775
http://os.51cto.com/art/201003/187591.htm
http://os.51cto.com/art/201003/187572.htm

## 进程
**`ps`**<br/>
查看进程<br/>
````
// 显示所有用户进程，显示用户和时间
# ps -aux
````


## 查找
**`locate`**<br/>
  用于模糊搜索文件(目录)的绝对路径。<br/>
  示例：<br/>
````
// 凡是绝对路径当中含jdk字符串的文件（目录）均被搜索出来
fsjohnhuang@fsjohnhuang~# locate jdk
````
失败示例：<br/>
````
// 在家目录添加文件mytest.md
fsjohnhuang@fsjohnhuang~# touch mytest.md
fsjohnhuang@fsjohnhuang~# locate mytest
````
上述例子再添加mytest.md后马上通过locate搜索mytest的绝对路径,但什么都搜不到。那是因为locate命令是从系统db中搜索的，而这个系统db是定时在晚上与文件系统的目录结构同步，因此刚才添加文件信息无法通过locate搜索出来。这也是locate的速度必find高的原因。我们可以通过`sudo updatedb`命令手动方式同步系统db,现在就可以通过locate来搜索了。<br/>
进阶<br/>
````
// 精确查找文件（目录),那么就只有文件或目录名为jdk才匹配成功
locate -b "\jdk"
// 精确查找文件（目录),那么就只有文件或目录名含jdk才匹配成功
locate -b "jdk"
````
**`find`**<br/>
  用于在特定目录(默认是当前目录)下根据文件名称、文件类型等信息搜索文件相对特定目录的相对路径。<br/>
示例:<br/>
````
// 在当前目录下搜索
find -name "mytest.md"
// 使用通配符
find -name "mytest*"

// 在特定目录下搜索
find /home/fsjohnhuang

// 仅列出普通文件
find -type f
// 仅列出目录
find -type d

// 对find搜索出来的文件或目录执行其他命令 
// -exec 其他命令 ';'
// 而'{}'就是代表find搜索出来的文件或目录
find -exec ls -l '{}' ';'
````
**`grep`**<br/>
  在文本字符串当中查找。<br/>
````
// 基本格式:grep 字符串 文件名
grep hello a.txt

// 不区分大小写
grep -i hello a.txt

// 显示行号
grep -n hello a.txt
````
**ack-grep**<br/>
  对于代码的查找可以使用ack-grep。<br/>
  教学地址:happycasts.net
**which**<br/>
  用于搜索命令的可执行文件所在位置。<br/>
  如`which cat`将返回/bin/cat<br/>
**whereis**<br/>
  用于搜索命令的可执行文件、源码文件和man文件所在位置。<br/>
  如`which cat`将返回`cat: /bin/ls /usr/share/man/man1/ls.1.gz /usr/share/man/man1p/ls.1p.gz`<br/>
