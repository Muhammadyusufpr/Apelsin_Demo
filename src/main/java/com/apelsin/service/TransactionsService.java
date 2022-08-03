package com.apelsin.service;

import com.apelsin.dto.request.TransactionsRequestDTO;
import com.apelsin.dto.response.CardResponseDTO;
import com.apelsin.dto.response.ProfileResponseDTO;
import com.apelsin.dto.response.TransactionsResponseDTO;
import com.apelsin.entity.TransactionsEntity;
import com.apelsin.enums.TranStatus;
import com.apelsin.mapper.TransactionsMapper;
import com.apelsin.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final CardService cardService;


    @Transactional
    public TransactionsResponseDTO create(TransactionsRequestDTO requestDTO) {

        cardService.get(requestDTO.getFromCardId(), requestDTO.getAmount());

        cardService.get(requestDTO.getToCardId());

        cardService.paymentMinus(requestDTO.getAmount(), requestDTO.getFromCardId());
        cardService.paymentPlus(requestDTO.getAmount(), requestDTO.getToCardId());

        TransactionsEntity entity = new TransactionsEntity();
        entity.setAmount(requestDTO.getAmount());
        entity.setFromCardId(requestDTO.getFromCardId());
        entity.setToCardId(requestDTO.getToCardId());
        entity.setStatus(TranStatus.ACTIVE);
        transactionsRepository.save(entity);
        return toDTO(entity);
    }

    public PageImpl<TransactionsResponseDTO> getByCardIdAndPagination(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);


        Page<TransactionsMapper> entityPage = transactionsRepository.getByCardId(pageable, cid, TranStatus.ACTIVE);

        List<TransactionsMapper> entityList = entityPage.getContent();
        List<TransactionsResponseDTO> playListDTO = new LinkedList<>();
        entityList.forEach(entity -> {
            playListDTO.add(toDTOMapper(entity));
        });

        return new PageImpl<>(playListDTO, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionsResponseDTO> getByPhone(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);


        Page<TransactionsMapper> entityPage = transactionsRepository.getByPhone(pageable, cid, TranStatus.ACTIVE);

        List<TransactionsMapper> entityList = entityPage.getContent();
        List<TransactionsResponseDTO> playListDTO = new LinkedList<>();
        entityList.forEach(entity -> {
            playListDTO.add(toDTOMapper(entity));
        });

        return new PageImpl<>(playListDTO, pageable, entityPage.getTotalElements());
    }



    public PageImpl<TransactionsResponseDTO> getByClientId(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);


        Page<TransactionsMapper> entityPage = transactionsRepository.getByClientId(pageable, cid, TranStatus.ACTIVE);

        List<TransactionsMapper> entityList = entityPage.getContent();
        List<TransactionsResponseDTO> playListDTO = new LinkedList<>();
        entityList.forEach(entity -> {
            playListDTO.add(toDTOMapper(entity));
        });

        return new PageImpl<>(playListDTO, pageable, entityPage.getTotalElements());
    }



    private TransactionsResponseDTO toDTOMapper(TransactionsMapper entity) {
        TransactionsResponseDTO responseDTO = new TransactionsResponseDTO();
        responseDTO.setId(entity.getT_id());

        CardResponseDTO fromCard = new CardResponseDTO();
//        fromCard.setId(entity.getFcr_id());
        String fromCardNumber = getCardNumberSkr(entity.getFcr_number());
        fromCard.setNumber(fromCardNumber);

        ProfileResponseDTO fromProfile = new ProfileResponseDTO();
        fromProfile.setId(entity.getFcl_id());
        fromProfile.setName(entity.getFcl_name());
        fromProfile.setSurname(entity.getFcl_surname());

        fromCard.setProfile(fromProfile);

        CardResponseDTO toCard = new CardResponseDTO();
//        toCard.setId(entity.getTcr_id());

        ProfileResponseDTO toProfile = new ProfileResponseDTO();
        toProfile.setId(entity.getTcl_id());
        toProfile.setName(entity.getTcl_name());
        toProfile.setSurname(entity.getTcl_surname());

        toCard.setProfile(toProfile);
        toCard.setNumber(getCardNumberSkr(entity.getTcr_number()));

        responseDTO.setToCard(toCard);
        responseDTO.setFromCard(fromCard);
        responseDTO.setAmount(entity.getT_amount());
        responseDTO.setStatus(entity.getT_status());
        responseDTO.setCreatedDate(entity.getT_createdDate());
        return responseDTO;
    }

    private String getCardNumberSkr(String entity) {
        return entity.substring(0, 4) + "-****-****-" + entity.substring(entity.length() - 4);
    }

    private TransactionsResponseDTO toDTO(TransactionsEntity entity) {
        TransactionsResponseDTO responseDTO = new TransactionsResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setFromCardId(entity.getFromCardId());
        responseDTO.setToCardId(entity.getToCardId());
        responseDTO.setAmount(entity.getAmount());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        return responseDTO;
    }
}
