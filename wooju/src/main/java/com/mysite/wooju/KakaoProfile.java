package com.mysite.wooju;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoProfile {

public Long id;
public String connected_At;
public Properties properties;
public KakaoAccount kakao_account;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Properties {
public String nickname;

}

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoAccount {
public Boolean profile_nickname_needs_agreement;
public Profile profile;
public Boolean hasEmail;
public Boolean email_needs_agreement;
//public Boolean has_age_range;
//public Boolean age_range_needs_agreement;
public Boolean has_gender;
public Boolean gender_needs_agreement;
public String email;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Profile {
public String nickname;

}

}



}






