package com.sctk.cmc.service.member.like.handler.function;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.likeobject.LikeDesigner;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeDesignerFunction implements LikeFunction {
    private final MemberService memberService;
    private final DesignerService designerService;

    @Override
    public boolean support(Class<? extends LikedEntity> targetClass) {
        return targetClass.equals(Designer.class);
    }

    @Transactional
    @Override
    public int apply(Long memberId, Long designerId, LikeObject likeDesigner) {
        LikeDesigner like = (LikeDesigner) likeDesigner;

        if (like != null) {
            return memberService.cancelLike(like);
        }

        Member member = memberService.retrieveById(memberId);
        Designer designer = designerService.retrieveById(designerId);

        // 생성자를 통해 연관관계 매핑
        new LikeDesigner(member, designer);

        return designer.getLikeCount();
    }
}
