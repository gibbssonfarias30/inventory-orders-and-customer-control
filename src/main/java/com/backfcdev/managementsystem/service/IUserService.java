package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.dto.request.UserRequest;
import com.backfcdev.managementsystem.dto.response.UserResponse;
import com.backfcdev.managementsystem.model.User;

public interface IUserService extends ICRUD<User, UserRequest, UserResponse, Long> {
}
