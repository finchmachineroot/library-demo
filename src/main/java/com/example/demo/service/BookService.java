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

    // 主厨的新技能：撤掉某本书
    public void deleteBook(Long id) {
        // 1. 先检查书是否存在（防止删了个寂寞）
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("操作失败：没找到 ID 为 " + id + " 的书！");
        }
        // 2. 确认存在，直接删除
        bookRepository.deleteById(id);
    }

    // 主厨新技能：翻新（修改）图书
    public Book updateBook(Long id, Book newBookData) {
        // 1. 先去仓库看看，这本书到底在不在
        return bookRepository.findById(id)
                .map(book -> {
                    // 2. 找到了！把新名字、新作者写上去
                    book.setTitle(newBookData.getTitle());
                    book.setAuthor(newBookData.getAuthor());
                    // 3. 让仓库管理员存回去
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("修改失败：没找到 ID 为 " + id + " 的书！"));
    }
}
