package com.goit.feature;

import com.goit.feature.util.TimeZoneUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private String initTime;

    private final TimeZoneUtil timeZoneUtil = new TimeZoneUtil();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ZoneId zid = ZoneId.of(timeZoneUtil.parseTimeZone(req));
        Clock clock = Clock.system(zid);
        LocalDateTime localDateTime = LocalDateTime.now(clock);
        initTime = localDateTime.toString().replace('T', ' ').substring(0, 19) + " " + zid;

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("<h1>" + initTime + "</h1>");
        resp.getWriter().close();
    }

    private String parseTimeZone(HttpServletRequest request) {
        if (request.getParameterMap().containsKey("timezone")) {
            return (request.getParameter("timezone").replace(" ", "+").length() < 1) ?
                    "UTC" : request.getParameter("timezone").replace(" ", "+").toUpperCase();
        }
        return "UTC";
    }
}
