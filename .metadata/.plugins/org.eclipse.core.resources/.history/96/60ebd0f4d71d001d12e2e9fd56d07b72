package com.mysite.wooju.sale;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.mysite.wooju.category.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleForm {

    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
    
    @NotEmpty(message="가격은 필수항목입니다.")
    private String price;
    
    @NotNull(message="지역은 필수항목입니다.")
    private Category category;

    
    
    private List<MultipartFile> imageFiles;

}