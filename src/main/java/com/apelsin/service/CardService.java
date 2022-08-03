package com.apelsin.service;


import com.apelsin.dto.request.CardRequestDTO;
import com.apelsin.dto.response.CardResponseDTO;
import com.apelsin.entity.CardEntity;
import com.apelsin.enums.CardStatus;
import com.apelsin.exception.AppBadRequestException;
import com.apelsin.exception.InsufficientFundsException;
import com.apelsin.exception.ItemNotFoundException;
import com.apelsin.repository.CardRepository;
import com.apelsin.service.integration.UzcardCardService;
import com.apelsin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UzcardCardService uzcardCardService;

    @Autowired
    private SmsService smsService;
    private final int min = 1000;
    private final int max = 9999;


    public CardResponseDTO create(CardRequestDTO requestDTO, String profileId) {

        CardResponseDTO cardResponseDTO = uzcardCardService.getCardByNumber(requestDTO.getNumber());

        if (!cardResponseDTO.getStatus().equals(CardStatus.ACTIVE)) {

            throw new AppBadRequestException("card not active");
        }

        if (DateUtil.checkExpiredDate(requestDTO.getExpDate(), LocalDate.parse(cardResponseDTO.getExpDate()))) {
            throw new AppBadRequestException("expired date wrong");
        }


        CardEntity entity = new CardEntity();
        entity.setName(requestDTO.getName());
        entity.setNumber(cardResponseDTO.getNumber());
        entity.setExpiryDate(LocalDate.parse(cardResponseDTO.getExpDate()));
        entity.setProfileId(profileId);
        entity.setStatus(CardStatus.NOT_VERIFIED);
        entity.setPhone(cardResponseDTO.getPhone());

        cardRepository.save(entity);
        smsService.sendSms(cardResponseDTO.getPhone());
        return toDTO(entity);
    }

    public CardResponseDTO getById(String id) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, CardStatus.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return toDTO(entity);
    }

    public CardEntity get(String id) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, CardStatus.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return entity;
    }

    public CardEntity get(String id, Long amount) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, CardStatus.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        if (entity.getBalance() < amount) {
            throw new InsufficientFundsException("Balance not Found");
        }
        return entity;
    }

    public CardResponseDTO getByCardNumber(String id) {
        CardEntity entity = cardRepository.findByNumber(id).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return toDTO(entity);
    }


    public List<CardResponseDTO> getByPhoneId(String phone) {
        List<CardResponseDTO> dtoList = new LinkedList<>();
        cardRepository.findByPhoneAndStatus(phone, CardStatus.ACTIVE).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public List<CardResponseDTO> getByProfile(String cid) {
        List<CardResponseDTO> dtoList = new LinkedList<>();
        cardRepository.findByProfileIdAndStatus(cid, CardStatus.ACTIVE).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public Long getBalance(String number) {
        return cardRepository.getBalance(number).orElseThrow(() -> {
            log.warn("Card number not found");
            throw new ItemNotFoundException("Card number not found");
        });
    }

    public Boolean paymentMinus(Long amount, String cid) {
        int n = cardRepository.paymentMinus(amount, cid);
        return n > 0;
    }

    public Boolean paymentPlus(Long amount, String cid) {
        int n = cardRepository.paymentPlus(amount, cid);
        return n > 0;
    }

    public Boolean assignPhone(String phone, String cid) {
        int n = cardRepository.assignPhone(phone, cid);
        return n > 0;
    }



    public Boolean chengStatus(CardStatus status, String id) {
        int n = cardRepository.chengStatus(status, id);
        return n > 0;
    }

    private String getCardNumber() {
        int a = (int) (Math.random() * (max - min + 1) + min);
        int b = (int) (Math.random() * (max - min + 1) + min);
        int c = (int) (Math.random() * (max - min + 1) + min);
        String cardNumber = "8600-" + a + "-" + b + "-" + c;

        Optional<CardEntity> optional = cardRepository.findByNumber(cardNumber);
        if (optional.isPresent()) {
            getCardNumber();
        }
        return cardNumber;
    }

    private CardResponseDTO toDTO(CardEntity entity) {
        CardResponseDTO responseDTO = new CardResponseDTO();
//        responseDTO.sei(entity.getId());
        responseDTO.setNumber(entity.getNumber());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setExpDate(String.valueOf(entity.getExpiryDate()));
        return responseDTO;
    }
}
