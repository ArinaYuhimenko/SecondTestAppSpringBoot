package ru.yuhimenko.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            throw new UnsupportedOperationException("Расчет премий поддерживается только для менеджеров.");
        }
        int daysInYear = Year.now().isLeap() ? 365 : 365;
        double annualBonus = salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
        double quarterlyBonus = annualBonus / 4;
        return quarterlyBonus;
    }
}
