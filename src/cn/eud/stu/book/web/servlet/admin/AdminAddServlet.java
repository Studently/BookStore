package cn.eud.stu.book.web.servlet.admin;

import cn.edu.stu.tools.commons.CommonUtils;
import cn.eud.stu.book.service.BookService;
import cn.eud.stu.category.domain.Category;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import cn.eud.stu.book.domain.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 完成图文信息的上传
 */
@WebServlet(name = "AdminAddServlet",urlPatterns = "/admin/AdminAddServlet")
public class AdminAddServlet extends HttpServlet {


    private BookService bookService=new BookService();
    /**
     * 上传图书文件
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        /**
         * 上传校验工作
         * 1、上传图片大小 0-15KB
         *      在图片加载之前设置
         * 2、上传图片尺寸：小于200*200
         *      在图片保存后校验
         * 3、上传图片类型为jpg格式
         *      在图片加载后校验
         */

        /**
         * 上传文件的三步
         * 1、建立文件工厂
         * 2、得到解析器
         * 3、用解析器解析request
         */

        DiskFileItemFactory factory=new DiskFileItemFactory(15*1024,new File("g:/temp"));
        ServletFileUpload sfu=new ServletFileUpload(factory);
        //设置单个文件大小最大为15KB
        sfu.setFileSizeMax(30*1024);
        try {
            //解析request
            List<FileItem> fileItemList = sfu.parseRequest(request);

            /**
             * 1、把所有普通表单项封装到map中，然后直接转成book对象
             */

            Map<String,String>maps=new HashMap<>();
            //遍历表单项
            for (int i = 0; i <fileItemList.size() ; i++) {
                if (fileItemList.get(i).isFormField()) {
                    //文件名作为键，文件值作为值
                    maps.put(fileItemList.get(i).getFieldName(),fileItemList.get(i).getString("utf-8"));
                }
            }

            //创建book对象，将maps封装到book中
            Book book=CommonUtils.toBean(maps,Book.class);;
            //设置图书id
            book.setBid(CommonUtils.uuid());

            //创建category对象，并将maps封装进去
            Category category=CommonUtils.toBean(maps,Category.class);
            book.setCategory(category);


            /**
             * 处理文件表单
             * 1、保存文件
             *      文件名称
             *      文件位置
             */
            //文件存放完整路径
            String filePath=request.getServletContext().getRealPath("/book_img");
            //文件名
            String fileName=CommonUtils.uuid()+"_"+fileItemList.get(1).getName();
            //目录和文件名创建目标文件
            File destFile=new File(filePath,fileName);

            //保存文件
            fileItemList.get(1).write(destFile);


            //校验图片尺寸
            /**
             * 得到图片对象
             */
            Image image=new ImageIcon(destFile.getAbsolutePath()).getImage();
            if(image.getWidth(null)>200||image.getHeight(null)>200){
                request.setAttribute("msg","该图片尺寸超过200*200");
                request.getRequestDispatcher("/adminjsps/msg.jsp")
                        .forward(request,response);
                return;
            }
            /**
             * 将图片的位置添加到book中
             * 调用bookService的add方法，将book添加到数据库
             * 返回调用AdminBookServlet方法的findAll，查看图书列表
             */
            book.setImage("/book_img/"+fileName);

            //校验图片文件名
            if (!fileName.toLowerCase().endsWith("jpg")) {
                request.setAttribute("msg","该图片不是JPG扩展名");
                request.getRequestDispatcher("/adminjsps/msg.jsp")
                        .forward(request,response);
                return;
            }

            bookService.add(book);

            //转发
            request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").
                    forward(request,response);

        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
                /**
                 * 单个文件上传大小超出设置
                 * 1、保存错误信息到request中
                 * 2、转发到/adminjsps/msg.jsp
                  */
                request.setAttribute("msg","该图片大小超过15KB!");
                request.getRequestDispatcher("/adminjsps/msg.jsp")
                        .forward(request,response);
                return;
            }
        }

    }

}
