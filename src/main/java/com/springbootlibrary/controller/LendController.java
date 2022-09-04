package com.springbootlibrary.controller;

import com.springbootlibrary.mapper.LendMapper.LendMapper;
import com.springbootlibrary.mapper.bookMapper.BookMapper;
import com.springbootlibrary.mapper.userMapper.ReaderMapper;
import com.springbootlibrary.pojo.BookInfo;
import com.springbootlibrary.pojo.LendList;
import com.springbootlibrary.pojo.ReaderCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class LendController {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LendMapper lendMapper;

    @Autowired
    private ReaderMapper readerMapper;

    //归还图书
    @GetMapping("/lendbook/alsoBook")
    public String alsoBook(@RequestParam("bookId") String bookId, RedirectAttributes redirectAttributes) {
        Integer id = Integer.parseInt(bookId);
        List<LendList> lendLists = lendMapper.selectByBookId(id);
        if (lendLists.size() == 0){
            redirectAttributes.addFlashAttribute("error","归还图书失败，请联系管理员");
        }
        for (LendList lendList : lendLists) { //如果lendLists长度为0，则不会进行遍历
            if (lendList.getBackDate() == null) { //该图书的借还记录为null表示书籍未还
                BookInfo bookInfo = bookMapper.selectByBookId(id);
                bookInfo.setState(1);
                bookMapper.updateBook(bookInfo);
                //更新借还日志
                lendList.setBackDate(new Date());
                lendMapper.updateLendList(lendList);
                redirectAttributes.addFlashAttribute("succ", "图书归还成功");
                break;
            }
        }
        return "redirect:/allBooks.html";
    }

    //借阅图书页面
    @GetMapping("/lendbook/borrowBook")
    public String toBookLend(@RequestParam("bookId") String bookId, Model model) {
        Integer id = Integer.parseInt(bookId);
        BookInfo bookInfo = bookMapper.selectByBookId(id);
        model.addAttribute("book", bookInfo);
        return "admin/admin_book_lend";
    }

    //借阅的操作
    @PostMapping("/lendbookdo/borrowBookDo")
    public String borrowBookDo(@RequestParam("bookId") String bookId, String readerId, RedirectAttributes redirectAttributes) {
        Integer book_id = Integer.parseInt(bookId);
        Integer reader_id = Integer.parseInt(readerId);
        ReaderCard readerCard = readerMapper.selectByReaderCardById(reader_id);
        if (readerCard != null) { //该读者存在
            BookInfo bookInfo = bookMapper.selectByBookId(book_id);
            bookInfo.setState(0);
            bookMapper.updateBook(bookInfo);
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功");
            //创建LendList记录
            LendList lendList = new LendList(book_id, reader_id, new Date(), null);
            lendMapper.insertLend(lendList);
        } else {
            redirectAttributes.addFlashAttribute("error", "图书借阅失败，该读者不存在，请联系管理员");
        }
        return "redirect:/allBooks.html";
    }

    @GetMapping("/lendlist.html")
    public String allLendList(Model model) {
        List<LendList> lendLists = lendMapper.selectAllLend();
        model.addAttribute("lendLists", lendLists);
        return "admin/admin_lend_list";
    }

    @GetMapping("/lendDel")
    public String lendDel(String sernum,RedirectAttributes redirectAttributes){
        Integer paresSernum = Integer.parseInt(sernum);
        int i = lendMapper.deleteLendListBySernum(paresSernum);
        if (i == 1){
            redirectAttributes.addFlashAttribute("succ","图书借还记录删除成功");
        } else {
            redirectAttributes.addFlashAttribute("error","图书借还记录删除失败，请联系爱吃烧烤的管理员");
        }
        return "redirect:/lendlist.html";
    }

    @GetMapping("/mylend")
    public String toMyLend(String readerId,Model model){
        Integer id = Integer.parseInt(readerId);
        List<LendList> lendLists = lendMapper.selectByReaderId(id);
        model.addAttribute("lendLists",lendLists);
        return "reader/reader_lend_list";
    }
}
