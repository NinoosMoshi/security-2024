package com.ninos.service.auth;

import com.ninos.model.dto.AccountResponse;
import com.ninos.model.dto.ActiveAccount;
import com.ninos.model.dto.JwtAuthResponse;
import com.ninos.model.dto.LoginDTO;
import com.ninos.model.dto.RegisterDTO;
import com.ninos.model.dto.UserActive;
import com.ninos.model.entity.User;

public interface AuthService {

    AccountResponse register(RegisterDTO registerDTO);
    JwtAuthResponse login(LoginDTO loginDTO);

//    int getUserActive(String username, String email);
//    String getPasswordByUsernameOrEmail(String username, String email);

    UserActive getUserActive(LoginDTO loginDTO);
    User getByUsernameOrEmail(String username, String email);
    AccountResponse activeAccount(ActiveAccount activeAccount);

//    User editUser(User user);

}
