package com.mysite.wooju.user;



import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.wooju.KakaoProfile;


/**import com.mysite.wooju.category.Category;
import com.mysite.wooju.category.CategoryService;***/


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")

public class UserController {
	
	@Value("${coskey}")
	private String cosKey;
	
    private final UserService userService;
    

    
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), 
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/sale/list";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
    RestTemplate rt = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", "301a6639546e52f69c69f0696ec14651");
    params.add("redirect_uri", "http://localhost:8080/user/auth/kakao/callback");
    params.add("code", code);
    
    HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
    		new HttpEntity<>(params, headers);
    
    ResponseEntity<String> response = rt.exchange(
    		"https://kauth.kakao.com/oauth/token", 
    		HttpMethod.POST,
    		kakaoTokenRequest,
    		String.class);
    
    ObjectMapper objectMapper = new ObjectMapper();
    OAuthToken oauthToken = null;
    try {
		oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
    
    System.out.println("카카오 엑세스 토큰 :" +oauthToken.getAccess_token());
    
    RestTemplate rt2 = new RestTemplate();
    HttpHeaders headers2 = new HttpHeaders();
    headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
    headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    
    HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = 
    		new HttpEntity<>(headers2);
    
    ResponseEntity<String> response2 = rt2.exchange(
    		"https://kapi.kakao.com/v2/user/me", 
    		HttpMethod.POST,
    		kakaoProfileRequest2,
    		String.class);
    
    System.out.println(response2.getBody());
    
    ObjectMapper objectMapper2 = new ObjectMapper();
    KakaoProfile kakaoProfile = null;
    try {
    	kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
    System.out.println("카카오 아이디: "+kakaoProfile.getId());
    System.out.println("카카오 계정 정보: "+kakaoProfile.getKakao_account());
    System.out.println("카카오 닉네임: "+kakaoProfile.getProperties().getNickname());
    
    System.out.println("우주마켓 유저네임: " +kakaoProfile.getProperties().getNickname()+kakaoProfile.getId());
    
    System.out.println("우주마켓 패스워드: "+ cosKey);
   
    System.out.println("회원 검색 시작 ");
    
    SiteUser originUser = userService.getUser(kakaoProfile.getProperties().getNickname()+kakaoProfile.getId());
    
    
    if(originUser == null) {
    	System.out.println("자동회원가입진행");
    	userService.create(kakaoProfile.getProperties().getNickname()+kakaoProfile.getId(), kakaoProfile.getKakao_account().getEmail(), cosKey);
    	System.out.println("유저 만들어졌나");
    }
    else{
    	System.out.println("이미 존재하는 회원.");	
    }
    
   System.out.println("카카오 연동 완료 ");
    
   Authentication authentication = new UsernamePasswordAuthenticationToken(kakaoProfile.getProperties().getNickname()+kakaoProfile.getId(), cosKey);
   SecurityContextHolder.getContext().setAuthentication(authentication);
   
 
    return "redirect:/sale/list";
    
    
    }
}
    
