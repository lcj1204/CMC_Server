package com.sctk.cmc.service.member.like.handler.function.find.adapter;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.service.member.like.handler.function.find.LikeDesignerFindFunction;
import com.sctk.cmc.service.member.like.handler.function.find.LikeObjectFindFunction;
import com.sctk.cmc.service.member.like.handler.function.find.LikeProductFindFunction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeObjectFindAdapter {
    private final List<LikeObjectFindFunction> likeObjectFindFunctions;

    public LikeObjectFindAdapter(LikeDesignerFindFunction likeDesignerFindFunction, LikeProductFindFunction likeProductFindFunction) {
        likeObjectFindFunctions = List.of(
                likeDesignerFindFunction,
                likeProductFindFunction
        );
    }

    public LikeObject getLikeObject(Long memberId, Long targetId, Class<? extends LikedEntity> targetClass) {
        LikeObjectFindFunction likeObjectFindFunction = likeObjectFindFunctions.stream()
                .filter(provider -> provider.support(targetClass))
                .findAny()
                .orElseThrow(() -> new CMCException(ResponseStatus.NOT_SUPPORTED_LIKING));

        return likeObjectFindFunction.apply(memberId, targetId);
    }
}
