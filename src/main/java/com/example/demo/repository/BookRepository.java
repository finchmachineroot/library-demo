package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // 继承了 JpaRepository 之后，你就自带了“增删改查”全家桶，一行代码都不用写！
    List<Book> findByTitleContainingOrAuthorContaining(String titleKeyword, String authorKeyword);
}