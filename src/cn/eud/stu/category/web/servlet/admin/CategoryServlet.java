package cn.eud.stu.category.web.servlet.admin;

import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.servlet.BaseServlet;
import cn.eud.stu.category.domain.Category;
import cn.eud.stu.category.service.CategoryService;
import cn.eud.stu.category.exception.CategoryException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 后台管理分类web层
 */
@WebServlet(name = "AdminCategoryServlet",urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    //添加依赖
    private CategoryService categoryService=new CategoryService();


    /**
     * 修改分类名称
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 1、将参数转化成Category对象
         * 2、调用service得edit方法，修改分类名称
         * 3、返回调用findAll方法，显示分类列表
         */

        Category category=CommonUtils.toBean(request.getParameterMap(),Category.class);
        categoryService.edit(category);
        return findAll(request,response);
    }

    /**
     * 修改分类之前
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editPre(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        /**
         * 修改分类之前
         * 1、获得分类id
         * 2、通过分类id,加载分类对象
         * 3、将分类对象存放在request
         * 4、转发到/adminjsps/admin/category/mod.jsp
         */

        String cid=request.getParameter("cid");
        request.setAttribute("category",categoryService.load(cid));

        return "f:/adminjsps/admin/category/mod.jsp";
    }


    /**
     * 删除图书分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * 1、得到图书分类id：cid参数
         * 2、调用categoryService得delete删除函数
         *      如果成功，返回调用findAll，展示剩余分类
         *      如果失败，保存失败信息到request中，转发到/adminjsps/msg.jsp
         */
        String cid=request.getParameter("cid");

        try{
            categoryService.delete(cid);
        }catch (CategoryException e){
            //保存异常信息
            request.setAttribute("msg",e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }


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
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 查询所有分类
         * 1、调用service层的findAll方法
         * 2、将返回结果保存到request中
         * 3、转发到/adminjsps/admin/category/list.jsp
         */

        request.setAttribute("categoryList",categoryService.findAll());

        return "f:/adminjsps/admin/category/list.jsp";
    }


    /**
     * 添加分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 添加分类
         * 1、得到category对象参数
         * 2、调用service的add方法
         * 3、返回调用findAll方法，跳转到分类显示界面
         */

        //得到category对象
        Category category= CommonUtils.toBean(request.getParameterMap(),Category.class);

        //不全cid
        category.setCid(CommonUtils.uuid());

        categoryService.add(category);


        return findAll(request,response);
    }

}
