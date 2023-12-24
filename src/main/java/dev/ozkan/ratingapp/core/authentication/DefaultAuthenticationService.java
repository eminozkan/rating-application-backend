package dev.ozkan.ratingapp.core.authentication;

import dev.ozkan.ratingapp.business.core.result.AuthenticationResult;
import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.config.jwt.JwtService;
import dev.ozkan.ratingapp.core.user.UserPasswordEncoder;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements AuthenticationService{
    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;

    private final Logger logger = LogManager.getLogger();
    private final JwtService jwtService;

    public DefaultAuthenticationService(UserRepository userRepository, UserPasswordEncoder userPasswordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userPasswordEncoder = userPasswordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticationResult authenticate(AuthenticationServiceRequest request) {
        var userOptional = userRepository.getByEmail(request.getEmail());
        if (userOptional.isEmpty()){
            logger.debug("User {} has not found.",request.getEmail());
            return AuthenticationResult.failed(OperationFailureReason.NOT_FOUND,"User has not found");
        }
        var userFromDb = userOptional.get();
        if(!userPasswordEncoder.matches(request.getPassword(),userFromDb.getPasswordHash())){
            logger.debug("User {} tried to login with wrong password.",request.getEmail());
            return AuthenticationResult.failed(OperationFailureReason.UNAUTHORIZED,"Invalid credentials");
        }

        var jwtToken = jwtService.generateToken(userFromDb);
        return AuthenticationResult.success(jwtToken);
    }
}
