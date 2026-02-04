package com.Caching.Weather_Service.repository;

import com.Caching.Weather_Service.entity.Users;
import io.netty.util.internal.ObjectPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
