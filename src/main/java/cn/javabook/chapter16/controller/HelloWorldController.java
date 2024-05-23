package cn.javabook.chapter16.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller接口
 *
 */
@RestController
public class HelloWorldController {
    /**
     * 调用scala类中的方法
     *
     */
    @GetMapping("/")
    public void said(@RequestParam("name") final String name,
                     @RequestParam("age") final int age) {
        HelloGreet hello = new HelloGreet();
        hello.greet(name, age);
    }
}
