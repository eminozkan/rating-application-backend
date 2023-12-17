package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.business.core.result.CreationResult;
import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.core.dto.User;
import dev.ozkan.ratingapp.core.user.UserPasswordEncoder;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultRegistrationService implements RegistrationService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger();

    private final UserPasswordEncoder userPasswordEncoder;

    public DefaultRegistrationService(UserRepository userRepository, UserPasswordEncoder userPasswordEncoder) {
        this.userRepository = userRepository;
        this.userPasswordEncoder = userPasswordEncoder;
    }

    @Override
    public CreationResult<User> register(RegistrationServiceRequest request) {
        Optional<User> userWithEmailOptional = userRepository.getByEmail(request.getEmail());
        if (userWithEmailOptional.isPresent()) {
            logger.debug("User {} has already registered.", request.getEmail());
            return CreationResult.failure(OperationFailureReason.CONFLICT, "User has already registered");
        }

        Optional<User> userWithUsernameOptional = userRepository.getByUsername(request.getUsername());
        if (userWithUsernameOptional.isPresent()) {
            logger.debug("User {} has already registered.", request.getUsername());
            return CreationResult.failure(OperationFailureReason.CONFLICT, "User has already registered");
        }
        String passwordHash = userPasswordEncoder.encodePassword(request.getPassword());
        var user = new User()
                .setUserId(UUID.randomUUID().toString())
                .setEmail(request.getEmail())
                .setUsername(request.getUsername())
                .setPasswordHash(passwordHash)
                .setName(request.getName());

        userRepository.save(user);
        return CreationResult.success(user,"userId : " + user.getUserId());
    }
}
