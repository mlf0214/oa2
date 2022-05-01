package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

out.print("<!DOCTYPE html>");
out.print("<html lang='en'>");
out.print("       <head>");
out.print("           <meta charset='UTF-8'>");
out.print("           <meta http-equiv='X-UA-Compatible' content='IE=edge'>");
out.print("           <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
out.print("           <title>Document</title>");
out.print("       </head>");
out.print("       <body>");
out.print("           <h1>部门详情</h1>");
out.print("           <hr>");
        //获取部门编号
        //服务器获取的是字符串
        String depton = request.getParameter("depton");
        //连接数据库，根据部门编号查询部门信息。
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
        connection= DBUtil.getConnection();
        String sql="select dname,loc from dept where depton=?";
        ps= connection.prepareStatement(sql);
        ps.setString(1,depton);
        rs = ps.executeQuery();
        if (rs.next()){
            String dname = rs.getString("dname");
            String loc = rs.getString("loc");
            out.print("部门编号:"+depton+"<br>");
            out.print("部门名称:"+dname+"<br>");
            out.print("部门位置:"+loc+"<br>");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        out.print("           <input type='button' value='后退' onclick='window.history.back()'/>");
        out.print("       </body>");
        out.print("       </html>");


    }
}
