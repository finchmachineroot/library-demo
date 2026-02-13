package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 标记为“主厨”
public class BookService {

    @Autowired // 呼叫仓库管理员
    private BookRepository bookRepository;

    // 主厨的技能1：做新菜（存书）
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // 主厨的技能2：看菜单（查所有书）
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}
