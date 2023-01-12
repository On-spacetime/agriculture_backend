package com.agriculture.service.impl;

import com.agriculture.entity.Seller;
import com.agriculture.mapper.SellerMapper;
import com.agriculture.service.SellerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {
}
