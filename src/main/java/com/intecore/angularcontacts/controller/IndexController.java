package com.intecore.angularcontacts.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(HttpServletRequest request) {
        logger.debug("Remote host: {}", request.getRemoteHost());
        logger.debug("Remote port: {}", request.getRemotePort());
        logger.debug("Remote address: {}", request.getRemoteAddr());
        logger.debug("Request URI: {}", request.getRequestURI());
        return "index";
    }
}
