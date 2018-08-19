package com.l0veyt.example.ssoserver.repository;

import com.l0veyt.example.ssoserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
