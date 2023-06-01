package com.sctk.cmc.util.redis.repository;

import com.sctk.cmc.util.redis.domain.RedisMember;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberRepository extends CrudRepository<RedisMember, String> {
}
