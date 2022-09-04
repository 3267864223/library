package com.springbootlibrary.controller;

import com.alibaba.fastjson.JSON;
import com.springbootlibrary.mapper.userMapper.AdminMapper;
import com.springbootlibrary.mapper.userMapper.ReaderMapper;
import com.springbootlibrary.pojo.Admin;
import com.springbootlibrary.pojo.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @PostMapping("/api/loginCheck")
    @ResponseBody
    public String UserLoginCheck(String id, String passwd,String remember) {
        Integer parseId = Integer.parseInt(id);
        Admin admin = adminMapper.selectById(parseId);
        ReaderCard readerCard = readerMapper.selectByReaderCardById(parseId);
        HashMap<String, String> msg = new HashMap<>();
        if (admin != null && admin.getPassword().equals(passwd)) {
            msg.put("stateCode", "1");
            session.setAttribute("user", admin);
            if (remember.equals("true")){
                Cookie cookie = new Cookie("boot_rememberMe", "YWRtaW4=Admin");
                cookie.setMaxAge(3600);
                cookie.setPath("/"); //设置cookie的路径为主路径
                response.addCookie(cookie);
            }
        } else if (readerCard!=null && readerCard.getPasswd().equals(passwd)){
            msg.put("stateCode", "2");
            session.setAttribute("user", readerCard);
            if (remember.equals("true")){
                Cookie cookie = new Cookie("boot_rememberMe", "YWRtaW4=Reader");
                cookie.setMaxAge(3600);
                cookie.setPath("/"); //设置cookie的路径为主路径
                response.addCookie(cookie);
            }
        }else {
            msg.put("stateCode", "0");
        }

        return JSON.toJSONString(msg);
    }

    @GetMapping("/logout.html")
    public String UserLogout(){
        session.removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("boot_rememberMe")){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        return "redirect:/";
    }

}
