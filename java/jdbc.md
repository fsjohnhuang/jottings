## 获取数据链接`Connection`对象
JDO,ORM均基于JDBC<br/>
JDBC驱动:各DB厂商实现JDBC接口的实现类<br/>
分类：<br/>
1. JDBC-ODBC桥<br/>
2. 部分本地API，部分JAVA的驱动<br/>
3. JDBC网络纯JAVA驱动程序<br/>
4. 本地协议的纯JAVA驱动程序<br/>

**JDBC URL格式**
mysql:`jdbc:mysql(子协议)://主机IP:端口号/数据库名`<br/>
oracle:`jdbc:oracle(子协议):thin:@主机IP:端口号:数据库名`<br/>
MSSQL:`jdbc:microsoft:sqlserver//主机IP:端口号;DatabaseName=数据库名`<br/>
SQLite:`jdbc:sqlite:数据库文件路径`<br/>

`Driver Manager`
`Connection`<->`Statement`<->`Result`
`Connection`<->`PreparedStatement`(预编译)<->`Result`
`Connection`<->`CallableStatement`(存储过程)<->`Result`

`java.sql.Driver`是所有JDBC驱动程序均需要实现的接口，java程序中一般不直接使用该接口。而是使用`java.sql.DriverManager`获取驱动程序实例。<br/>
1. 直接使用`java.sql.Driver`获取Connection<br/>
````
public void testDriver() throws SQLException{
  // 与具体的jdbc驱动程序紧耦合
  Driver driver = new com.mysql.jdbc.Driver();

  String url = "jdbc:mysql://localhost:3306/test";
  Properties props = new Properties();
  props.pus("user", "root");
  props.pus("password", "1230");

  Connection conn = driver.connect(url, info);
}
// 通过反射解耦
public Connection getConn() throws Exception{
  String driverCls = jdbcUrl = user = password = null;
  Driver driver = (Driver)Class.forName(driverCls).newInstance();

  Properties props = new Properties();
  props.pus("user", "root");
  props.pus("password", "1230");

  // 若url和jdbc驱动程序不匹配，则conn为null
  Connection conn = driver.connect(url, info);
  return conn;
}
````
2. 使用`java.sql.DriverManager`获取Connection<br/>
````
public void testDriverManager(){
  // 加载jdbc驱动程序
  // 完整的做法是，DriverManager.registerDriver(Class.forName(driverCls).newIntance())
  // 但由于驱动程序内部已经通过静态代码块进行上述操作，因此只需执行下列代码即可
  Class.forName(driverCls1);
  // 加载注册多个jdbc驱动程序，然后通过jdbcUrl来获取不同的jdbc驱动程序的Connection
  Class.forName(driverCls2);
  Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
}
````

## `Statement`对象
作用：通过SQL语句执行对数据库的操作
````
public void testStatement(Connection conn)
  throws Exception{
  Statement statement = null;
  try{
        String sql = "INSERT INTO tbl VALUES('test')";
        statement = conn.createStatement();
        statement.executeUpdate(sql);
  }
  catch(Exception e){
  }
  finally{
    try{
      if (statement != null)
        statement.close();
    }
    catch(Exception e){
      if (conn != null)
        conn.close();
    }
  }
}
````
## `ResultSet`对象
作用：维护一个指向当前行的游标，初始化时游标指向第一个行之前。<br/>
````
public void testResultSet(Connection conn){
  Statement statement = null;
  ResultSet rs = null;
  String sql = "SELECT * FROM tbl";
  try{
    statement = conn.createStatement();
    rs = statement.executeQuery(sql);
    // 游标初始时指向第一行记录之前
    while(rs.next()){
      // 列索引从1开始
      System.out.println(rs.getInt(1));
      System.out.println(rs.getString(2));
      // 或者通过列名称获取值
      // System.out.println(rs.getString("Name"));
    }
  }
  catch(Exception e){
  }
  finally{
    try{
      if (rs != null)
        rs.close();
    }
    catch(Exception e){
    }
    try{
      if (statement != null)
        statement.close();
    }
    catch(Exception e){
    }
    if (conn != null)
      conn.close();
  }
}
````

## `PreparedStatement`对象
作用：解决sql语句拼接问题，防止SQL注入<br/>
````
String sql = "INSERT INTO tbl VALUES(?,?,?)";
PreparedStatement ps = conn.prepareStatement(sql);
// 设置第一个？号占位符
ps.setInt(1, 12);
// 设置第二个？号占位符
ps.setString(2, "fsjohnhuang");
// 设置第三个？号占位符
ps.setDate(3, new Date(new java.util.Date().getTime()));
ps.executeUpdate();
````

## 结果集元数据`ResultSetMetaData`
作用：描述ResultSet的元数据对象，从中可以获取的结果集有多少列，列名等信息。<br/>
````
// 获取ResultSetMetaData
ResultSet rs = .....
ResultSetMetaData meta = rs.getMetaData();

// 获取列数量
int colCount = meta.getColumnCount();

// 获取制定的列的别名，索引从1开始
String colLabel = meta.getColumnLabel(int colIndex);
// 获取制定的列名，索引从1开始
String colName = meta.getColumnName(int colIndex);
````

**DAO: Data Access Object**<br/>
操作数据库的行为类
**Entity：实体对象**<br/>
通过getter和setter来定义属性，属性就是get/set方法去除get/set后首字母小写就是属性名。<br/>
而直接在类内定义的变量称为成员变量。<br/>

##`org.apache.commons.beanutils.BeanUtils`操作JavaBean
是Apache的jar包<br/>
````
public class DAO{
  private String name;
  public void setName(String val){
    name = val;
  }
}
public static void main(String[] args){
  DAO dao = new DAO(); 
  BeanUtils.setProperty(dao, "name", "fsjohnhuang");
  String name = (String)BeanUtils.getProperty(dao, "name");
}
````

## `DataBaseMetaData`获取JDBC元数据
获取数据库中各个表、表中各列、数据类型、触发器、存储过程等数据库的所有信息。<Br/>
````
Connection conn = .....
// 获取DataBaseMetaData
DataBaseMetaData dbMeta = conn.getMetaData();

// 数据库主版本号
int version = dbMeta.getDatabaseMajorVersion();

// 链接用户名
String user = dbMeta.getUserName();

// 数据库
ResultSet dbs = dbMeta.getCatalogs();
````

## 插入记录并获取记录主键值
````
public int insert(){
  Integer id = null;
  String sql = "INSERT INTO tbl VALUES('fsjohnhuang')";
  PreparedStatment ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
  ps.executeUpdate();
  ResultSet rs = ps.getGeneratedKeys();
  if (rs.next()){
   id = rs.getInt(1); 
  }
  return id;
}
````
## 处理Blob数据类型
LOB(就是Large Object，大对象)，用来存储大量的二进制和文本数据的数据类型（LOB字段可存储4GB的数据）<br/>
LOB分类：<br/>
1. **内部LOB**<br/>
以字节流形式将数据存储在数据库内部，因此对该数据的操作均可参与事务，也可像普通数据那样进行备份和恢复。细分为<br/>
[a]. BLOB(二进制数据),存储如图像、视频等<br/>
[b]. CLOB(单字节字符数据),存储超长文本数据<br/>
[c]. NCLOB(多字节字符数据),存储超长文本数据<br/>
2. **外部LOB**<br/>
目前只有一种外部LOB类型——BFILE类型。数据的实体部分以文件的形式保存在OS的文件系统上，而数据库字段仅保存数据位于文件系统的位置信息。该类型的数据无法参与事务。<br/>
**MySQL的BLOB**
1. TinyBlob, 255B<br/>
2. Blob, 25KB<br/>
3. MediumBlob, 16MB<br/>
4. LongBlob, 4GB<br/>
**实例**<br/>
````
InputStream blobData = .....
// 插入数据时，直接设置即可
ps.setBlob(1, blobData);

// 读取
Blob blobData = rs.getBlob(1);
InputStream is = blobData.getBinaryStream();
````

## 事务
多个操作需要在相同的链接中才能加入同一个事务中。<br/>
````
Connection conn = ....
try{
	// 设置事务的隔离级别, 各种数据库所支持的级别类型和默认值都不尽相同
	// Oracle只支持READ_COMMITED(默认)和Serialized
	// MySql就支持READ_UNCOMMITED,READ_COMMITED,REPEATED_READ(默认)和Serialized四种级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITED);
	// 取消默认提交行为
	conn.setAutoCommit(false);
	// 各种数据库操作
        // 各种数据库操作成功后，手动执行
	conn.commit();
}
catch(Exception e){
	// 回滚
        conn.rollback();

}
````
## 批量处理
````
String sql = "INSERT INTO tbl VALUES(?)";
PreparedStatement ps = conn.prepareStatement(sql);
for (int i = 0, len = 1000; i < len; ++i){
  ps.setString(1, "name" + i);
  
  // 缓存起来
  ps.addBatch();
  // 每300条则执行一次并清理缓存
  if ((i+1) % 300 == 0){
     // 若使用Statement，则executeBatch()需要将sql作为入参
     ps.executeBatch();
     ps.clearBatch();
  }
}
if (1000 % 300 != 0){
  ps.executeBatch();
  ps.clearBatch();
}
````

## dbcp数据库链接池
连接池使用接口`javax.sql.DataSource`(包含连接池和连接池管理两部分)表示，具体实现由应用服务器(Tomcat等)提供。<br/>
开源组织提供的常用有两种：<br/>
1. DBCP数据库连接池<Br/>
位于`org.apache.commons.dbcp`包位中<br/>
````
DataSource ds = new BasicDataSource();
// 配置必要属性
ds.setUsername("root");
ds.setPassword("123");
ds.setUrl("jdbc:mysql://localhost:3305/test");
ds.setDriverClassName("com.mysql.jdbc.Driver");

// 配置可选属性
ds.setInitialSize(10) // 设置初始化链接数
ds.setMaxActive(10) // 设置最大链接数
ds.setMinIdle(5) // 设置最小链接数
ds.setMaxWait(1000) // 设置链接池分配链接的最长时间ms

Connection conn = s.getConnection();
````
更好的调用方式<br/>
````
// 通过Properties读取配置文件的属性来创建DataSource
DataSource ds = BasicDataSourceFactory.createDataSource(Properties实例);
Connection conn = ds.getConnnection();
````
2. C3P0数据库连接池(hibernat默认的数据库连接池)<br/>
````
ComboPooledDataSource ds = new ComboPooledDataSource();
ds.setUser("root");
ds.setPassword("123");
ds.setJdbcUrl("jdbc:mysql://localhost:3305/test");
ds.setDriverClass("com.mysql.jdbc.Driver");
```
使用xml配置文件
````
ComboPooledDataSource ds = new ComboPooledDataSource("配置文件中name-config标签的name属性值");
````

## 调用存储过程和函数
通过`CallableStatment`调用。
````
// 存储过程就这样"{存储过程名(?,?)}"
String sql = "{?= 函数名(?, ?)}";
// 获取CallableStatement对象
CallableStatement cs = conn.prepareCall(sql);
// 注册OUT类型参数
cs.registerOutParameter(1, java.sql.Type的数据类型);
cs.registerOutParameter(3, java.sql.Type的数据类型);
// 对IN类型参数赋值
cs.setInt(2, 80);
// 执行
cs.execute();
// 获取返回值
double val1 = cs.getDouble(1);
String val3 = cs.getString(3);
````

## Apache的DBUtils
对jdbc进行简单的封装，性能比orm框架要好。

##JDBC，DBUtils，MyBatis和Hibernate比较
JDBC：其他操作数据库方法的基石，性能最高。但需要自行组装sql语句，封装查询结果,和进行优化工作。<br/>
DBUtils：对JDBC进行简单封装。需要自行组装sql语句，工具库会自动完成查询结果的封装工作，工具库也会进行数据库链接池等的优化工作。<Br/>
MyBatis：自行组装查询的SQL语句，而新增、修改的等操作均由框架根据实体类的映射元数据生成SQL语句，SQL语句外置到xml配置文件中，框架会自动完成查询结果的封装工作，工具库也会进行数据库链接池等的优化工作。<br/>
Hibernate：几乎不用写SQL，面向对象的方式操作数据库。<br/>
