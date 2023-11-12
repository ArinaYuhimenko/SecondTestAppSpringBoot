package ru.yuhimenko.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.yuhimenko.MySecondTestAppSpringBoot.model.Positions;

import java.util.Calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {

        //given
        Positions positions = Positions.MANAGER;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //when
        double result = new AnnualBonusServiceImpl().calculate(positions, salary, bonus, workDays);

        // then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }
    @Test
    void calculateNotForManager() {
        Positions position = Positions.DEV;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();
        double result = service.calculate(position, salary, bonus, workDays);

        double expected = 0.0;
        assertThat(result).isEqualTo(expected);
    }
}