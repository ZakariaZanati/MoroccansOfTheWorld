package com.social.service;

import com.social.dto.*;
import com.social.exceptions.SpringRedditException;
import com.social.model.*;
import com.social.repository.UserRepository;
import com.social.repository.VerificationTokenRepository;
import com.social.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) throws Exception {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent() ||
                userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new Exception(
                    "There is an account with that email address or username: "
                            +  registerRequest.getEmail());
        }
        else{
            User user;
            System.out.println(registerRequest.getUserType());
            if (registerRequest.getUserType().equals("candidate")){
                user = new Candidate();
                user.setUsername(registerRequest.getUsername());
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setCreated(Instant.now());
                user.setUserType(UserType.CANDIDATE);
            }
            else {
                user = new Provider();
                user.setUsername(registerRequest.getUsername());
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setCreated(Instant.now());
                user.setUserType(UserType.PROVIDER);
            }
            user.setEnabled(true);
            user.setCompleted(false);
            userRepository.save(user);


            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("Please activate your account",
                    user.getEmail(),
                    "Thank you for signing up to our application, please click on the url below to activate your account : \n " +
                            "http://localhost:8181/api/auth/accountVerification/"+token));
        }

    }

    @Transactional
    public void saveInfos(UserDetailsDto userDetailsDto){
        User user = this.getCurrentUser();
        user.setFirstName(userDetailsDto.getFirstName());
        user.setLastName(userDetailsDto.getLastName());
        user.setBirthDate(userDetailsDto.getBirthDate());
        user.setCity(userDetailsDto.getCity());
        user.setCountry(userDetailsDto.getCountry());
        user.setAboutMe(userDetailsDto.getAboutMe());
        user.setPhoneNumber(userDetailsDto.getPhoneNumber());
        user.setCurrentJob(userDetailsDto.getCurrentJob());
        user.setWebsite(userDetailsDto.getWebsite());
        user.setCompleted(true);

        userRepository.save(user);
    }

    @Transactional
    public UserDetailsDto getInfos(){
        User user = this.getCurrentUser();

        return UserDetailsDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .city(user.getCity())
                .country(user.getCountry())
                .aboutMe(user.getAboutMe())
                .phoneNumber(user.getPhoneNumber())
                .currentJob(user.getCurrentJob())
                .website(user.getWebsite())
                .build();
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()->new SpringRedditException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken){
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("User Not Found with name - "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("test test");
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                    .authenticationToken(token)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(loginRequest.getUsername())
                    .completed(this.getCurrentUser().isCompleted())
                    .fullName(this.getCurrentUser().getFirstName()+" "+this.getCurrentUser().getLastName())
                    .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
