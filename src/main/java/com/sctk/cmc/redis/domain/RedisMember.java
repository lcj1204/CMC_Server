package com.sctk.cmc.redis.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("MEMBER") // prefix에 입력될 keyspace
public class RedisMember {
    @Id // keyspace:@Id 형태로 키를 저장함.
    private String email;
    private String refreshToken;
    @TimeToLive(unit = TimeUnit.MILLISECONDS) // Redis 에 유지되는 시간
    private Long expiredTimeMs;

    @Builder
    public RedisMember(String email, String refreshToken, Long expiredTimeMs) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.expiredTimeMs = expiredTimeMs;
    }
}
