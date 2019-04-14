package cn.eud.stu.book.web.servlet.admin;

import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.book.service.BookService;
import cn.eud.stu.category.domain.Category;
import cn.eud.stu.category.service.CategoryService;
import cn.eud.stu.book.domain.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="AdminBookServlet",urlPatterns = "/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {

    private BookService bookService=new BookService();
    private CategoryService categoryService=new CategoryService();


    /**
     * 修改图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 1、将参数转化为book类型，和category类型
         * 2、调用bookservice的edit方法
         * 3、返回调用findAll
         */
        Book book= CommonUtils.toBean(request.getParameterMap(),Book.class);
        Category category=CommonUtils.toBean(request.getParameterMap(),Category.class);
        book.setCategory(category);

        bookService.edit(book);

        return findAll(request,response);
    }

    /**
     * 删除图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 1、获得图书id参数
         * 2、调用bookservice 的delete方法
         * 3、返回调用findAll（）
         */

        String bid=request.getParameter("bid");

        bookService.delete(bid);

        return findAll(request,response);
    }

    /**
     * 查询所有分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 查询所有分类，并保存到/adminjsps/admin/book/add.jsp
         *
         */
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }

    /**
     * 通过图书id查询图书信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByBid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        /**
         * 1、获得图书id参数
         * 2、调用bookService的findByBid方法，查询图书信息
         * 3、保存到request
         * 4、转发到/adminjsps/admin/book/desc.jsp
         */

        String bid=request.getParameter("bid");
        request.setAttribute("book",bookService.findByBid(bid));
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    /**
     * 查询所有图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 1、调用BookService的findAll方法查询所有图书
         * 2、保存到request
         * 3、转发到/adminjsps/admin/book/list.jsp
         */
        request.setAttribute("bookList",bookService.findAll());

        return "f:/adminjsps/admin/book/list.jsp";
    }
}
