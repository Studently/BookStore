package cn.eud.stu.user.web.servlet;



import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.mail.Mail;
import cn.edu.stu.tools.mail.MailUtils;
import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.cart.domain.Cart;
import cn.eud.stu.user.domian.User;
import cn.eud.stu.user.exception.UserException;
import cn.eud.stu.user.service.UserService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 用户操作层
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {

    //添加依赖
    private UserService userService=new UserService();



    public String active(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 根据激活码激活
         * 1、获得激活码参数
         * 2、调用userservice的actice()方法激活
         *      (1)如果失败，保存错误信息到request中，并转发到msg.jsp
         *      (2)如果成功，保存正确信息到request中，并转发到msg.jsp
         */

        String code=request.getParameter("code");

        try {
            userService.active(code);
            request.setAttribute("msg","恭喜，激活成功,请登录！");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserException e) {
            request.setAttribute("msg",e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }


    /**
     * 注册函数
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String regist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, MessagingException {
        /**
         * 1、将表单数据封装到user对象中,并设置uid和code
         * 2、校验表单数据，错误信息封装到map中保存到request中，转发给regist.jsp
         * 3、调用userService的regist()函数，注册
         * 4、注册成功后，发送邮件
         * 5、保存成功信息到request中，转发到msg.jsp
         */

        //将表单数据封装到user对象中
        User form= CommonUtils.toBean(request.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid()+CommonUtils.uuid());

        Map<String,String>error=new HashMap<>();

        //验证用户名
        String username=form.getUsername();
        if (username == null||username.trim().isEmpty()) {
            error.put("username","用户名不能为空！");
        }else if(username.length()<3||username.length()>16){
            error.put("username","用户名长度必须在3-16之间");
        }

        //验证密码
        String password=form.getPassword();
        if (password == null||password.trim().isEmpty()) {
            error.put("password","密码不能为空！");
        }else if(password.length()<3||password.length()>16){
            error.put("password","密码长度必须在3-16之间");
        }

        //验证邮箱
        String email=form.getEmail();
        if (email == null||email.trim().isEmpty()) {
            error.put("email","邮箱不能为空！");
        }else if(email.matches("//w+@//w+.//w+")){
            error.put("email","邮箱格式错误");
        }

        if (error.size()>0) {
            //将错误信息保存到request中
            request.setAttribute("error",error);
            request.setAttribute("form",form);
            //转发到regist.jsp页面
            return "f:/jsps/user/regist.jsp";
        }

        try {
            //注册
            userService.regist(form);
        } catch (UserException e) {
            //将错误信息保存到request中
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("form",form);
            //转发到regist.jsp页面
            return "f:/jsps/user/regist.jsp";
        }

        /**
         * 发送邮件
         * 1、获得配置文件内容
         * 2、使用mail小工具
         */
        //获得配置文件对象
        Properties pro=new Properties();
        //加载配置文件
        pro.load(this.getClass().getResourceAsStream("/email_template.properties"));
        //服务器主机
        String host=pro.getProperty("host");
        //用户名
        String uname=pro.getProperty("username");
        //密码
        String pwd=pro.getProperty("password");
        //发件人
        String from=pro.getProperty("from");
        //主题
        String subject=pro.getProperty("subject");
        //内容
        String content=pro.getProperty("content");

        //替换内容换位符
        content= MessageFormat.format(content,form.getCode());

        //获得session对象
        Session session= MailUtils.createSession(host,uname,pwd);
        Mail mail=new Mail(from,form.getEmail(),subject,content);
        //发送邮件
        MailUtils.send(session,mail);


        //保存成功信息到request，转发到msg.jsp
        request.setAttribute("msg","恭喜，注册成功，请赶快到邮箱激活账号！");
        return "f:/jsps/msg.jsp";

    }


    /**
     * 登陆
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        /**
         * 登陆
         * 1、将表单数据封装到user对象中
         * 2、校验用户名和密码
         * 3、调用UserService的login()函数
         *      （1）如果有异常，将异常信息保和表单信息存到request，转发到login.jsp页面
         *      （2）如果成功，保存用户信息到session中，重定项到index.jsp
         */

        User form=CommonUtils.toBean(request.getParameterMap(),User.class);

        /**
         * 校验用户名和密码
         */
        Map<String,String>error=new HashMap<>();
        //验证用户名
        String username=form.getUsername();
        if (username == null||username.trim().isEmpty()) {
            error.put("username","用户名不能为空！");
        }

        //验证密码
        String password=form.getPassword();
        if (password == null||password.trim().isEmpty()) {
            error.put("password","密码不能为空！");
        }

        if (error.size()>0) {
            //将错误信息保存到request中
            request.setAttribute("error",error);
            request.setAttribute("form",form);
            //转发到regist.jsp页面
            return "f:/jsps/user/login.jsp";
        }


        try {
            User user =userService.login(form);

            //登陆成功，将用户信息保存到session中
            request.getSession().setAttribute("user",user);
            //在session中添加一辆购物车
            request.getSession().setAttribute("cart",new Cart());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserException e) {
            //保存异常信息和表单信息到request
             request.setAttribute("msg",e.getMessage());
             request.setAttribute("form",form);
             return "f:/jsps/user/login.jsp";
        }
        //重定向到主页
        return "r:"+request.getContextPath()+"/index.jsp";
    }


    /**
     * 退出登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String quit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        //销毁session
        request.getSession().invalidate();

        //重定向到index.jsp
        return "r:"+request.getContextPath()+"/index.jsp";
    }

}
