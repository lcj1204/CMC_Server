package com.sctk.cmc.auth;

import com.sctk.cmc.auth.dto.LoginResponseDto;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Member;

public interface AuthService {
    Member authenticateMember(String email, String password);

    Designer authenticateDesigner(String email, String password);

    LoginResponseDto loginMember(String email, String password);

    LoginResponseDto loginDesigner(String email, String password);
}

