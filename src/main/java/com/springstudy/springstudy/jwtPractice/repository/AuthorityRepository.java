package com.springstudy.springstudy.jwtPractice.repository;

import com.springstudy.springstudy.jwtPractice.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}