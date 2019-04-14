package cn.eud.stu.category.service;

import cn.eud.stu.book.dao.BookDao;
import cn.eud.stu.category.dao.CategoryDao;
import cn.eud.stu.category.domain.Category;
import cn.eud.stu.category.exception.CategoryException;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类业务层
 */
public class CategoryService {

    private CategoryDao categoryDao=new CategoryDao();
    private BookDao bookDao=new BookDao();

    /**
     * 查询所有分类
     * @return
     * @throws SQLException
     */
    public List<Category>findAll(){
        return categoryDao.findAll();
    }

    /**
     * 添加分类
     * @param category
     */
    public void add(Category category) {
        categoryDao.add(category);
    }

    /**
     * 删除分类
     * @param cid
     */
    public void delete(String cid) throws CategoryException {
        //查询图书数量
        int count=bookDao.findCountByCid(cid);

        //如果图书不为0，抛出异常
        if(count>0)
            throw new CategoryException("该分类下还有图书，请先删除图书。");

        categoryDao.delete(cid);
    }

    /**
     * 通过分类id,查询分类
     * @param cid
     * @return
     */
    public Category load(String cid) {
        return categoryDao.load(cid);
    }

    /**
     * 修改分类名称
     * @param category
     */
    public void edit(Category category) {
        categoryDao.edit(category);
    }
}
