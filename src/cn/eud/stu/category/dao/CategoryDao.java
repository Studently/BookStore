package cn.eud.stu.category.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.eud.stu.category.domain.Category;
import cn.edu.stu.tools.jdbc.TrQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类数据库层
 */
public class CategoryDao {

    //依赖
    private QueryRunner qr=new TrQueryRunner();

    /**
     * 查询所有分类
     * @return
     */
    public List<Category>findAll(){
        try {
            String sql="select * from category";
            return qr.query(sql,new BeanListHandler<>(Category.class));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 添加分类
     * @param category
     */
    public void add(Category category) {
        try {
            String sql="insert into category values(?,?)";
            qr.update(sql,category.getCid(),category.getCname());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除图书分类
     * @param cid
     */
    public void delete(String cid) {

        try {
            String sql="delete from category where cid=?";
            qr.update(sql,cid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过分类id,查询分类对象
     * @param cid
     */
    public Category load(String cid) {

        try{
            String sql="select * from category where cid=?";
            return (Category) qr.query(sql,new BeanHandler<>(Category.class),cid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改分类名称
     * @param category
     */
    public void edit(Category category) {

        try{
            String sql="update category set cname=? where cid=?";
            qr.update(sql,category.getCname(),category.getCid());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
