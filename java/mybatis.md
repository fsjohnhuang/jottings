## 示例
src/config.xml文件<br/>
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "...............">
<configuration>
  <!--
    default属性用于指定默认使用哪个运行模式
  -->
  <environments default="dev">
    <!--
      id属性用于标识运行模式
    -->
    <environment id="dev">
      <!--
        type属性用于指定事务管理器类型
           JDBC：使用JDBC的提交和回滚设置，依赖从数据源获取的连接来管理事务范围。
           MANAGED：让容器（如Spring）来管理事务的生命周期。默认情况会关闭连接，若不想关闭连接则需要如下配置：
	<transactionManager type="MANAGED">
	  <property name="closeConnection" value="false"/>
        </transactionManager>
      -->
      <transactionManager type="JDBC"/>
      <!--
         type属性用于指定连接池类型
           UNPOOLED：连接用完就关闭，不放到连接池
           POOLED：连接用完则放在连接池
      -->
      <dataSource type="POOLED">
        <property name="driver" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/test"/>
        <property name="username" value="fsjohnhuang"/>
        <property name="password" value="root"/>
      </dataSource>
    </environment>
  </environments>
  <!--
    注册映射文件
  -->
  <mappers>
    <mapper resource="映射文件的相对路径，以src作为参考点"/>
  </mappers>
</configuration>
````
实体类<br/>
````
public class User{
  private int id;
  private String name;
  
  public int getId(){
    return id;
  }
  public void setId(int val){
    id = val;
  }
  public int getName(){
    return name;
  }
  public void setName(String val){
    name = val;
  }
}
````
映射文件(userMapper.xml)<br/>
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC ".........">
<mapper namespace="实体类的包名.userMapper">
  <select id="getUser" parameterType="int" resultType="实体类的全限定类名">
     select * from users where id=#{id}
  </select>
</mapper>
````
主文件<br/>
````
public class Test{
  public static void main(String[] args){
    String conf = "config.xml";
    InputStream is = Test.class.getClassLoader.getResourceAsStream(conf); 
   
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
    // 默认时手动提交
    SqlSession session = factory.openSession();

    /* statement可以直接设置为select标签的id属性值，mybatis将自行搜索配置项
     * 但若多个select标签id属性值相同的情况，会抛异常
    String statement = "映射文件的namespace属性值.select标签的id属性值";
    User user = session.selectOne(statement, 2);
  }
}
````

## 基于XML映射文件的CRUD示例
映射文件<br/>
````
<!--新增-->
<insert id="addUser" parameterType="实体类的全限定类名">
  insert into users(name) values(#{name})
</insert>
<!--删除-->
<delete id="deleteUser" parameterType="int">
  delete from users where id=#{id}
</delete>
<!--更新-->
<update id="updateUser" parameterType="实体类的全限定类名">
  update users set name=#{name} where id=#{id}
</update>
<!--查询多个结果对象-->
<select id="selectUsers" returnType="实体类的全限定类名">
  select * from users
</select>
````
主文件<br/>
````
// 新增
User user = new User();
user.setId(1);
user.setName("fsjohnhuang");
session.insert("映射文件的namespace属性值.addUser", user);
session.commit();
// 更新
User user = new User();
user.setId(1);
user.setName("fsjohnhuang");
session.update("映射文件的namespace属性值.updateUser", user);
session.commit();
// 删除
int deletedCount = session.delete("映射文件的namespace属性值.deleteUser", 1);
session.commit();
// 查询多个结果对象
List<User> users = session.selectList("映射文件的namespace属性值.selectUsers");
session.close();
````

## 基于注解的CRUD示例
映射类<br/>
````
public class UserMapper{
  @Insert("insert into users(name) values(#{name})")
  public int addUser(User user);
  @Delete("........");
  public int deleteUser(int id);
  @Update("........");
  public int updateUser(User user); 
  @Select("........");
  public User getUser(int id);
  @Select("........");
  public List<User> getUsers(int id);
}
````
config.xml文件中注册映射类<br/>
````
<mapper class="映射类全限定类名"/>
````
主文件<br/>
````
UserMapper mapper = session.getMapper(UserMapper.class);
mapper.addUser(new User(1, "fsjohnhuang"));
session.close();
````

## xml映射与接口相结合
xml映射文件作为statement定义，接口作为调用接口。然后将xml映射文件的`namespace`设置为接口的全限定类名关联两者。<br/>
xml映射文件test/dao/themeMgr.xml<br/>
````
<mapper namespace="test.dao.ThemeMgr">
  <select id="getIds" returnType="java.lang.Long">
    select id from Theme_ML
  </select>
</mapper>
````
接口<Br/>
````
package test.dao
public interface ThemeMgr{
  List<Long> getIds();
}
````
配置文件<br/>
````
<mappers>
  <mapper resource="test/dao/themeMgr.xml"/>
</mappers>
````
主程序<br/>
````
InputStream is = this.getClass().getClassLoader().getResourceAsStream("配置文件路径");
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
SqlSession session = factory.openSession();
try{
  ThemeMgr tm = session.getMapper(ThemeMgr.class);
  List<Long> ids = tm.getIds();
}
finally{
  session.close();
}
````

## 优化
1. **将数据库配置信息外移到properties文件**<br/>
数据库配置文件com/test/db.properties
````
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test
username=fsjohnhuang
password=root
````
config.xml
````
<configuration>
  <properties resource="com/test/db.properties"/>
  <!--
    在使用实例化SqlSessionFactory时，还可以通过new SqlSessionFactoryBuilder.build(config.xml的InputStream实例, Properties实例)来设置属性值。
    优先级从高到低是：
	1. 通过build方法入参设置
	2. 通过resource引入的属性
	3. 通过property标签设置的属性
  -->
  <properties resource="db.properties">
    <property name="url" value="sdb"/>
  </properties>
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC"/>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
</configuration>
````
2. **配置实体类别名**<br/>
config.xml文件中<br/>
````
  <configuration>
     <!--
       若配置文件中存在properties节点，那么typeAliases节点必须在其后，否则会报错
     -->
     <typeAliases>
	<typeAlias type="实体类全限定名" alias="别名"/>
        <!--
	  这时对于包下的类，我们只需写类名或首字母小写的类名
          当配合@Aliase("别名")使用时，只需写别名即可，且不区分大小写
	  <package name="包名"/>
	-->
     </typeAliases>
  </configuration>
````
内置别名<br/>
`int`,`long`等就是`_int`,`_long`<br/>
而`Integer`,`String`就是`int`,`string`<br/>
3. **添加写日志功能**<br/>
采用log4j

## 解决字段名和属性名不相同时的冲突 
1. 通过sql语句中起别名来处理<br/>
2. 通过ResultMap属性处理<br/>
映射文件中<br/>
````
<mapper>
  <select id="selectUsers" resultMap="getUser2Map">
    select * from users
  </select>
  <resultMap type="实体类全限定类名" id="getUser2Map">
    <!--id标签用于主键-->
    <id property="id" column="user_id"/>
    <!--result标签用于一般字段-->
    <result property="name" column="user_name"/>
  </resultMap>
</mapper>
````

## 一对一关联表
1. **嵌套结果**<br/>
实际上就是连表查询，并去除重复记录。<Br/>
映射文件内<Br/>
````
<select id="getClass" parameterType="int" resultMap="ClassResultMap">
  select * from class c inner join teacher t on(c.tid = t.id) where t.id = #{id}
</select>
<resultMap id="ClassResultMap" type="Cls">
  <id property="id" column="c_id"/>
  <result property="name" column="c_name"/>
  <association prorperty="teacher" javaType="Teacher">
    <id property="id" column="t_id"/>
    <result property="name" column="t_name"/>
  </association>
</resultMap>
````
实体类<br/>
````
public class Teacher{
  private int id;
  private String name;
  getter/setter;
}
public class Cls{
  private int id;
  private String name;
  private Teacher teacher;
  getter/setter;
}
````
2. **嵌套查询**<br/>
````
<select id="getClass" parameterType="int" resultMap="ClassResultMap">
  select * from class where tid = #{id}
</select>
<select id="getTeacher" parameterType="int" resultType="Teacher">
  select t_id id, t_name name from teacher where t_id = #{id}
</select>
<resultMap id="ClassResultMap" type="Cls">
  <id property="id" column="c_id"/>
  <result property="name" column="c_name"/>
  <association prorperty="teacher" column="tid" select="getTeacher">
  </association>
</resultMap>
````

## 一对多关联表
1. **嵌套结果**<br/>
映射文件<br/>
````
<select id="getClass" parameterType="int" resultMap="ClassResultMap">
  select * from class c inner join student s on(c.id = s.cid) where c.id = #{id}
</select>
<resultMap id="ClassResultMap" type="Cls">
  <id property="id" column="c_id"/>
  <result property="name" column="c_name"/>
  <collection prorperty="students" ofType="Student">
    <id property="id" column="s_id"/>
    <result property="name" column="s_name"/>
  </collection>
</resultMap>
````
实体类<Br/>
````
public class Student{
  private int id;
  private String name;
  getter/setter;
}
public class Cls{
  private int id;
  private String name;
  private Teacher teacher;
  private List<Student> students;
  getter/setter;
}
````
2. **嵌套查询**<br/>
映射文件<br/>
````
<select id="getClass" parameterType="int" resultMap="ClassResultMap">
  select * from class where tid = #{id}
</select>
<select id="getStudents" parameterType="int" resultType="Student">
  select s_id id, s_name name from student where cid = #{id}
</select>
<resultMap id="ClassResultMap" type="Cls">
  <id property="id" column="c_id"/>
  <result property="name" column="c_name"/>
  <collection prorperty="students" column="c_id" select="getStudents">
  </collection>
</resultMap>
````

## 动态SQL和模糊查询
映射文件中<br/>
````
<select>
 select * from tbl where 1=1
 <if test="title != null">
   and title like #{title}
 </if>
</select>
````
还有`<choosen>,<when>,<otherwise>,<trim>,<where>,<set>,<foreach>`<Br/>

## 存储过程
映射文件<br/>
````
<select id="getUserCount" parameterMap="getUserCountMap" statementType="CALLABLE">
  CALL get_user_count(?, ?)
</select>
<parameterMap type="java.util.Map" id="getUserCountMap">
  <parameter property="sexid" mode="IN" jdbcType="INTEGER"/>
  <parameter property="usercount" mode="OUT" jdbcType="INTEGER"/>
</parameterMap>
````
主文件<br/>
````
Map<String, Integer> pm = new HashMap<String, Integer>();
pm.put("sexid", 1);
pm.put("usercount", -1);
session.selectOne("getUserCount", pm);
Integer rs = pm.get("usercount");
session.close();
````

## 一级和二级缓存
1. **一级缓存(默认开启)**<br/>
作用域为session对象(就是在同一个session上执行的查询语句将从缓存中执行), 缓存存储源为基于PerpetualCache的HashMap。当调用session的clearCache、flush或close方法后，缓存将被清除。当执行CUD操作后，所有select缓存均被清除。<Br/>
2. **二级缓存(默认关闭)**<br/>
作用域是映射文件（就是一namespace为作用域）,缓存存储源可自定义（如Ehcache），但默认是使用基于PerpetualCache的HashMap。当执行CUD操作后，所有select缓存均被清除。<Br/>
启动二级缓存<br/>
a. 修改实体类<Br/>
````
// 实体类需要继承可序列化类
public class user implements Serializable{}
````
b. 修改config.xml<Br/>
````
<settings>
  <setting name="cacheEnabled" value="true"/>
</settings>
````
c. 修改映射文件<Br/>
````
<mapper>
  <!--
    cahce标签用于表示该mapper使用二级缓存，具体属性如下：
      1.eviction，回收策略
          LRU(默认), 移除最长时间不用的对象
 	  FIFI，先进先出
          SOFT，移除基于垃圾回收起状态和软引用规则的对象
	  WEAK，移除基于垃圾回收起状态和弱引用规则的对象
      2.flushInterval，刷新缓存的时间，单位为ms，默认不刷新。      
      3.size，缓存的最大对象数，默认为1024
      4. readOnly，设置返回缓存的深拷贝还是句柄，true表示句柄（不能修改，性能更好），false表示深拷贝（可修改，安全性好些，但性能差些）,默认使用false
  -->
  <cache/>
</mapper>
````

## 生命周期
**1.`SqlSessionFactoryBuilder`**<br/>
  由于`SqlSessionFactoryBuilder`实例用于生成`SqlSessionFactory`实例而已，因此并没有必要以应用程序全局作为作用域，并且无必要多线程共享。因此作为函数的局部变量使用即可。<br/>
**2.`SqlSessionFactory`**<br/>
  作为数据库连接池和连接池管理器使用，为达到数据库连接复用的效果，`SqlSessionFactory`实例应当以程序全局作为作用域，并且多线程共享。采用单例或静态单例模式较好<br/> 
**3.`SqlSession`**<br/>
  由于`SqlSession`实例非线程安全，因此作为函数的局部变量使用。而且由于数据库连接为共享资源，因此必须遵循晚调用早释放原则, 确保调用`close()`函数释放连接。<Br/>
````
try{
}
finally{
  sqlSession.close();
}
````

##  输出sql语句日志
log4j.rootLogger=DEBUG就可以看到了

## mybatis-cfg.xml的mappers节点
  用于向mybatis注册sql语句映射信息。mybatis3.2.1起提供4种方法<br/>
````
/** Mapper的xml配置文件 **/
// 1. 通过mapper标签resource属性，指定相对于根类路径下的资源
<mapper resource="com/test/mapper/UserMapper.xml"/>

// 2. 通过mapper标签url属性
<mapper url="file:///E:/project/bin/com/test/mapper/UserMapper.xml"/>

/** Mapper接口 **/
// 3. 通过mapper标签class属性
<mapper class="com.test.mapper.UserMapper"/>

// 4. 通过package标签name属性
<package name="com.test.mapper"/>
````

## mybatis-cfg.xml的typeAliases节点
  用于为mapper中使用的类定义别名，从而不用使用全限定类型。<Br/>
````
<typeAliases>
  // 1. 使用typeAliases标签逐个添加 
  <typeAlias type="com.test.entity.EUser" alias="EUser"/>
 
  // 2. 使用package标签批量添加
  <package name="com.test.entity"/>
</typeAliases>
````


## mybatis与Spring整合
