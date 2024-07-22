package com.kkoch.admin.domain.trade.repository.response;

import com.kkoch.admin.domain.Grade;

import java.time.LocalDateTime;

public class TradeResponse {

    private String itemName;
    private String varietyName;
    private Grade grade;
    private int plantCount;
    private int bidPrice;
    private LocalDateTime bidDateTime;
    private String region;
}
