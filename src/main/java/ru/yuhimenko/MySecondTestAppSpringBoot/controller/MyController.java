package ru.yuhimenko.MySecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.yuhimenko.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.yuhimenko.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.*;
import ru.yuhimenko.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.yuhimenko.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.yuhimenko.MySecondTestAppSpringBoot.service.ValidationService;
import ru.yuhimenko.MySecondTestAppSpringBoot.util.DateTimeUtil;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final List<ModifyRequestService> modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        List<ModifyRequestService> modifyRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response: {}", response);

        try {
            if (Objects.equals(request.getUid(), "123")) {
                throw new UnsupportedCodeException("Uid = 123 unsupported");
            }
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponseService.modify(response);
        log.info("response: {}", response);

        modifyRequestService.forEach(service -> service.modify(request));
        timeDifferenceToService2(request);

        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }

    private void timeDifferenceToService2(Request request) {
        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:8087/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}