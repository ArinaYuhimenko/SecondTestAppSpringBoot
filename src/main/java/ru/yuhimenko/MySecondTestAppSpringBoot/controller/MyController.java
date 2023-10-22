package ru.yuhimenko.MySecondTestAppSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yuhimenko.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.yuhimenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.Request;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.Response;
import ru.yuhimenko.MySecondTestAppSpringBoot.service.ValidationService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {
    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        try {
            if (request.getUid().equals("123")) {
                throw new UnsupportedCodeException("Unsupported uid");
            }
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("Неподдерживаемый код");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}