package com.sctk.cmc.service.member;

import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.repository.member.MemberRepository;
import com.sctk.cmc.controller.common.ProfileImgPostResponse;
import com.sctk.cmc.service.member.dto.*;
import com.sctk.cmc.service.member.like.handler.function.adapter.LikeFunctionAdapter;
import com.sctk.cmc.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;

    @Value("${cmc.member.profile.default-img-url}")
    private String MEMBER_DEFAULT_PROFILE_IMG_URL;

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
                .profileImgUrl(MEMBER_DEFAULT_PROFILE_IMG_URL)
                .build());
    }

    @Override
    public Member retrieveById(Long memberId) {
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

        BodyInfoView infoView = new BodyInfoView(SizesByPart.getEmpty());
        if (info != null) {
            infoView = new BodyInfoView(info.getSizesByPart());
        }

        return new MemberInfo(
                member.getName(),
                member.getProfileImgUrl(),
                infoView
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

    @Override
    public BodyInfoView retrieveBodyInfoById(Long memberId) {
        Member member = retrieveById(memberId);

        BodyInfo bodyInfo = member.getBodyInfo();
        if (bodyInfo == null) {
            throw new CMCException(ResponseStatus.MEMBERS_EMPTY_BODY_INFO);
        }

        return new BodyInfoView(bodyInfo.getSizesByPart());
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

    @Override
    public int cancelLike(LikeObject like) {
        LikedEntity likedEntity = like.getObject();
        like.remove();

        return likedEntity.getLikeCount();
    }

    @Transactional
    @Override
    public ProfileImgPostResponse registerProfileImg(Long memberId, MultipartFile profileImg) {
        Member member = retrieveById(memberId);

        if (!member.getProfileImgUrl().equals(MEMBER_DEFAULT_PROFILE_IMG_URL)) {
            amazonS3Service.delete(member.getProfileImgUrl());
        }

        String uploadedUrl = amazonS3Service.uploadProfileImg(profileImg, memberId, member.getRole());
        member.setProfileImgUrl(uploadedUrl);
        return new ProfileImgPostResponse(uploadedUrl);
    }

    @Override
    public void checkRequirements(Long memberId) {
        // body-info 작성 여부
        BodyInfoView infoView = retrieveBodyInfoById(memberId);

        if (infoView == null) {
            throw new CMCException(MEMBERS_EMPTY_BODY_INFO);
        }
        // 추후 추가 가능
    }
}
