package com.mysite.wooju;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/start";
    }
    
    @RequestMapping("/start")
    public String start_page() {
    	return "start_page.html";
    }
 
    
    @RequestMapping("/sale/detail")
    public String detail() {
    	return "sale_detail.html";
    }
    
   /** @RequestMapping("/sale/create")
    public String form() {
    	return "sale_form_.html";
    }**/
}