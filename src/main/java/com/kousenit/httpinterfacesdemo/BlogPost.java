package com.kousenit.httpinterfacesdemo;

public record BlogPost(Integer id,
                       Integer userId,
                       String title,
                       String body) {
}
