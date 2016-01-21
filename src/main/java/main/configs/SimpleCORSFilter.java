package main.configs;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by delf on 1/20/16.
 * Из-за того, что я не настроил правильно возврат HTML, веб морда писалась отдельно.
 * Для этого пришлось создать сей класс.
 */
@Component
public class SimpleCORSFilter implements Filter {

    //Filter implementation
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(req, res);
    }

    //Required methods for Filter interface
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
