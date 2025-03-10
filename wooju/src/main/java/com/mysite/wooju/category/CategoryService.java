package com.mysite.wooju.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysite.wooju.DataNotFoundException;
import com.mysite.wooju.sale.Sale;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getCategory(){
		
		List<Category> menuList = categoryRepository.findAll();
		return menuList;
		
	}
	
	public Category getCategory2(Integer id){
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new DataNotFoundException("category not found");
        }
	}
	

}
