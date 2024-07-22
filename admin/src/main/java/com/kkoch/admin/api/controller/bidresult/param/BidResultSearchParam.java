package com.kkoch.admin.api.controller.bidresult.param;

import com.kkoch.admin.domain.bidresult.repository.dto.BidResultSearchCond;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BidResultSearchParam {

    @Positive(message = "페이지 번호을 올바르게 입력해주세요.")
    private int page = 1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "화훼부류를 입력해주세요.")
    private String plantCategory;

    private String itemName;

    private String varietyName;

    private String region;

    @Builder
    private BidResultSearchParam(int page, LocalDate startDate, LocalDate endDate, String plantCategory, String itemName, String varietyName, String region) {
        this.page = page;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.region = region;
    }

    public BidResultSearchCond toSearchCond() {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay().minusSeconds(1);
        return BidResultSearchCond.of(startDateTime, endDateTime, PlantCategory.valueOf(plantCategory), itemName, varietyName, region);
    }

    public Pageable toPageable() {
        return PageRequest.of(page - 1, 15);
    }
}
