package cn.eud.stu.user;

import cn.edu.stu.tools.jdbc.JDBCUtils;
import cn.edu.stu.tools.jdbc.TrQueryRunner;
import cn.eud.stu.category.dao.CategoryDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import cn.eud.stu.category.domain.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class test {
    //依赖
    private QueryRunner qr=new TrQueryRunner();
    @Test
    public void func1() throws SQLException {
        Connection cn = JDBCUtils.getConnction();
        System.out.println(cn);

            String sql="select * from category";
            List<Category> categoryList=qr.query(sql,new BeanListHandler<>(Category.class));
        for (int i = 0; i <categoryList.size() ; i++) {
            System.out.println(categoryList.get(i).getCname());
        }

    }

    @Test
    public void func2(){

        CategoryDao dao=new CategoryDao();

        List<Category> categoryList=dao.findAll();
        for (int i = 0; i <categoryList.size() ; i++) {
            System.out.println(categoryList.get(i).getCname());
        }

    }



    /**
     https://www.yeepay.com/app-merchant-proxy/node?
     p0_Cmd=Buy
     &p1_MerId=10001126856
     &p2_Order=123456
     &p3_Amt=100
     &p4_Cur=CNY
     &p5_Pid=
     &p6_Pcat=
     &p7_Pdesc=
     &p8_Url=http://localhost:8080/bookstore/OrderServlet?method=back
     &p9_SAF=
     &pa_MP=
     &pd_FrpId=ICBC-NET-B2C
     &pr_NeedResponse=1
     &hmac=008e95ff1b1928d243e71a47ac48790c
     */
    @Test
    public void fun5() {
        String hmac = PaymentUtil.buildHmac("Buy", "10001126856",
                "123456", "100", "CNY",
                "", "", "", "http://localhost:8080/bookstore/OrderServlet?method=back",
                "", "", "ICBC-NET-B2C", "1", "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
        System.out.println(hmac);
    }

}
