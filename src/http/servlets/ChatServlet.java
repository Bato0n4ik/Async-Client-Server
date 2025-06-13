package http.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("           Start doPost: ");
        var headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            var header = headers.nextElement();
            System.out.println(header + ": " + req.getHeader(header));
        }

        var params = req.getParameterMap();
        System.out.println(params);
        Iterator<String> iter = params.keySet().iterator();
        iter.forEachRemaining(elem -> System.out.println(elem + ": " + Arrays.toString(params.get(elem))));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

    }
}
