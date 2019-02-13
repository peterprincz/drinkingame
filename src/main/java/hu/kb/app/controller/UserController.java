package hu.kb.app.controller;

import hu.kb.app.modell.User;
import hu.kb.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String hello(){
        return userService.sayHelloToEveryone();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveUser(@RequestBody User user){
        userService.addUser(user);
        return "example";
    }

}
