package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.core.model.user.User;
import dev.ozkan.ratingapp.core.user.UserPasswordEncoder;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultRegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPasswordEncoder userPasswordEncoder;

    private DefaultRegistrationService registrationService;

    private RegistrationServiceRequest request;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        registrationService = new DefaultRegistrationService(userRepository, userPasswordEncoder);
        request = new RegistrationServiceRequest()
                .setEmail("unique-email@email.tld")
                .setPassword("password")
                .setName("Name");
    }

    @DisplayName("Try registration with non-unique email")
    @Test
    void emailIsNotUnique() {

        request.setEmail("non-unique@email.tld");

        Mockito.doReturn(Optional.of(new User()))
                .when(userRepository)
                .getByEmail(request.getEmail());

        var result = registrationService.register(request);
        assertFalse(result.isSuccess());
        assertEquals(OperationFailureReason.CONFLICT, result.getReason());
        Mockito.verifyNoMoreInteractions(userRepository);
    }


    @DisplayName("Success")
    @Test
    void success(){
        Mockito.doReturn(Optional.empty())
                .when(userRepository)
                .getByEmail(request.getEmail());

        Mockito.doReturn("encodedPassword")
                .when(userPasswordEncoder)
                .encodePassword(request.getPassword());

        var result = registrationService.register(request);
        assertTrue(result.isSuccess());

        Mockito.verify(userRepository)
                .save(userArgumentCaptor.capture());

        var capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getUserId()).isNotNull().isNotEmpty().isNotBlank();
        assertEquals(request.getEmail(),capturedUser.getEmail());
        assertEquals("encodedPassword",capturedUser.getPasswordHash());
        assertEquals(request.getName(),capturedUser.getName());
    }


}