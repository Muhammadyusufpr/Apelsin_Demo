package com.apelsin.controller;


import com.apelsin.dto.request.CardAssignRequestDTO;
import com.apelsin.dto.request.CardRequestDTO;
import com.apelsin.enums.CardStatus;
import com.apelsin.service.CardService;
import com.apelsin.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Slf4j
@Api(tags = "Card")
@RestController
@RequestMapping("/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @ApiOperation(value = "Create ", notes = "Method Create Card")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid CardRequestDTO requestDTO,
                                    HttpServletRequest request) {
        log.info("Create: {}", requestDTO);
        String profileId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(cardService.create(requestDTO, profileId));
    }



    @ApiOperation(value = "Get by id", notes = "Method get By id")
//    @PreAuthorize("hasRole('BANK')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        log.info("Get by id: {}",id);
        return ResponseEntity.ok(cardService.getById(id));
    }

    @ApiOperation(value = "Get by Card number", notes = "Method get By Card number")
    @GetMapping("/getByCardNumber/{id}")
    public ResponseEntity<?> getByCardNumber(@PathVariable("id") String id) {
        log.info("Get by card number: {}",id);
        return ResponseEntity.ok(cardService.getByCardNumber(id));
    }

    @ApiOperation(value = "Get by Profile Id", notes = "Method get By Profile Id")
    @GetMapping("/getByClientId/{id}")
    public ResponseEntity<?> getByProfile(@PathVariable("id") String id) {
        log.info("Get by profile id: {}",id);
        return ResponseEntity.ok(cardService.getByProfile(id));
    }

    @ApiOperation(value = "Get by Phone Id", notes = "Method get By Phone Id")
    @GetMapping("/getByPhoneId/{id}")
    public ResponseEntity<?> getByPhoneId(@PathVariable("id") String id) {
        log.info("Get by phone: {}",id);
        return ResponseEntity.ok(cardService.getByPhoneId(id));
    }

    @ApiOperation(value = "Get by Card number Balance", notes = "Method get By Card number Balance")
    @GetMapping("/getBalance/{number}")
    public ResponseEntity<?> getBalance(@PathVariable("number") String number) {
        log.info("Get balance by card number: {}",number);
        return ResponseEntity.ok(cardService.getBalance(number));
    }


    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id Active")
//    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/chengStatus/{id}/Active")
    public ResponseEntity<?> chengStatusActive(@PathVariable("id") String id) {
        log.info("Chang status active: {}",id);
        return ResponseEntity.ok(cardService.chengStatus(CardStatus.ACTIVE, id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id Active")
//    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/assignPhone/{id}")
    public ResponseEntity<?> assignPhone(@PathVariable("id") String id,
                                         @RequestBody @Valid CardAssignRequestDTO requestDTO) {
        log.info("Assign phone: {},{}",requestDTO,id);
        return ResponseEntity.ok(cardService.assignPhone(requestDTO.getPhone(), id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id block")
//    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/chengStatus/{id}/block")
    public ResponseEntity<?> chengStatusBlock(@PathVariable("id") String id) {
        log.info("Chang status block: {}",id);
        return ResponseEntity.ok(cardService.chengStatus(CardStatus.BLOCK, id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id Not active")
//    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/chengStatus/{id}/notactive")
    public ResponseEntity<?> chengStatusNotActive(@PathVariable("id") String id) {
        log.info("Chang status not active: {}",id);
        return ResponseEntity.ok(cardService.chengStatus(CardStatus.NOT_ACTIVE, id));
    }
}
