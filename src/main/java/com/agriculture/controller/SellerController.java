package com.agriculture.controller;

import com.agriculture.common.R;
import com.agriculture.entity.Seller;
import com.agriculture.entity.User;
import com.agriculture.service.SellerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 农户登录
     * @param request
     * @param seller
     * @return
     */
    @PostMapping("/login")
    public R<Seller> login(HttpServletRequest request, @RequestBody Seller seller){
        //1、将页面提交的密码password进行md5加密处理
        String password = seller.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getName,seller.getName());
        Seller s = sellerService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(s == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!s.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("seller",s.getId());
        return R.success(s);
    }

    /**
     * 农户注册
     * @param seller
     * @return
     */
    @PostMapping("/register")
    public R<String> register(@RequestBody Seller seller){
        if(seller.getName() == null || seller.getPassword() == null){
            return R.error("用户名或密码不能为空");
        }

        //1、将页面提交的密码password进行md5加密处理
        seller.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getName,seller.getName());
        Seller s = sellerService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(s == null){
            // 能注册，将数据写进数据库
            sellerService.save(seller);
            return R.success("注册成功");
        }else{
            // 不能注册
            return R.error("用户名重复");
        }
    }

    /**
     * 农户退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("seller");
        return R.success("退出成功");
    }
}
