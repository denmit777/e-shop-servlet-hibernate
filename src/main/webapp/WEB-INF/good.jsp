<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.*, service.*, service.impl.*, utils.*, java.util.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title>http://www.online-shop.com</title>
    </head>
    <body>
        <h1 align="center">Hello <%= (String) session.getAttribute("login") %>!</h1>
        <div align="center">
            <% String chosenGoods = (String) session.getAttribute("chosenGoods");
                if (chosenGoods != null) {
                    out.println ("<h2>You have already chosen:</h2>\n" +
                                "<h2><pre>" + chosenGoods + "</pre></h2>");
                } else {
                    out.println("<h2>Make your order</h2>\n");
                }
            %>
        </div>
        <form action="good" method="post" align="center">
            <select name="goodName" id="goodName">
                <%  GoodService service = new GoodServiceImpl();
                    List<Good> goods = service.getListGoods();
                    for (Good el : goods) {
                        out.println("<option>" + el.getName() + " (" + el.getPrice() + ") </option>\n");
                    }
                %>
            </select><br/><br/>
            <input name="submit" type="submit" value="Add Good">
            <input name="submit" type="submit" value="Submit">
        </form>
    </body>
</html>
