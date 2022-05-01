package com.servlets;

import com.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应的内容及字符集
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String contextPath = request.getContextPath();

        writer.print("<!DOCTYPE html>");
        writer.print("<html lang='en'>");
        writer.print("<head>");
        writer.print("<meta charset='UTF-8'>");
        writer.print("<meta http-equiv='X-UA-Compatible' content='IE=edge'>");
        writer.print("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        writer.print("<title>Document</title>");
        writer.print("<script type='text/javascript'>");
        writer.print("function del(dno){");
        writer.print("if (window.confirm('亲，删了不可回复哦')){");
        writer.print("window.location.href='"+contextPath+"/delete?depton='+dno");
        writer.print("}");
        writer.print("}");
        writer.print("</script>");






        writer.print("</head>");
        writer.print("<body>");
        writer.print("    <h1 align='center'>部门列表</h1>");
        writer.print("    <hr>");
        writer.print("    <table border='1px' align='center' width='50%'>");
        writer.print("        <tr>");
        writer.print("            <th>序号</th>");
        writer.print("            <th>部门编号</th>");
        writer.print("            <th>部门名称</th>");
        writer.print("            <th>操作</th>");
        writer.print("        </tr>");

        writer.print("<!--            以上是固定的，-->");

        //连接数据库查询所有的部门
        Connection connection=null;
        PreparedStatement pr=null;
        ResultSet resultSet=null;
        try {
            connection= DBUtil.getConnection();
            String sql="select depton as a,dname,loc from dept";
            pr=connection.prepareStatement(sql);
            //执行sql语句
            resultSet = pr.executeQuery();
            int i=0;
            while (resultSet.next()){
                String depton = resultSet.getString("a");
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                writer.print("        <tr>");
                writer.print("    <td>"+(++i)+"</td>");
                writer.print("            <td>"+depton+"</td>");
                writer.print("            <td>"+dname+"</td>");
                writer.print("            <td>");
                //javascript:void(0)表示：仍然保留超链接的样子，点击超链接之后，不进行页面的跳转
                writer.print("<a href='javascript:void(0)' onclick='del("+depton+")'>删除</a>");
                writer.print("<a href='"+contextPath+"/edit?depton="+depton+"'>修改</a>");
                writer.print("<a href='"+contextPath+"/detail?depton="+depton+"'>详情</a>");
                writer.print("            </td>");
                writer.print("        </tr>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,pr,resultSet);
        }
        writer.print("<!--    一下是固定的-->");
        writer.print("    </table>");
        writer.print("   <a href='"+contextPath+"/add.html'> 新增部门</a>");
        writer.print("   ");
        writer.print("</body>");
        writer.print("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
