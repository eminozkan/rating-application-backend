package dev.ozkan.ratingapp.core.registration;

import dev.ozkan.ratingapp.support.result.CreationResult;
import dev.ozkan.ratingapp.support.result.OperationFailureReason;
import dev.ozkan.ratingapp.core.model.user.User;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.core.user.UserPasswordEncoder;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
    public CreationResult register(RegistrationServiceRequest request) {
        Optional<User> userWithEmailOptional = userRepository.getByEmail(request.getEmail());
        if (userWithEmailOptional.isPresent()) {
            logger.debug("User {} has already registered.", request.getEmail());
            return CreationResult.failure(OperationFailureReason.CONFLICT, "User has already registered");
        }

        String passwordHash = userPasswordEncoder.encodePassword(request.getPassword());
        var user = new User()
                .setUserId(UUID.randomUUID().toString())
                .setEmail(request.getEmail())
                .setPasswordHash(passwordHash)
                .setName(request.getName())
                .setRole(UserRole.ROLE_USER)
                .setLocked(false)
                .setEnabled(true);

        userRepository.save(user);
        return CreationResult.success(user.getUserId());
    }
}
