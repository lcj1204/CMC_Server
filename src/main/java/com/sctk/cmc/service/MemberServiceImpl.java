package com.sctk.cmc.service;

import com.sctk.cmc.common.dto.member.MemberJoinParam;
import com.sctk.cmc.domain.BodyInfo;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.LikeDesigner;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.service.abstractions.DesignerService;
import com.sctk.cmc.service.dto.member.*;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.repository.MemberRepository;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.web.dto.ProfileImgPostResponse;
import com.sctk.cmc.web.dto.member.LikeDesignerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final DesignerService designerService;
    private final AmazonS3Service amazonS3Service;

    @Transactional
    @Override
    public Long join(MemberJoinParam param) {
        if (existsByEmail(param.getEmail())) {
            throw new CMCException(AUTHENTICATION_DUPLICATE_EMAIL);
        }

        return memberRepository.save(Member.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .build());
    }

    private Member retrieveById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CMCException(MEMBERS_ILLEGAL_ID));
    }

    @Override
    public MemberDetail retrieveDetailById(Long memberId) {
        Member member = retrieveById(memberId);

        return new MemberDetail(
                member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImgUrl(),
                member.getIntroduce()
        );
    }

    @Override
    public MemberInfo retrieveInfoById(Long memberId) {
        Member member = retrieveById(memberId);
        BodyInfo info = member.getBodyInfo();

        return new MemberInfo(
                member.getName(),
                member.getProfileImgUrl(),
                new BodyInfoView(info.getSizes())
        );
    }

    @Override
    public Member retrieveByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

    @Transactional
    @Override
    public void registerBodyInfo(Long memberId, BodyInfoParams params) {
        Member member = retrieveById(memberId);

        new BodyInfo(member, params.getSizes());
    }

    @Transactional
    @Override
    public void modifyBodyInfo(Long memberId, BodyInfoModifyParams params) {
        Member member = retrieveById(memberId);

        new BodyInfo(member, params.getSizes());
    }

    @Transactional
    @Override
    public LikeDesignerResponse like(Long memberId, Long designerId) {
        LikeDesigner like = retrieveLike(memberId, designerId);

        if (like != null) {
            return cancelLike(like);
        }

        Member member = retrieveById(memberId);
        Designer designer = designerService.retrieveById(designerId);

        // 생성자를 통해 연관관계 매핑
        new LikeDesigner(member, designer);

        return new LikeDesignerResponse(designer.getLikeCount());
    }

    @Override
    public LikeDesignerResponse cancelLike(LikeDesigner like) {
        Designer designer = like.getDesigner();
        like.remove();

        return new LikeDesignerResponse(designer.getLikeCount());
    }

    @Override
    public ProfileImgPostResponse registerProfileImg(Long memberId, MultipartFile profileImg) {
        Member member = retrieveById(memberId);

        String uploadedUrl = amazonS3Service.upload(profileImg, memberId, member.getRole());

        member.setProfileImgUrl(uploadedUrl);
        return new ProfileImgPostResponse(uploadedUrl);
    }

    public LikeDesigner retrieveLike(Long memberId, Long designerId) {
        Member member = retrieveById(memberId);

        return member.getDesignerLikes()
                .stream()
                .filter(likeDesigner -> likeDesigner.getDesigner().getId() == designerId)
                .findAny()
                .orElse(null);
    }
}
