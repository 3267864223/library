package com.springbootlibrary.controller;

import com.springbootlibrary.mapper.userMapper.ReaderMapper;
import com.springbootlibrary.pojo.ReaderCard;
import com.springbootlibrary.pojo.ReaderInfo;
import com.springbootlibrary.utils.ReaderInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ReaderController {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @GetMapping("/allreaders.html")
    public String allReaderInfo(Model model) {
        List<ReaderInfo> readerInfos = readerMapper.selectAllByReaderInfo();
        model.addAttribute("readerInfos", readerInfos);
        return "admin/admin_readers";
    }

    @GetMapping("/reader/edit")
    public String toReaderInfoPage(String readerId, Model model) {
        Integer id = Integer.parseInt(readerId);
        ReaderInfo readerInfo = readerMapper.selectByReaderInfoById(id);
        model.addAttribute("readerInfo", readerInfo);
        return "admin/admin_reader_edit";
    }

    @PostMapping("/readerEditDo")
    public String bookEditDo(RedirectAttributes redirectAttributes, String readerId, String name, String sex, String birth, String address, String telcode) {
        Integer id = Integer.parseInt(readerId);
        ReaderInfo readerInfo = readerMapper.selectByReaderInfoById(id);
        ReaderInfoSet readerInfoSet = new ReaderInfoSet(readerInfo);
        ReaderInfo newReaderInfo = readerInfoSet.readerInfoSet(readerId, name, sex, birth, address, telcode);
        int i = readerMapper.updateReaderInfo(newReaderInfo);
        if (i == 1) {
            ReaderCard readerCard = readerMapper.selectByReaderCardById(id);
            readerCard.setName(name);
            readerMapper.updateReaderCard(readerCard);
            redirectAttributes.addFlashAttribute("succ", "读者资料修改成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "读者资料修改失败");
        }
        return "redirect:/allreaders.html";
    }

    @GetMapping("/readerDeleteDo")
    public String readerDel(String readerId, RedirectAttributes redirectAttributes) {
        Integer id = Integer.parseInt(readerId);
        int i = readerMapper.deleteReaderInfo(id);
        if (i == 1) {
            readerMapper.deleteReaderCard(id);
            redirectAttributes.addFlashAttribute("succ", "读者删除成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "读者删除失败，请联系管理员大大");
        }
        return "redirect:/allreaders.html";
    }

    @GetMapping("/readerAdd")
    public String toReaderAddPage() {
        return "admin/admin_reader_add";
    }

    @PostMapping("/checkReaderId")
    @ResponseBody
    public HashMap<String, String> checkReaderId(String readerId) {
        HashMap<String, String> res = new HashMap<>();
        Integer id = null;
        try {
            id = Integer.parseInt(readerId);
        } catch (NumberFormatException e) {
            res.put("isExist", "true");
            res.put("msg", "读者证号格式错误，最大为10位数");
            return res;
        }
        ReaderCard readerCard = readerMapper.selectByReaderCardById(id);
        if (readerCard == null) {
            res.put("isExist", "false");
            res.put("msg", "该读者证号可用");
        } else {
            res.put("isExist", "true");
            res.put("msg", "该读者证号已存在");
        }
        return res;
    }

    @PostMapping("/readerAddDo")
    public String readerAddDo(RedirectAttributes redirectAttributes, String readerId, String name, String sex, String birth, String address, String telcode) {
        Integer id = null;
        Date parseBirth = null;
        try {
            id = Integer.parseInt(readerId);
            parseBirth = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("error", "生日格式应该为yyyy-MM-dd");
            return "redirect:/allreaders.html";
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "读者证号不合法");
            return "redirect:/allreaders.html";
        }

        int i = readerMapper.insertReaderInfo(new ReaderInfo(id, name, sex, parseBirth, address, telcode));
        if (i == 1) {
            readerMapper.insertReaderCard(new ReaderCard(id,name));
            redirectAttributes.addFlashAttribute("succ", "读者添加成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "读者添加失败，请联系帅气的管理员");
        }
        return "redirect:/allreaders.html";
    }

    @GetMapping("/reader_main.html")
    public String toReaderMainPage(Model model) {
        ReaderCard user = (ReaderCard) request.getSession().getAttribute("user");
        model.addAttribute("readercard", user);
        return "reader/reader_main";
    }

    @GetMapping("/readerInfo")
    public String toReaderInfo(Model model) {
        ReaderCard user = (ReaderCard) session.getAttribute("user");
        ReaderInfo readerInfo = readerMapper.selectByReaderInfoById(user.getReaderId());
        model.addAttribute("readerinfo", readerInfo);
        return "reader/reader_info";
    }

    @GetMapping("/readerInfoEdit")
    public String toReaderInfoEditPage(String readerId, Model model) {
        Integer id = Integer.parseInt(readerId);
        ReaderInfo readerInfo = readerMapper.selectByReaderInfoById(id);
        model.addAttribute("readerinfo", readerInfo);
        return "reader/reader_info_edit";
    }

    @PostMapping("/reader_edit_do")
    public String reader_edit_do(RedirectAttributes redirectAttributes, String readerId, String name, String sex, String birth, String address, String telcode) {
        Integer id = Integer.parseInt(readerId);
        ReaderInfo readerInfo = readerMapper.selectByReaderInfoById(id);
        ReaderInfo newReaderInfo = new ReaderInfoSet(readerInfo).readerInfoSet(readerId, name, sex, birth, address, telcode);
        int i = readerMapper.updateReaderInfo(newReaderInfo);
        if (i == 1) {
            ReaderCard readerCard = readerMapper.selectByReaderCardById(id);
            readerCard.setName(name);
            readerMapper.updateReaderCard(readerCard);
            redirectAttributes.addFlashAttribute("succ","信息修改成功");
        } else {
            redirectAttributes.addFlashAttribute("error","信息修改失败，请联系爱玩锐雯的管理员");
        }
        return "redirect:/readerInfo";
    }

    @GetMapping("/reader_repasswd")
    public String toReaderRepasswd(){
        return "reader/reader_repasswd";
    }

    @PostMapping("/checkPassword")
    @ResponseBody
    public HashMap<String,String> checkPassword(String oldPasswd){
        HashMap<String, String> res = new HashMap<>();
        ReaderCard user = (ReaderCard) session.getAttribute("user");
        if(user.getPasswd().equals(oldPasswd)){
            res.put("isLike","true");
            res.put("msg","密码验证通过");
        } else {
            res.put("isLike","false");
            res.put("msg","旧密码错误");
        }
        return res;
    }

    @PostMapping("/reader_repasswd_do")
    public String readerRespasswdDo(RedirectAttributes redirectAttributes,String newPasswd){
        ReaderCard user = (ReaderCard) session.getAttribute("user");
        user.setPasswd(newPasswd);
        readerMapper.updateReaderCard(user);
        redirectAttributes.addFlashAttribute("succ","密码修改成功");
        return "redirect:/readerInfo";
    }


}

