<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.*, service.*, service.impl.*, java.util.*, java.math.BigDecimal;" %>

<!DOCTYPE html>
<html>
    <head>
        <title>http://www.online-shop.com</title>
    </head>
    <body>
        <h1 align="center">Dear <%= (String) session.getAttribute("login") %>, your order:</h1>
        <div style="font-size: 20" align="center">
            <% Cart cart = (Cart) session.getAttribute("cart");
                CartService service = new CartServiceImpl();
                if (cart != null) {
                    out.println("<h2><pre align=\"center\">" +  service.print(cart) + "</pre></h2>");
                }
            %>
        </div>
        <h2 align="center">
            <% BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
                if (totalPrice != null) {
                    out.println ("Total: $ " + totalPrice);
                } else {
                    out.println("You haven't added anything to your cart");
                }
            %>
        </h2>
    </body>
</html>








