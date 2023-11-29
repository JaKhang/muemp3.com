package com.ja.muemp3.services.v1;

import com.ja.muemp3.entities.User;
import com.ja.muemp3.entities.constants.AuthProvider;
import com.ja.muemp3.entities.constants.Role;
import com.ja.muemp3.entities.constants.UserStatus;
import com.ja.muemp3.exception.OAuth2AuthenticationProcessingException;
import com.ja.muemp3.exception.UserNotFoundException;
import com.ja.muemp3.factories.OAuth2UserInfoFactory;
import com.ja.muemp3.payload.auth.RegisterRequest;
import com.ja.muemp3.payload.auth.UserInfoResponse;
import com.ja.muemp3.repositories.UserRepository;
import com.ja.muemp3.security.user.OAuth2UserInfo;
import com.ja.muemp3.security.user.UserPrincipal;
import com.ja.muemp3.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DefaultUserService extends DefaultOAuth2UserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


//    @Autowired
//    private ConfirmationService confirmationService;
//
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    @Autowired
//    private LinkFactory urlFactory;


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

    @Override
    @Transactional
    public UserDetails registerNewUser(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .status(UserStatus.NEW)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .role(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .emailVerified(false)
                .build();
        user = userRepository.save(user);
        sendConfirmationEmail(user.getEmail());
        return UserPrincipal.create(user);
    }

    @Override
    public void sendConfirmationEmail(String email) {

    }

    @Override
    public UserDetails confirmToken(String token) {
        return null;
    }

//    @Override
//    public void sendConfirmationEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new ResourceNotFoundException("User", "email", email)
//        );
//
//        ConfirmationToken confirmationToken = ConfirmationToken.builder()
//                .token(UUID.randomUUID().toString())
//                .createAt(LocalDateTime.now())
//                .expiredAt(LocalDateTime.now().plusMinutes(15))
//                .user(user)
//                .build();
//        confirmationService.save(confirmationToken);
//
//
//        Context context = new Context();
//        context.setVariable("url", urlFactory.createConfirmationUrl(confirmationToken.getToken()));
//        context.setVariable("username", user.getFullName());
//
//        emailSenderService.send(email, "Confirm your email", "confirmation-email", context);
//    }

//    @Override
//    public UserDetails confirmToken(String confirmationTokenString) {
//        ConfirmationToken confirmationToken = confirmationService.confirmToken(confirmationTokenString);
//        User user = confirmationToken.getUser();
//        user.setEmailVerified(true);
//        userRepository.save(user);
//        return UserPrincipal.create(user);
//
//    }

    @Override
    public UserInfoResponse getUserInfoById(UUID id) {
        User user = findByIdElseThrow(id);
        return UserInfoResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .userStatus(user.getStatus())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .provider(user.getAuthProvider())
                .build();
    }

    @Override
    public void addFavouriteSong(UUID userId, UUID songId) {

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
        Favourite Song
    --------------------*/
//    @Override
//    public void addFavouriteSong(UUID userId, UUID songId) {
//        User user = findByIdElseThrow(userId);
//        Song song = songService.findByIdElseThrow(songId);
//        user.getFavouriteSongs().add(song);
//        userRepository.save(user);
//    }


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
                .emailVerified(true)
                .build();
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }


    private static final String mailTemplate = """
            """;


}
