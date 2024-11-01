package com.auca.library.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineService {

    public double calculateFine(LocalDate borrowDate, LocalDate returnDate, String membershipType) {
        long overdueDays = ChronoUnit.DAYS.between(borrowDate, returnDate) - 14; // Assuming 14-day loan period
        if (overdueDays <= 0) return 0;

        double dailyFee = membershipType.equals("Gold") ? 50 : membershipType.equals("Silver") ? 30 : 10;
        return overdueDays * dailyFee;
    }
}

