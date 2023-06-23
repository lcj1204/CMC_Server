package com.sctk.cmc.service.member.like.handler.function;

import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.likeobject.LikeObject;

public interface LikeFunction {
    boolean support(Class<? extends LikedEntity> targetClass);

    int apply(Long memberId, Long targetId, LikeObject likeObject);
}
