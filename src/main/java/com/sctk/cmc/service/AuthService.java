package com.sctk.cmc.service;

import com.sctk.cmc.domain.Member;

public interface AuthService {
    Member authenticateMember(String email, String password);
}

