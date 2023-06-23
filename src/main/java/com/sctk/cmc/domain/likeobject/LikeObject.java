package com.sctk.cmc.domain.likeobject;

import com.sctk.cmc.domain.LikedEntity;

public interface LikeObject {
    void remove();

    LikedEntity getObject();
}
