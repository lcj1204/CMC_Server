package com.sctk.cmc.service.member.like.handler.function.find;

import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.likeobject.LikeObject;

public interface LikeObjectFindFunction {
    boolean support(Class<? extends LikedEntity> targetClass);

    LikeObject apply(Long memberId, Long targetId);
}
