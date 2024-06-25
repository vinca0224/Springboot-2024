package com.vinca.backboard.service;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.vinca.backboard.common.NotFoundException;
import com.vinca.backboard.entity.Category;
import com.vinca.backboard.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;    // bean 생성

    // 카테고리를 생성하는 메서드
    public Category setCategory(String title){
        Category cate = new Category();
        cate.setTitle(title);
        cate.setCreateDate(LocalDateTime.now());
    
        Category category = this.categoryRepository.save(cate);
        return category;
    }

    // free, qna. notice
    // 카테고리 가져오는 메소드
    public Category getCategory(String title){
        Optional<Category> cate = this.categoryRepository.findByTitle(title);
        if(cate.isEmpty()){ // free나 qna 타이틀의 카테고리 데이터가 없으면
            cate = Optional.ofNullable(setCategory(title)); // 테이블에 해당 카테고리를 생성
        
        }
        if(cate.isPresent()){
            return cate.get();  // category 리턴
        }
        else{
            throw new NotFoundException("Category not found");  // 발생할 일이 없음
        }
    }
}
