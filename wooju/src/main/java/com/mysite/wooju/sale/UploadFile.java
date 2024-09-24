package com.mysite.wooju.sale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
   private String uploadFilename;
   private String storeFilename;
}