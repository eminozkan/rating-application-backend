package dev.ozkan.ratingapp.core.user;

import dev.ozkan.ratingapp.repository.UserRepository;
import dev.ozkan.ratingapp.support.masker.EmailMasker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserProfilePayload> getUserInformation(String userId) {
        var userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        var userFromDb = userOptional.get();
        var payload = new UserProfilePayload()
                .setName(userFromDb.getName())
                .setUserType(userFromDb.getRole().toString())
                .setMaskedEmail(EmailMasker.maskEmail(userFromDb.getEmail()));

        return Optional.of(payload);
    }
}
