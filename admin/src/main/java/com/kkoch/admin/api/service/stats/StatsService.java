package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.domain.bidresult.repository.BidResultQueryRepository;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResults;
import com.kkoch.admin.domain.stats.Stats;
import com.kkoch.admin.domain.stats.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository statsRepository;
    private final BidResultQueryRepository bidResultQueryRepository;

    public int createStats(LocalDate currentDate) {
        BidResults bidResults = bidResultQueryRepository.findAllByBidDateTimeBetween(currentDate.atStartOfDay(), currentDate.atStartOfDay().plusDays(1));

        List<Stats> stats = bidResults.getPriceMap(currentDate);

        return statsRepository.saveAll(stats)
            .size();
    }
}
