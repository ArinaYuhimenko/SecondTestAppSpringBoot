package ru.yuhimenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);

}
