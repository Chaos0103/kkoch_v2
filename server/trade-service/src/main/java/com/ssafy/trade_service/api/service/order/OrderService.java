package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.api.service.order.response.OrderCreateResponse;
import com.ssafy.trade_service.domain.bidinfo.Bid;
import com.ssafy.trade_service.domain.bidinfo.repository.BidRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.ssafy.trade_service.domain.bidinfo.repository.BidRepository.NO_SUCH_BID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BidRepository bidRepository;

    public OrderCreateResponse createOrder(Long memberId) {
        Bid bid = findBidByMemberId(memberId);

        Order order = bid.toOrder();
        Order savedOrder = orderRepository.save(order);

        bidRepository.deleteById(memberId);

        return OrderCreateResponse.of(savedOrder);
    }

    private Bid findBidByMemberId(Long memberId) {
        return bidRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_BID));
    }
}
