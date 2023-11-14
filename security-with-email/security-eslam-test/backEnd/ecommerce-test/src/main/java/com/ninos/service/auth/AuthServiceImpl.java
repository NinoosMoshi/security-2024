package com.ninos.service.auth;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ninos.exception.ProjectApiException;
import com.ninos.model.dto.AccountResponse;
import com.ninos.model.dto.ActiveAccount;
import com.ninos.model.dto.JwtAuthResponse;
import com.ninos.model.dto.LoginDTO;
import com.ninos.model.dto.Mail;
import com.ninos.model.dto.RegisterDTO;
import com.ninos.model.dto.UserActive;
import com.ninos.model.entity.Code;
import com.ninos.model.entity.Role;
import com.ninos.model.entity.User;
import com.ninos.repository.RoleRepository;
import com.ninos.repository.UserRepository;
import com.ninos.security.JwtTokenProvider;
import com.ninos.service.email.EmailService;
import com.ninos.util.UserCode;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final EmailService emailService;



    @Override
    public AccountResponse register(RegisterDTO registerDTO) {

        AccountResponse accountResponse = new AccountResponse();

        // check if username is exists in database
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            accountResponse.setResult(0);
            throw new ProjectApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
        }

        // check if email is exists in database
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            accountResponse.setResult(0);
            throw new ProjectApiException(HttpStatus.BAD_REQUEST, "Email is already exists");
        }


        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setActive(0);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_CUSTOMER");
        roles.add(userRole);
        user.setRoles(roles);

        String myCode = UserCode.getCode();
        Mail mail = new Mail(registerDTO.getEmail(), myCode);
        emailService.sendCodeByMail(mail);

        Code code = new Code();
        code.setCode(myCode);
        user.setCode(code);
        userRepository.save(user);

        accountResponse.setResult(1);

        return accountResponse;
    }


    @Override
    public JwtAuthResponse login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional = userRepository
                .findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());

        String role= null;
        if(userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();
            if (optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setId(userOptional.get().getId());

        return jwtAuthResponse;
    }



//    @Transactional  // we add @Transactional to manage our session
//    @Override
//    public int getUserActive(String username, String email){
//        return userRepository.getActiveByUsernameOrEmail(username, email);
//    }

//    @Transactional
//    @Override
//    public String getPasswordByUsernameOrEmail(String username,String email) {
//        return userRepository.getPasswordByUsernameOrEmail(username, email);
//    }

    @Transactional
    @Override
    public UserActive getUserActive(LoginDTO loginDTO) {

        String passwordDB = userRepository.getPasswordByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());
        boolean matchesPassword = passwordEncoder.matches(loginDTO.getPassword(),passwordDB);

        UserActive userActive = new UserActive();

        if(matchesPassword){
            int activeByUsernameOrEmail = userRepository.getActiveByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail());
            userActive.setActive(activeByUsernameOrEmail);
        }
        else{
            userActive.setActive(-1);
        }

        return userActive;
    }

    @Override
    public User getByUsernameOrEmail(String username, String email) {
       return userRepository.findByUsernameOrEmail(username, email).orElseThrow(() -> new UsernameNotFoundException("Username or Email not Found"));
    }


    @Override
    public AccountResponse activeAccount(ActiveAccount activeAccount) {
        User user = userRepository.findByUsernameOrEmail(activeAccount.getUsernameOrEmail(), activeAccount.getUsernameOrEmail()).orElseThrow(() -> new UsernameNotFoundException("Username or Email not Found"));

        AccountResponse accountResponse = new AccountResponse();

        if(user.getCode().getCode().equals(activeAccount.getCode())){
             user.setActive(1);
             userRepository.save(user);
             accountResponse.setResult(1);
        }
        else{
            accountResponse.setResult(0);
        }

           return accountResponse;
    }






//    @Override
//    public User editUser(User user) {
//        return userRepository.save(user);
//    }


}
