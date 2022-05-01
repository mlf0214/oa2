package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决中文乱码问题。
        request.setCharacterEncoding("utf-8");
        //获取表单数据
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        String depton = request.getParameter("depton");

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int cont;
        try {
            con=DBUtil.getConnection();

            String sql="update dept set dname=? ,loc=? where depton=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,depton);
            cont= ps.executeUpdate();
            if (cont==1){
                request.getRequestDispatcher("/list").forward(request,response);
            }else {
                request.getRequestDispatcher("/error.html").forward(request,response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(con,ps,rs);
        }

    }
}
