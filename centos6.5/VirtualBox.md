## 网络设置
**四个方式**<br/>
**1. NAT(Network Address Translation, 网络地址转换模式)**<br/>
原理：虚拟机通过宿主机访问所有网络数据，虚拟机并不真实存在于网络当中，宿主机和其他主机均不能访问虚拟机。<br/>
虚拟机与宿主机的关系：<br/>
  1. 默认为单向通迅，虚拟机可以访问宿主机，而宿主机无法访问虚拟机。<br/>
  2. 通过端口映射后，宿主机才能访问虚拟机。<br/>
虚拟机与网络中其他主机的关系：<br/>
  1. 单向通迅，虚拟机可以访问宿主机，而宿主机无法访问虚拟机。<br/>
同一宿主机的虚拟机间的关系：<br/>
  1. 各自独立，无法相互访问。<br/>

**2. Bridged Adapter(桥接模式)**<br/>
原理：相当于虚拟机直接使用宿主机的网卡直连网络，虚拟机和宿主机处于同一网段且有独立的局域网IP。<br/>
虚拟机与宿主机、网络上其他主机和其他虚拟机的关系：<br/>
  1. 可相互访问。<br/>

**3. Internal(内部网络模式)**<br/>
原理：虚拟机与外网完全断开，无法访问外网。<br/>
虚拟机与宿主机的关系：<br/>
  1. 相互无法访问<br/>
虚拟机与网络中其他主机的关系：<br/>
  1. 相互无法访问<br/>
同一宿主机的虚拟机间的关系：<br/>
  1. 网络名称相同时，可相互访问，否则无法访问<br/>

**4. Host-only Adapter(主机模式)**<br/>
最复杂的网络访问方式。<br/>
原理：宿主机上会模拟一张专供虚拟机使用的网卡,所有虚拟机都连接到该网卡上。然后我们就可以通过设置该网卡的属性达到上述三种方式的效果了。<br/>

**NAT端口映射**<br/>
通过`VBoxManage命令工具`,示例为在虚拟机上安装了Apache2服务器，使用80端口，现在需要映射到宿主机的8080端口
````
VBoxManage setextradata 'Linux Guest' 'VBoxInternal/Devices/pcnet/0/LUN#0/Config/fsjohnhuang/Protocol' TCP
````
````
VBoxManage setextradata 'Linux Guest' 'VBoxInternal/Devices/pcnet/0/LUN#0/Config/fsjohnhuang/GuestPort' 80
````
````
VBoxManage setextradata 'Linux Guest' 'VBoxInternal/Devices/pcnet/0/LUN#0/Config/fsjohnhuang/HostPort' 8080
````
其中'Linux Guest'是虚拟机名字，fsjohnhuang是自定义名称（可任意设置）<br/>
执行上述三行命令后，重启虚拟机就可以了。<br/>

