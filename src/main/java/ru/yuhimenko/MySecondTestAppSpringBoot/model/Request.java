package ru.yuhimenko.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank(message = "Уникальный идентификатор сообщение")
    @Size(max = 32, message = "Уникальный идентификатор сообщения должен быть не более 32 символов")
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    @NotBlank(message = "Уникальный идентификатор операции обязателен")
    @Size(max = 32, message = "Уникальный идентификатор операции должен быть не более 32 символов")
    private String operationUid;
    /**
     * Имя системы отправителя
     */
    @JsonDeserialize(using = SystemNameDeserializer.class)
    private Systems systemName;

    /**
     * Время создания сообщения
     */
    @NotBlank(message = "Время создания сообщения обязательно")
    private String systemTime;

    /**
     * Наименование ресурса
     */
    private String source;
    /**
     * Должность
     */
    private Positions position;
    /**
     * Заработная плата
     */
    private Double salary;
    /**
     * Коэффициент премии
     */
    private Double bonus;
    /**
     * Рабочие дни
     */
    private Integer workDays;
    /**
     * Уникальный идентификатор коммуникации
     */
    @Min(value = 1, message = "Уникальный идентификатор коммуникации должен быть не менее 1")
    @Max(value = 100000, message = "Уникальный идентификатор коммуникации должен быть не более 100000")
    private int communicationId;
    /**
     * Уникальный идентификатор шаблона
     */
    private int templateId;
    /**
     * Код продукта
     */
    private int productCode;
    /**
     * СМС-код
     */
    private int smsCode;
    @Override
    public String toString() {
        return "{" +
                "uid=' " + uid + '\'' +
                ", operationUid=' " + operationUid + '\'' +
                ", systemName=' " + systemName + '\'' +
                ", systemTime=' " + systemTime + '\'' +
                ", source=' " + source + '\'' +
                ", position=" + position +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", workDays=" + workDays +
                ", communicationId=' " + communicationId + '\'' +
                ", templateId=' " + templateId + '\'' +
                ", productCode=' " + productCode + '\'' +
                ", smsCode=' " + smsCode + '\'' +
                '}';
    }
}
