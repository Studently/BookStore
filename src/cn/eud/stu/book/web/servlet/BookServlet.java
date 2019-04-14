package cn.eud.stu.book.web.servlet;

import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.book.domain.Book;
import cn.eud.stu.book.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "BookServlet",urlPatterns = "/BookServlet")
public class BookServlet extends BaseServlet {

    private BookService service=new BookService();


    /**
     * 查询所有图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        request.setAttribute("bookList",service.findAll());

        return "f:/jsps/book/list.jsp";
    }


    /**
     * 按分类查询图书
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String findByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        //获得分类cid
        String cid=request.getParameter("cid");

        //查询所有分类
        List<Book> bookList=service.findByCategory(cid);
        //保存到request中
        request.setAttribute("bookList",bookList);

        return "f:/jsps/book/list.jsp";
    }

    /**
     * 根据图书的bid查询
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String findByBid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        //获得图书bid
        String bid=request.getParameter("bid");
        //将图书存入request
        request.setAttribute("book",service.findByBid(bid));

        return "f:/jsps/book/desc.jsp";
    }

}
