## 示例
src/config.xml文件<br/>
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "...............">
<configuration>
  <environments default="development">
    <!--
      id属性用于指定运行模式
        development：开发模式
        work：工作模式
    -->
    <environment id="development">
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
    <mapper resource="映射文件路径(通过qualify name获取)"/>
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
<update id="updateUser" parameterTYpe="实体类的全限定类名">
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

## 优化
1. **将数据库配置信息外移到properties文件**<br/>
数据库配置文件db.properties(config.xml和db.properties同级目录）
````
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test
username=fsjohnhuang
password=root
````
config.xml
````
<configuration>
  <properties resource="db.properties"/>
  <!--
    内部的property标签用于覆盖properties文件中的同名属性
    <properties resource="db.properties">
      <property name="" value=""/>
    </properties>
  -->
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
     <typeAliases>
	<typeAlias type="实体类全限定名" alias="别名"/>
        <!--
	  或通过package标签来自动补全实体类的全限定名（我们只需提供实体类名就可以了）
	  <package name="包名"/>
	-->
     </typeAliases>
  </configuration>
````
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
<select id="getTeacher" parameterType="int" returnType="Teacher">
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
<select id="getStudents" parameterType="int" returnType="Student">
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
