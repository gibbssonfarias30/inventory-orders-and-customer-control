package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.dto.request.UserRequest;
import com.backfcdev.managementsystem.dto.response.UserResponse;
import com.backfcdev.managementsystem.mapper.UserMapper;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.model.User;
import com.backfcdev.managementsystem.repository.IUserRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.IUserService;
import com.backfcdev.managementsystem.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends CRUDImpl<User, UserRequest, UserResponse, Long> implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    protected IGenericRepository<User, Long> repository() {
        return userRepository;
    }

    @Override
    protected IMapper<User, UserRequest, UserResponse> mapper() {
        return userMapper;
    }

    @Override
    public UserResponse save(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setRolId(Constants.USER);
        return super.save(request);
    }
}
