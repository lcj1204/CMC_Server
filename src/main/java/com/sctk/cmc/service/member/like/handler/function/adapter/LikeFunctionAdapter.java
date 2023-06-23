package com.sctk.cmc.service.member.like.handler.function.adapter;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.controller.member.dto.LikeResponse;
import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.service.member.like.handler.function.LikeDesignerFunction;
import com.sctk.cmc.service.member.like.handler.function.LikeFunction;
import com.sctk.cmc.service.member.like.handler.function.LikeProductFunction;
import com.sctk.cmc.service.member.like.handler.function.find.adapter.LikeObjectFindAdapter;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LikeFunctionAdapter {
    private final LikeObjectFindAdapter likeObjectFindAdapter;
    private final List<LikeFunction> likeFunctions;

    public LikeFunctionAdapter(LikeDesignerFunction likeDesignerFunction, LikeProductFunction likeProductFunction, LikeObjectFindAdapter likeObjectFindAdapter) {
        this.likeObjectFindAdapter = likeObjectFindAdapter;
        likeFunctions = List.of(
                likeDesignerFunction, likeProductFunction
        );
    }

    public LikeResponse handle(Long memberId, Long targetId, Class<? extends LikedEntity> targetClass) {
        LikeFunction supportFunction = likeFunctions.stream()
                .filter(likeFunction -> likeFunction.support(targetClass))
                .findAny()
                .orElseThrow(() -> new CMCException(ResponseStatus.NOT_SUPPORTED_LIKING));

        LikeObject likeObject = likeObjectFindAdapter.getLikeObject(memberId, targetId, targetClass);

        return new LikeResponse(supportFunction.apply(memberId, targetId, likeObject));
    }
}
