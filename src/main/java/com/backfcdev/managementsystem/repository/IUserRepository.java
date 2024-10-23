package com.backfcdev.managementsystem.repository;

import com.backfcdev.managementsystem.model.User;

import java.util.Optional;

public interface IUserRepository extends IGenericRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
