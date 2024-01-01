package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.config.UserPasswordEncoderAdapter;
import dev.ozkan.ratingapp.core.jwt.JwtService;
import dev.ozkan.ratingapp.core.model.user.User;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserPasswordEncoderAdapter passwordEncoderAdapter;

    private DefaultAuthenticationService service;

    @BeforeEach
    void setUp() {
        service = new DefaultAuthenticationService(userRepository, passwordEncoderAdapter, jwtService);
    }

    @DisplayName("No user")
    @Test
    void nonExistUser() {
        var request = new AuthenticationServiceRequest()
                .setEmail("user@email.com")
                .setPassword("password");

        Mockito.doReturn(Optional.empty())
                .when(userRepository)
                .getByEmail(request.getEmail());

        var result = service.authenticate(request);

        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.NOT_FOUND, result.getReason());
    }

    @DisplayName("Wrong Password")
    @Test
    void wrongPassword() {
        var request = new AuthenticationServiceRequest()
                .setEmail("user@email.com")
                .setPassword("password");

        var user = new User()
                .setUserId("userId")
                .setEmail("user@email.com")
                .setPasswordHash("passwordHash")
                .setName("User");

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .getByEmail(request.getEmail());

        Mockito.doReturn(false)
                .when(passwordEncoderAdapter)
                .matches(request.getPassword(), user.getPasswordHash());

        var result = service.authenticate(request);

        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.UNAUTHORIZED, result.getReason());
    }

    @DisplayName("Success")
    @Test
    void success(){
        var request = new AuthenticationServiceRequest()
                .setEmail("user@email.com")
                .setPassword("password");

        var user = new User()
                .setUserId("userId")
                .setEmail("user@email.com")
                .setPasswordHash("passwordHash")
                .setName("User");

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .getByEmail(request.getEmail());

        Mockito.doReturn(true)
                .when(passwordEncoderAdapter)
                .matches(request.getPassword(), user.getPasswordHash());

        var result = service.authenticate(request);

        assertTrue(result.isSuccess());
        Mockito.verify(jwtService)
                .generateToken(user);
    }
}