package cn.eud.stu.user.web;

import cn.eud.stu.user.domian.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class userFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 从session中得到user信息
         *      如果存在，放行
         *      否者，保存错误信息，转发到登陆界面：/jsps/user/login.jsp
         */
        HttpServletRequest request= (HttpServletRequest) req;
        User user= (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg","请先登陆系统！");
            request.getRequestDispatcher("/jsps/user/login.jsp")
                    .forward(request,resp);
        }else{
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
