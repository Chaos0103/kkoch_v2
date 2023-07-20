package com.kkoch.user.domain.wishlist;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Wishlist extends TimeBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long auctionArticleId;

    @Builder
    public Wishlist(Long id, Member member, Long auctionArticleId) {
        this.id = id;
        this.member = member;
        this.auctionArticleId = auctionArticleId;
    }
}
