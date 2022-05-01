package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门编号
        String depton = request.getParameter("depton");
        //连接数据库删除数据
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int cont = 0;
        try {
            connection=DBUtil.getConnection();
            connection.setAutoCommit(false);
            String sql="delete from dept where depton=?";
            ps=connection.prepareStatement(sql);
            ps.setString(1,depton);
            //影响了数据库表中多少条记录
           cont = ps.executeUpdate();
            connection.commit();
        }catch (Exception e){
            if (connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        if (cont>0){
          //删除成功
            //仍然推跳转到部门列表页面
            //部门列表页面的显示需要执行另一个servlet，先用转发
            request.getRequestDispatcher("/list").forward(request,response);
        }else {
            request.getRequestDispatcher("/error.html").forward(request,response);
        }
    }

}
