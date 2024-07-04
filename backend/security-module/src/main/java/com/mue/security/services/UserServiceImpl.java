package com.mue.security.services;

import com.mue.security.domain.OAuth2UserInfo;
import com.mue.security.domain.UserPrincipal;
import com.mue.entities.User;
import com.mue.enums.AuthProvider;
import com.mue.enums.Role;
import com.mue.enums.UserStatus;
import com.mue.core.exception.OAuth2AuthenticationProcessingException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
public class UserServiceImpl extends DefaultOAuth2UserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


//    @Autowired
//    private ConfirmationService confirmationService;
//
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    @Autowired
//    private URLFactory urlFactory;


    /*------------------
         UserDetails
    --------------------*/
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserById(UUID id) {
        User user = this.findByIdElseThrow(id);
        return UserPrincipal.create(user);
    }





    /*------------------
          OAuth2
    --------------------*/
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        System.out.println(userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }





    /*------------------
          Private
    --------------------*/
    private User findByIdElseThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + id)
                );
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        //convert to OAuth2UserInfo
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        System.out.println(oAuth2UserInfo);
        //checking email
        if (ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);

        if (user == null) {
            user = registerNewUser(userRequest, oAuth2UserInfo);
        } else {
            if (!user.getAuthProvider().equals(AuthProvider.fromString(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getAuthProvider() + " account. Please use your " + user.getAuthProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private @NonNull User registerNewUser(OAuth2UserRequest request, OAuth2UserInfo oAuth2UserInfo) {
        User user = User
                .builder()
                .authProvider(AuthProvider.fromString(request.getClientRegistration().getRegistrationId()))
                .email(oAuth2UserInfo.getEmail())
                .fullName(oAuth2UserInfo.getName())
                .avatar(oAuth2UserInfo.getImageUrl())
                .providerId(oAuth2UserInfo.getId())
                .status(UserStatus.NEW)
                .role(Role.USER)
                .emailVerified(Boolean.TRUE)
                .build();
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
