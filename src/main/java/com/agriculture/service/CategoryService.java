package com.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.agriculture.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
