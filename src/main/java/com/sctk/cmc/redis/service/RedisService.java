package com.sctk.cmc.redis.service;

import com.sctk.cmc.redis.domain.RedisDesigner;
import com.sctk.cmc.redis.domain.RedisMember;
import com.sctk.cmc.redis.repository.RedisDesignerRepository;
import com.sctk.cmc.redis.repository.RedisMemberRepository;
import com.sctk.cmc.common.exception.CMCException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sctk.cmc.common.exception.ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RedisService {
    private final RedisMemberRepository redisMemberRepository;
    private final RedisDesignerRepository redisDesignerRepository;
    @Value("${jwt.token.refresh-token.expiredTimeMs}")
    private Long refreshTokenExpiredTimeMs;

    @Transactional
    public void saveMember(String email, String refreshToken){
        RedisMember redisMember = RedisMember.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiredTimeMs(refreshTokenExpiredTimeMs)
                .build();

        redisMemberRepository.save(redisMember);
    }

    @Transactional
    public void saveDesigner(String email, String refreshToken ){
        RedisDesigner redisDesigner = RedisDesigner.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiredTimeMs(refreshTokenExpiredTimeMs)
                .build();

        redisDesignerRepository.save(redisDesigner);
    }

    public RedisMember getMember(String email){
        return redisMemberRepository.findById(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    public RedisDesigner getDesigner(String email){
        return redisDesignerRepository.findById(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    @Transactional
    public void deleteMember(String email) {
        redisMemberRepository.deleteById(email);
    }

    @Transactional
    public void deleteDesigner(String email) {
        redisDesignerRepository.deleteById(email);
    }
}
