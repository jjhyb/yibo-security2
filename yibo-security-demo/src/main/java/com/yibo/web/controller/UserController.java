package com.yibo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yibo.dal.dto.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2019/7/26 23:48
 * @Description:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @ApiOperation(value = "用户注册服务")
    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request){
        //不管是注册用户还是绑定用户都会拿到一个用户唯一标识
        String userId = user.getUsername();
        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
    }

    @ApiOperation(value = "获取认证用户信息服务")
    @GetMapping("/me1")
    public Object getCurrentUser1(){
        //获取认证用户信息
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @ApiOperation(value = "获取认证用户信息服务")
    @GetMapping("/me2")
    public Object getCurrentUser2(Authentication authentication){
        //上面方法和本方法效果一样
        //获取认证用户信息
        return authentication;
    }

    @ApiOperation(value = "获取认证用户信息服务")
    @GetMapping("/me3")
    public Object getCurrentUser3(@AuthenticationPrincipal UserDetails userDetails){

        //获取认证用户信息,只获取其中principal中的信息
        return userDetails;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> query(@RequestParam String username, @PageableDefault(page = 1,size = 10,sort = "username,desc") Pageable pageable){

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }


    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id){
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping
    @JsonView(User.UserSimpleView.class)
    public User create(@Valid @RequestBody User user, BindingResult erros){
        if(erros.hasErrors()){
            erros.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user);
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    @JsonView(User.UserSimpleView.class)
    public User update(@Valid @RequestBody User user, BindingResult erros){
        if(erros.hasErrors()){
            erros.getAllErrors().stream().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
        }
        System.out.println(user);
        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public boolean  delete(@ApiParam("用户id") @PathVariable String id){
        System.out.println(id);
        return true;
    }
}
