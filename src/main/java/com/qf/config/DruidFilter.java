package com.qf.config;

import com.alibaba.druid.support.http.WebStatFilter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-19
 * Time: 17:34
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = {
    @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})
public class DruidFilter extends WebStatFilter {

}
