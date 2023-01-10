package com.agriculture.service.impl;

import com.agriculture.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.agriculture.entity.User;
import com.agriculture.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
