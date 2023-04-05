package com.sctk.cmc.service;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.HighCategory;
import com.sctk.cmc.domain.LowCategory;
import com.sctk.cmc.dto.designer.DesignerJoinParam;
import com.sctk.cmc.exception.CMCException;
import com.sctk.cmc.repository.DesignerRepository;
import com.sctk.cmc.service.abstractions.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sctk.cmc.exception.ResponseStatus.AUTHENTICATION_ILLEGAL_EMAIL;
import static com.sctk.cmc.exception.ResponseStatus.DESIGNERS_ILLEGAL_ID;

@RequiredArgsConstructor
@Service
public class DesignerServiceImpl implements DesignerService {
    private final PasswordEncoder passwordEncoder;
    private final DesignerRepository designerRepository;

    @Transactional
    @Override
    public Long join(DesignerJoinParam param) {
        return designerRepository.save(Designer.builder()
                .name(param.getName())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .build());
    }

    @Override
    public Designer retrieveById(Long designerId) {
        return designerRepository.findById(designerId)
                .orElseThrow(() -> new CMCException(DESIGNERS_ILLEGAL_ID));
    }

    @Override
    public Designer retrieveByEmail(String email) {
        return designerRepository.findByEmail(email)
                .orElseThrow(() -> new CMCException(AUTHENTICATION_ILLEGAL_EMAIL));
    }

    @Override
    public boolean existsByEmail(String email) {
        return designerRepository.findByEmail(email)
                .isPresent();
    }

    @Override
    public List<Designer> retrieveAllByName(String name) {
        return designerRepository.findAllByName(name);
    }

    @Transactional
    @Override
    public int registerHighCategories(Long designerId, List<HighCategory> highCategories) {
        Designer designer = retrieveById(designerId);
        designer.setHighCategories(highCategories);
        return highCategories.size();
    }

    @Transactional
    @Override
    public int registerLowCategories(Long designerId, List<LowCategory> lowCategories) {
        Designer designer = retrieveById(designerId);
        designer.setLowCategories(lowCategories);
        return lowCategories.size();
    }
}
