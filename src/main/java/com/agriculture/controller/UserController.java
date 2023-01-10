package com.agriculture.controller;

import com.agriculture.common.R;
import com.agriculture.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.agriculture.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 员工登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){

        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User u = userService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(u == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!u.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user",u.getId());
        return R.success(u);
    }

    /**
     * 用户注册
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R<String> register(HttpServletRequest request, @RequestBody User user){

        if(user.getName() == null || user.getPassword() == null){
            return R.error("用户名或密码不能为空");
        }

        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User u = userService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(u == null){
            // 能注册，将数据写进数据库
            userService.save(user);
            return R.success("注册成功");
        }else{
            // 不能注册
            return R.error("用户名重复");
        }
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }
}
