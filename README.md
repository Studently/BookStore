# BookStore
网上商城：前端：查询图书，购买图书等；后端：上传图书，删除图书等

这个项目是观看智传播客的崔凡希老师的javaWeb课程敲的。
注册

![image](https://github.com/Studently/BookStore/blob/master/web/reference/login.gif "gif效果演示")

用户前端操作

![image](https://github.com/Studently/BookStore/blob/master/web/reference/user.gif "gif效果演示")

管理员后端操作

![image](https://github.com/Studently/BookStore/blob/master/web/reference/admin.gif "gif效果演示")


 首先是一些配置文件的使用
 c3p0-config.xml
 
    <c3p0-config>
      <!-- 这是默认配置信息 -->
      <default-config>
      <!-- 连接四大参数配置 -->
        <property name="jdbcUrl">jdbc的url</property>
        <property name="driverClass">数据库驱动类名r</property>
        <property name="user">数据库的用户名</property>
        <property name="password">数据库密码</property>
        <!-- 池参数配置 -->
        <property name="acquireIncrement">3</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">2</property>
        <property name="maxPoolSize">10</property>
       </default-config>
     </c3p0-config>

  email_template.properties

    host=smtp.qq.com（邮箱服务器名称）
    username=905379100（用来发送邮件的用户名）
    password=授权码（用来发送邮件的邮箱的密码：需要注意，这里的密码是邮箱网站设置的授权码）
    from=905379100@qq.com（用于发送邮件的邮箱）
    subject=\u6211\u7684\u5546\u57CE\u6FC0\u6D3B\u90AE\u4EF6 （邮件标题）
    content=<a href="http://localhost:8080/BookStore/UserServlet?method=active&code={0}">\u70B9\u51FB\u8FD9\u91CC\u6FC0\u6D3B</a>（邮件内容）


