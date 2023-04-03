package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;

public interface AuthService {
    Member authenticate(String email, String password);
}

