## �ֲ�ʽ��ʽ
1. ������Ϣ
2. ����Զ�̵��ã�RPC��

## �㲥
����UDP/IP��չ�����ĶಥЭ�飬һ�������������Ͻ��д��䡣<br/>
���ݽ��շ��ͷ��ͷ���Ҫ����ಥ�飨�ಥ���ַ��224.0.0.0~239.255.255.255֮�䣩<br/>
````
InetAddress groupAddress = InetAddress.getByName("244.1.1.1");
MulticastSocket server = new MulticastSocket(port);
// ͨ��server.leaveGroup(�ಥ��ַ)���Ƴ��ಥ��
server.joinGroup(groupAddress);

MulticastSocket client = new MulticastSocket(port);
client.joinGropu(groupAddress);

// �������UDP/IP��ͨ�Ŵ���
````

## Mina������Ϣ��ʽͨ�ŵĿ�Դ���
����NIO��ͬʱ֧��TCP/IP��UDP/IPЭ�顣<br/>
1. ��ɲ���<br/>
`IoConnector`
`IoAcceptor`
`IoHandler`
`IoSession`
2. ����Filter Chain��ʽ������Ϣ�Ĵ������ͺͽ��յȹ�����<br/>

## Զ�̵���
��Ҫ������RMI��WebService��<br/>

## �ֲ�ʽ
1. ����<Br/>
�����ͨ�š���Ⱥ�����ؾ��⡢�ֲ�ʽ���桢�ֲ�ʽ�ļ�ϵͳ�ȡ�<br/>
2. �ֲ�ʽ������������<Br/>
����༶���ô�������ʱ������/�������ѡ����ߵİ�ȫ/���Ҫ������Ӧ����ֲ��Qos��֧�֡��߿��ú͸߶ȿ���������汾����������<br/>
3. SOA��׼SCA(Service Component Architecture)<br/>
  ����SOA������һ��˼�룬����ʵ�ַ���ָ�������IBM��Oracle�Ⱦ�ͷ�����������ƶ�SCAʵ�ֹ淶��<br/>

