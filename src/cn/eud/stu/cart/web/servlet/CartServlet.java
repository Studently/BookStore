package cn.eud.stu.cart.web.servlet;


import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.book.dao.BookDao;
import cn.eud.stu.book.domain.Book;
import cn.eud.stu.cart.domain.Cart;
import cn.eud.stu.cart.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {


    /**
     * 添加购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {


        /**
         * 1、获得购物车
         * 2、获得图书id和数量参数，通过bookdao中的load(bid)加载图书信息
         * 3、得到购物条，添加到购物车中
         * 4、转发到购物车页面
         */

        Cart cart= (Cart) request.getSession().getAttribute("cart");
        String bid=request.getParameter("bid");
        int count=Integer.parseInt(request.getParameter("count"));

        //获得图书
        Book book= new BookDao().findByBid(bid);

        CartItem cartItem=new CartItem();
        cartItem.setBook(book);
        cartItem.setCount(count);
        cart.add(cartItem);

        return "f:/jsps/cart/list.jsp";
    }


    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1、获得购物车
         * 2、清空
         */

        Cart cart= (Cart) request.getSession().getAttribute("cart");
        cart.clear();

        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 删除指定条目
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 1、获得购物车
         * 2、获得图书id参数
         * 3、删除指定条目
         */
        Cart cart= (Cart) request.getSession().getAttribute("cart");

        String bid=request.getParameter("bid");
        cart.delete(bid);

        return "f:/jsps/cart/list.jsp";
    }

}
