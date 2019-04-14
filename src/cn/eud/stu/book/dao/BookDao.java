package cn.eud.stu.book.dao;

import cn.edu.stu.tools.commons.CommonUtils;
import cn.edu.stu.tools.jdbc.TrQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cn.eud.stu.book.domain.Book;
import cn.eud.stu.category.domain.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库层
 */
public class BookDao {

    private QueryRunner qr=new TrQueryRunner();


    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll(){
        try{
            String sql="select * from book where del=false";
            return qr.query(sql,new BeanListHandler<Book>(Book.class));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


    /**
     * 按分类查询图书
     * @param cid
     * @return
     * @throws SQLException
     */
    public List<Book> findByCategory(String cid) throws SQLException {
        String sql="select * from book where cid=? and del=false";
        return qr.query(sql,new BeanListHandler<Book>(Book.class),cid);
    }


    /**
     * 根据图书的bid查询
     * @param bid
     * @return
     * @throws SQLException
     */
    public Book findByBid(String bid){

        try {
            /**
             * 这里需要将查询的图书信息返回为map形势
             * 分别包装到Category中和Book中
             * 然后将category设置到book中
             */
            String sql="select * from book where bid=?";
            Map<String,Object> map=qr.query(sql,new MapHandler(),bid);
            Category category=new Category();
            Book book=new Book();
            category= CommonUtils.toBean(map,Category.class);
            book=CommonUtils.toBean(map,Book.class);
            book.setCategory(category);
            return book;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询分类下的图书数量
     * @param cid
     * @return
     */
    public int findCountByCid(String cid) {

        try{
            String sql="select count(*) from book where cid=? and del=false";
            //查询图书数量
            Number num=(Number)qr.query(sql,new ScalarHandler(),cid);
            return num.intValue();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加图书
     * @param book
     */
    public void add(Book book) {

        try{
            String sql="insert into book(bid,bname,price,author,image,cid) values(?,?,?,?,?,?)";

            Object[]params={book.getBid(),book.getBname(),book.getPrice(),
                    book.getAuthor(),book.getImage(),book.getCategory().getCid()};

            qr.update(sql,params);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除图书
     * @param bid
     */
    public void delete(String bid) {
        try {
            String sql="update book set del=true where bid=?";
            qr.update(sql,bid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改图书
     * @param book
     */
    public void edit(Book book) {
        try {

            String sql="update book set bname=?,price=?,author=?, cid=? where bid=?";
            Object[]parmas={book.getBname(),book.getPrice(),book.getAuthor(),
                            book.getCategory().getCid(),book.getBid()};
            qr.update(sql,parmas);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
