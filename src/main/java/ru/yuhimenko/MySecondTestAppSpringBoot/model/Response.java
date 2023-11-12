package ru.yuhimenko.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;
    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;
    /**
     * Время создания сообщения
     */
    private String systemTime;
    /**
     * Код результата выполнения операции
     */
    private Codes code;
    /**
     * Годовая премия
     */
    private Double annualBonus;
    /**
     * Код ошибки
     */
    private ErrorCodes errorCode;
    /**
     * Сообщение об ошибке
     */
    private ErrorMessages errorMessage;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", code=" + code +
                ", errorCode=" + errorCode +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
