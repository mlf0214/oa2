package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

out.print("       <!DOCTYPE html >");
        out.print("<html lang = 'en' >");
out.print("<head >");
out.print("    <meta charset = 'UTF-8' >");
out.print("    <meta http -equiv = 'X-UA-Compatible' content = 'IE=edge' >");
out.print("    <meta name = 'viewport' content = 'width=device-width, initial-scale=1.0' >");
out.print("    <title > Document </title >");
out.print("</head >");
out.print("<body >");
out.print("    <h1 > 修改部门 </h1 >");
out.print("    <hr >");
out.print("    <form action = '"+request.getContextPath()+"/modify' method = 'post' >");
                //获取部门编号
                String depton = request.getParameter("depton");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select dname,loc as location from dept where depton=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, depton);
            rs = ps.executeQuery();
            if (rs.next()) {
                String dname = rs.getString("dname");
                String location = rs.getString("location");//参数“location”是查询SQL语句查询结果的列名

                //输出动态网页
                out.print("部门编号 <input type = 'text' name = 'depton' readonly value='"+depton+"'/ ><br>");
                out.print("部门名称 <input type = 'text' name = 'dname' value='"+dname+"'/ ><br>");
                out.print("部门位置 <input type = 'text' name = 'loc' value='"+location+"'/ ><br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        out.print("<input type = 'submit' value = '修改' >");
        out.print("</form >");
        out.print("</body >");
        out.print("</html >");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
