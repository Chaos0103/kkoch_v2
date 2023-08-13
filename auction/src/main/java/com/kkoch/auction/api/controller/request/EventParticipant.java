package com.kkoch.auction.api.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EventParticipant {

    @NotNull
    private String memberKey;
    @NotNull
    private String auctionArticleId;
    @NotNull
    private Integer price;

    public EventParticipant(String memberKey, Long auctionArticleId, Integer price) {
        this.memberKey = memberKey;
        this.auctionArticleId = String.valueOf(auctionArticleId);
        this.price = price;
    }
}
