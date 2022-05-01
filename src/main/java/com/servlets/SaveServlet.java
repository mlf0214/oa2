package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门的信息
        request.setCharacterEncoding("utf-8");
        String depton = request.getParameter("depton");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        //连接数据库修改信息
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int cont=0;
        try {
            connection=DBUtil.getConnection();
            String sql="insert into dept(depton,dname,loc) value (?,?,?)";
            ps=connection.prepareStatement(sql);
            ps.setString(1,depton);
            ps.setString(2,dname);
            ps.setString(3,loc);
            cont = ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        if (cont==1){
            //保存成功跳转到列表页面
            request.getRequestDispatcher("/list").forward(request,response);
        }else {
            request.getRequestDispatcher("/error.html").forward(request,response);
        }
        //保存失败跳转到错误页面
    }
}
