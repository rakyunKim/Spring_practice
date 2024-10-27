package kr.ccfc.service;


import kr.ccfc.model.LoginForm;
import kr.ccfc.model.LoginUser;

public interface LoginService {

    boolean logOut(String token);
}
