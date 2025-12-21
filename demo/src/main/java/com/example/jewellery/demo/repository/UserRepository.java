package com.example.jewellery.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.jewellery.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    
    @Query("""
    		{
    		  $or: [
    		    { "username": { $regex: ?0, $options: "i" } },
    		    { "email":    { $regex: ?0, $options: "i" } },
    		    { "address":  { $regex: ?0, $options: "i" } },
    		    { "district": { $regex: ?0, $options: "i" } },
    		    { "state":    { $regex: ?0, $options: "i" } }
    		  ]
    		}
    		""")
    		List<User> searchUsers(String keyword);

}
