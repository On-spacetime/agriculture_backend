package com.agriculture.service.impl;

import com.agriculture.mapper.AddressBookMapper;
import com.agriculture.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.agriculture.entity.AddressBook;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
