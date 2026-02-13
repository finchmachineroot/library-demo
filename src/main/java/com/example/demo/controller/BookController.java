package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 挂上“前台”的招牌，支持返回 JSON 数据
@RequestMapping("/books") // 这家店的地址是 /books
public class BookController {

    @Autowired // 呼叫主厨
    private BookService bookService;

    // 技能：接下客人的存书订单
    @PostMapping
    public Result<Book> addBook(@RequestBody Book book) {
        // 让主厨去处理，然后把结果装进 Result 信封
        Book savedBook = bookService.saveBook(book);
        return Result.success(savedBook);
    }

    // 技能：给客人展示所有图书
    @GetMapping
    public Result<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return Result.success(books);
    }
}
