package com.qf.config;

import com.alibaba.druid.support.http.StatViewServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-19
 * Time: 17:27
 */
@WebServlet(urlPatterns = "/druid/*", initParams = {
    @WebInitParam(name = "loginUsername", value = "admin"),
    @WebInitParam(name = "loginPassword", value = "admin"),
    @WebInitParam(name = "resetEnable", value = "false")})
public class DruidServlet extends StatViewServlet {

  private static final long serialVersionUID = 1L;
}
