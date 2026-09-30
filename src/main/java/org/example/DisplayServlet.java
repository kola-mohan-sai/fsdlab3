package org.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/db";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "MOHANSAI2006";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Student Records</title>");

        out.println("<style>");
        out.println("""
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    padding: 30px;
                }

                h1 {
                    text-align: center;
                    color: #333;
                }

                table {
                    width: 80%;
                    margin: 30px auto;
                    border-collapse: collapse;
                    background-color: white;
                }

                th, td {
                    border: 1px solid #ccc;
                    padding: 12px;
                    text-align: center;
                }

                th {
                    background-color: #333;
                    color: white;
                }

                tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                tr:hover {
                    background-color: #ddd;
                }

                .error {
                    width: 80%;
                    margin: 30px auto;
                    padding: 15px;
                    background-color: #ffe6e6;
                    color: #cc0000;
                    border: 1px solid #cc0000;
                }
                """);
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Student Records</h1>");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM s1"
            );

            ResultSetMetaData metaData =
                    rs.getMetaData();

            int columnCount =
                    metaData.getColumnCount();

            out.println("<table>");

            // Column headings
            out.println("<tr>");

            for (int i = 1; i <= columnCount; i++) {

                out.println("<th>"
                        + metaData.getColumnName(i)
                        + "</th>");
            }

            out.println("</tr>");

            // Records
            while (rs.next()) {

                out.println("<tr>");

                for (int i = 1; i <= columnCount; i++) {

                    out.println("<td>"
                            + rs.getString(i)
                            + "</td>");
                }

                out.println("</tr>");
            }

            out.println("</table>");

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {

            out.println("<div class='error'>");

            out.println("<h2>Database Error</h2>");

            out.println("<pre>");
            out.println(e.getMessage());
            out.println("</pre>");

            out.println("</div>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}