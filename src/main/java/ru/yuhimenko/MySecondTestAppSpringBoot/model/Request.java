package ru.yuhimenko.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank(message = "Уникальный идентификатор сообщение")
    @Size(max = 32, message = "Уникальный идентификатор сообщения должен быть не более 32 символов")
    private String uid;

    @NotBlank(message = "Уникальный идентификатор операции обязателен")
    @Size(max = 32, message = "Уникальный идентификатор операции должен быть не более 32 символов")
    private String operationUid;

    private String systemName;
    @NotBlank(message = "Время создания сообщения обязательно")
    private String systemTime;

    private String source;
    @Min(value = 1, message = "Уникальный идентификатор коммуникации должен быть не менее 1")
    @Max(value = 100000, message = "Уникальный идентификатор коммуникации должен быть не более 100000")
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;
}
