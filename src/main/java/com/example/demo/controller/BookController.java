package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
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
    public Result<Book> addBook(@Valid @RequestBody Book book) {
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

    // 技能：接收删除请求
// @DeleteMapping 表示这个接口专门处理“删除”动作
// {id} 是个占位符，客人传什么，我们就接什么
    @DeleteMapping("/{id}")
    public Result<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success("ID 为 " + id + " 的书已成功撤除！");
    }

    // 接收修改请求
    @PutMapping("/{id}")
    public Result<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return Result.success(updatedBook);
    }
}
