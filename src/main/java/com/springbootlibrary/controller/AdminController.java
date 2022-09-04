package com.springbootlibrary.controller;

import com.springbootlibrary.mapper.userMapper.AdminMapper;
import com.springbootlibrary.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HttpSession session;

    @GetMapping("/adminRepasswd")
    public String toAdminRepasswdPage(){
        return "admin/admin_repasswd";
    }

    @PostMapping("/checkAdminPwd")
    @ResponseBody
    public HashMap<String,String> checkAdminPwd(String oldPassword){
        HashMap<String, String> res = new HashMap<>();
        Admin user = (Admin) session.getAttribute("user");
        String password = user.getPassword();
        if(oldPassword.equals(password)){
            res.put("oldPwd","true");
            res.put("msg","旧密码正确");
        } else {
            res.put("oldPwd","false");
            res.put("msg","旧密码错误");
        }
        return res;
    }

    @PostMapping("/adminRepasswdDo")
    public String adminRepassDo(String reNewPasswd, RedirectAttributes redirectAttributes){
        Admin user = (Admin) session.getAttribute("user");
        user.setPassword(reNewPasswd);
        int i = adminMapper.updateAdminPassword(user);
        if (i == 1){
            redirectAttributes.addFlashAttribute("succ","密码修改成功");
        } else {
            redirectAttributes.addFlashAttribute("error","密码修改失败，请联系爱玩德莱文的管理员");
        }
        return "redirect:/adminRepasswd";
    }
}
