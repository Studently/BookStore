package cn.eud.stu.order.web.servlet;

import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.order.domain.Order;
import cn.eud.stu.order.exception.OrderExcetion;
import cn.eud.stu.order.service.OrderService;
import cn.eud.stu.user.domian.User;
import cn.eud.stu.cart.domain.Cart;
import cn.eud.stu.cart.domain.CartItem;
import cn.eud.stu.order.domain.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name="OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    //依赖
    private OrderService service=new OrderService();


    /**
     * 确认订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{
        /**
         * 1、获取订单id参数
         * 2、调用service的confirm函数
         *      如果抛出异常，保存异常信息到request中
         *      否则保存正确信息到request中
         * 3、转发到msg.jsp中
         */

        String oid=request.getParameter("oid");

        try {
            service.confirm(oid);
            request.setAttribute("msg","恭喜，交易成功！");
        } catch (OrderExcetion e) {
           request.setAttribute("msg", e.getMessage());
        }

        return "f:/jsps/msg.jsp";

    }

    /**
     * 加载订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{

        //获得订单订单id
        String oid=request.getParameter("oid");
        //查询订单信息
        Order order=service.findByOid(oid);
        //保存到request中
        request.setAttribute("order",order);
        return "f:/jsps/order/desc.jsp";
    }


    /**
     * 处理我的订单页面
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        /**
         * 点击我的订单
         * 1、通过当前session得到当前用户
         * 2、通过用户的uid，查询得到订单列表
         * 3、将订单列表保存到request中，转发到/jsps/order/list.jsp显示
         */
        User user= (User) request.getSession().getAttribute("user");
        //通过uid查询订单列表
        List<Order> orderList=service.findByUid(user.getUid());
        request.setAttribute("orderList",orderList);

        return "f:/jsps/order/list.jsp";

    }


        /**
         * 添加订单
         * @param request
         * @param response
         * @return
         * @throws ServletException
         * @throws IOException
         */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 添加订单
         * 1、获得购物车
         * 2、得到订单对象：将购物车对应到订单中
         * 3、得到订单项对象，并将订单项添加到订单中：将购物车中的购物项对应到订单项中
         * 4、清空购物车
         * 5、调用service的add方法，将订单添加到数据库中
         * 6、转到付款页面
         */
        Cart cart= (Cart) request.getSession().getAttribute("cart");

        //创建订单对象
        Order order=new Order();
        //设置订单中的内容
        order.setOid(CommonUtils.uuid());
        order.setOrdertime(new Date());
        order.setState(1);
        order.setTotal(cart.getTotal());
        //获得当前用户
        User user= (User) request.getSession().getAttribute("user");
        order.setOwnUser(user);


        //创建订单项
        List<OrderItem> orderItemList=new ArrayList<>();

        //遍历购物项，来新建订单项
        for (CartItem item:cart.getCartItems()) {

            //新建订单项
            OrderItem orderItem=new OrderItem();
            orderItem.setIid(CommonUtils.uuid());
            orderItem.setCount(item.getCount());
            orderItem.setSubtotal(item.getSubtotal());
            orderItem.setOrder(order);
            orderItem.setBook(item.getBook());

            //添加到orderItemList
            orderItemList.add(orderItem);
        }

        //将订单项添加到订单中
        order.setOrderItemList(orderItemList);
        //清空购物车
        cart.clear();

        //添加订单
        service.add(order);

        //将order保存到request域中，转发到/jsps/order/desc.jsp
        request.setAttribute("order",order);

        return "f:/jsps/order/desc.jsp";
    }
}
