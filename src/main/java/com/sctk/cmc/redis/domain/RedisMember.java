package com.sctk.cmc.redis.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("MEMBER")
public class RedisMember {
    @Id
    private String email;
    private String refreshToken;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiredTimeMs;

    @Builder
    public RedisMember(String email, String refreshToken, Long expiredTimeMs) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiredTimeMs = expiredTimeMs;
    }
}
