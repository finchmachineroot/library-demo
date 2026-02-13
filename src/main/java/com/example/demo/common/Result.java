package com.example.demo.common;

import lombok.Data;

@Data // 自动生成 Getter 和 Setter
public class Result<T> {

    // 1. 状态码：比如 200 代表成功，500 代表后厨着火了
    private Integer code;

    // 2. 消息：给客人看的大白话，比如“点餐成功”或“食材没了”
    private String message;

    // 3. 数据内容：这就是那个“信封”里的具体东西（比如一本书，或者一个列表）
    // 用 <T> 是为了让它能装任何东西，这叫“泛型”
    private T data;

    // 快捷方法：成功时的精美包装
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    // 快捷方法：失败时的警告通知
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
