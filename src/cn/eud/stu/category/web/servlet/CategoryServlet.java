package cn.eud.stu.category.web.servlet;

import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.category.domain.Category;
import cn.eud.stu.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 分类操作层
 */
@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    //依赖
    private CategoryService service=new CategoryService();


    /**
     * 查询所有分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        //查询所有分类
        List<Category> categoryList=service.findAll();
        //将分类保存到request中
        request.setAttribute("categoryList",categoryList);
        return "f:/jsps/left.jsp";
    }



}
