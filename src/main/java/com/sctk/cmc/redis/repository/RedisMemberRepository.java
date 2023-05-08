package com.sctk.cmc.redis.repository;

import com.sctk.cmc.redis.domain.RedisMember;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberRepository extends CrudRepository<RedisMember, String> {
}
