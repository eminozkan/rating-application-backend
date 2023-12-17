package dev.ozkan.ratingapp.mongodbrepository.user;

import dev.ozkan.ratingapp.core.dto.User;
import dev.ozkan.ratingapp.repository.UserRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MongoDBUserRepository extends UserRepository, org.springframework.data.repository.Repository<User,String> {
}
