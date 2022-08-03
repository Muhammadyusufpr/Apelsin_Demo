package com.apelsin.service;

import com.apelsin.entity.ProfileEntity;
import com.apelsin.dto.RegistrationDto;
import com.apelsin.dto.SmsDTO;
import com.apelsin.dto.response.ProfileResponseDTO;
import com.apelsin.entity.SmsEntity;
import com.apelsin.enums.ProfileRole;
import com.apelsin.enums.ProfileStatus;
import com.apelsin.enums.SmsStatus;
import com.apelsin.exception.AppBadRequestException;
import com.apelsin.exception.ItemAlreadyExistsException;
import com.apelsin.exception.ItemNotFoundException;
import com.apelsin.repository.ProfileRepository;
import com.apelsin.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final ProfileService profileService;
    private final SmsRepository smsRepository;
    private final SmsService smsService;

    public void registration(RegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if (optional.isPresent()) {
            log.warn("Phone already exists : {}", dto);
            throw new ItemAlreadyExistsException("Phone already exists!");
        }

        ProfileEntity entity = toProfileEntity(dto);
        try {
            profileRepository.save(entity);
            smsService.sendSms(dto.getPhone());
        } catch (DataIntegrityViolationException e) {
            log.warn("Unique {}", dto);
            throw new AppBadRequestException("Unique Items!");
        }
    }

    public Boolean activation(SmsDTO dto) {
        ProfileEntity entity = profileService.getByPhone(dto.getPhone());
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneAndStatusOrderByCreatedDateDesc(dto.getPhone(), SmsStatus.NOT_USED);
        if (optional.isEmpty()) {
            return false;
        }

        SmsEntity smsEntity = optional.get();
        if (!smsEntity.getContent().equals(dto.getSms())) {
            smsRepository.updateSmsStatus(SmsStatus.INVALID, smsEntity.getId());
            throw new AppBadRequestException("Code wrong");
        }
        LocalDateTime extTime = smsEntity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(extTime)) {
            smsRepository.updateSmsStatus(SmsStatus.INVALID, smsEntity.getId());
            throw new AppBadRequestException("Time is up");
        }
        smsRepository.updateSmsStatus(SmsStatus.USED, smsEntity.getId());

        int n = profileRepository.activation(ProfileStatus.ACTIVE, dto.getPhone());
        return n > 0;
    }

    public ProfileEntity toProfileEntity(RegistrationDto dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public ProfileResponseDTO login(RegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(), dto.getPassword());
        if (!optional.isPresent()) {
            log.warn("phone or password wrong");
            throw new ItemNotFoundException("phone or password wrong");
        }


        ProfileEntity entity = optional.get();
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setPhone(entity.getPhone());
//        responseDTO.setJwt(JwtUtil.decode(entity.getId()));
        return responseDTO;
    }
}
