package cn.eud.stu.book.service;



import cn.eud.stu.book.dao.BookDao;
import cn.eud.stu.book.domain.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * 业务层
 */
public class BookService {

    private BookDao bookDao=new BookDao();

    /**
     * 查询所有图书
     * @return
     */
    public List<Book> findAll() {
        return bookDao.findAll();
    }


    /**
     * 按分类查询图书
     * @param cid
     * @return
     * @throws SQLException
     */
    public List<Book> findByCategory(String cid) throws SQLException {
        return bookDao.findByCategory(cid);
    }

    /**
     * 根据图书的bid查询
     * @param bid
     * @return
     * @throws SQLException
     */
    public Book findByBid(String bid){
        return bookDao.findByBid(bid);
    }

    /**
     * 添加图书
     * @param book
     */
    public void add(Book book) {

        bookDao.add(book);
    }

    /**
     * 删除图书
     * @param bid
     */
    public void delete(String bid) {

        bookDao.delete(bid);
    }

    /**
     * 修改图书
     * @param book
     */
    public void edit(Book book) {
        bookDao.edit(book);
    }
}
