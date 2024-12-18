package com.example.OneBillion.Model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LotteryTicketGenerator {

    private static final String PREFIX = "LotteryTicket";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 15;

    public static String generateLotteryTicket() {
        Random random = new Random();
        StringBuilder ticketCode = new StringBuilder();

        // Generate a 15-character alphanumeric string
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            ticketCode.append(ALPHANUMERIC.charAt(index));
        }

        // Combine the prefix and generated code
        return PREFIX + ticketCode.toString();
    }

    public static void main(String[] args) {
        // Example: Generate and print 5 unique tickets
        for (int i = 0; i < 5; i++) {
            System.out.println(generateLotteryTicket());
        }
    }
}
