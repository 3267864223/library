package com.springbootlibrary.controller;

import com.springbootlibrary.mapper.bookMapper.BookMapper;
import com.springbootlibrary.pojo.BookInfo;
import com.springbootlibrary.utils.BookInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/allBooks.html")
    public ModelAndView toAllBooks() {
        List<BookInfo> bookInfoList = bookMapper.selectAllBook();
        ModelAndView modelAndView = new ModelAndView("admin/admin_books");
        modelAndView.addObject("bookInfoList", bookInfoList);
        return modelAndView;
    }

    @GetMapping("/bookDetail")
    public String toBookDetail(@RequestParam("bookId") String bookId, Model model) {
        Integer id = Integer.parseInt(bookId);
        BookInfo bookInfo = bookMapper.selectByBookId(id);
        model.addAttribute("book", bookInfo);
        return "admin/admin_book_detail";
    }

    @GetMapping("/bookEdit")
    public String toBookEdit(String bookId, Model model) {
        Integer id = Integer.parseInt(bookId);
        BookInfo bookInfo = bookMapper.selectByBookId(id);
        model.addAttribute("book", bookInfo);
        return "admin/admin_book_edit";
    }

    @PostMapping("/bookEditDo")
    public String bookEditDo(RedirectAttributes redirectAttributes, String bookId, String name, String author, String publish, String isbn, String introduction, String language, String price, String pubdate, String classId, String pressmark, String state) {
        Integer id = Integer.parseInt(bookId);
        BookInfo bookInfo = bookMapper.selectByBookId(id);
        BookInfoSet bookInfoSet = new BookInfoSet(bookInfo);
        BookInfo newBookInfo = bookInfoSet.bookInfoSet(name, author, publish, isbn, introduction, language, price, pubdate, classId, pressmark, state);
        bookMapper.updateBook(newBookInfo);
        redirectAttributes.addFlashAttribute("succ","书籍修改成功");
        return "redirect:/allBooks.html";
    }

    @GetMapping("/bookDelete")
    public String bookDelete(String bookId,RedirectAttributes redirectAttributes){
        Integer id = Integer.parseInt(bookId);
        bookMapper.deleteBook(id);
        redirectAttributes.addFlashAttribute("succ","图书删除成功");
        return "redirect:/allBooks.html";
    }

    @PostMapping("/queryBook")
    public String queryBookLike(String searchWord,Model model){
        List<BookInfo> bookInfoList = bookMapper.selectBookByName(searchWord);
        if (bookInfoList.size() != 0){ //列表用长度去判断里面有没有值
            model.addAttribute("succ","以下是匹配到的书籍");
        } else {
            model.addAttribute("error","没有匹配的书籍");
        }
        model.addAttribute("bookInfoList",bookInfoList);
        return "admin/admin_books";
    }

    @GetMapping("/book_add.html")
    public String toBookAddPage(){
        return "admin/admin_book_add";
    }

    @PostMapping("/bookAddDo")
    public String bookAddDo(RedirectAttributes redirectAttributes,String name, String author, String publish, String isbn, String introduction, String language, String price, String pubdate, String classId, String pressmark, String state){
        BookInfoSet bookInfoSet = new BookInfoSet(new BookInfo());
        BookInfo bookInfo = null;
        try {
            bookInfo = bookInfoSet.bookInfoSet(name, author, publish, isbn, introduction, language, price, pubdate, classId, pressmark, state);
            bookMapper.insertBook(bookInfo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error","书籍添加失败,别瞎几把乱输!!!");
            return "redirect:/allBooks.html";
        }
        redirectAttributes.addFlashAttribute("succ","图书添加成功");
        return "redirect:/allBooks.html";
    }

    @GetMapping("/reader/queryBook")
    public ModelAndView toReaderQueryBookPage(){
        List<BookInfo> bookInfoList = bookMapper.selectAllBook();
        ModelAndView modelAndView = new ModelAndView("reader/reader_book_query");
        modelAndView.addObject("bookInfoList", bookInfoList);
        return modelAndView;
    }
    @PostMapping("/reader/searchBoook")
    public String readerSearchBook(String searchWord,Model model){
        List<BookInfo> bookInfoList = bookMapper.selectBookByName(searchWord);
        if (bookInfoList.size() != 0){ //列表用长度去判断里面有没有值
            model.addAttribute("succ","以下是匹配到的书籍");
        } else {
            model.addAttribute("error","没有匹配的书籍");
        }
        model.addAttribute("bookInfoList",bookInfoList);
        return "reader/reader_book_query";
    }

    @GetMapping("/reader/bookDetail")
    public String toReaderBookEditPage(String bookId,Model model){
        Integer id = Integer.parseInt(bookId);
        BookInfo bookInfo = bookMapper.selectByBookId(id);
        model.addAttribute("book",bookInfo);
        return "reader/reader_book_detail";
    }


}
