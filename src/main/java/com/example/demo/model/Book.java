package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*; // 这里的 jakarta 是新版 Java 的标准
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data // 自动剥蒜机：帮你写好 Getter/Setter
@Entity // 告诉冷库（数据库）：按照这个样子给我建个表
@Schema(description = "图书实体")
@SQLDelete(sql = "UPDATE book SET is_deleted = 1 WHERE id = ?") // 执行删除时，自动变为更新语句
@Where(clause = "is_deleted = 0") // 所有的查询（findAll等）都会自动带上这个过滤条件
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自动递增
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "图书名称", example = "Java从入门到精通")
    @Size(min = 1, max = 50, message = "【书名】长度必须在1-50个字符之间")
    @NotBlank(message = "书名不能为空") // 核心注解：不仅不能为 null，还不能是空格
    private String title;  // 书名

    @Schema(description = "作者")
    @NotBlank(message = "作者不能为空")
    private String author; // 作者

    @NotNull(message = "【价格】不能为空")
    @Min(value = 0, message = "【价格】不能为负数")
    @Schema(description = "价格")
    private Double price;

    // 关键字段：0-正常，1-已删除
    @Schema(description = "是否删除：0-未删，1-已删")
    private Integer is_deleted = 0;
}