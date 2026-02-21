package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*; // 这里的 jakarta 是新版 Java 的标准
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.io.Serializable;

@Data // 自动剥蒜机：帮你写好 Getter/Setter
@Entity // 告诉冷库（数据库）：按照这个样子给我建个表
@Schema(description = "图书实体")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自动递增
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "图书名称", example = "Java从入门到精通")
    @NotBlank(message = "书名不能为空") // 核心注解：不仅不能为 null，还不能是空格
    private String title;  // 书名

    @Schema(description = "作者")
    @NotBlank(message = "作者不能为空")
    private String author; // 作者
}