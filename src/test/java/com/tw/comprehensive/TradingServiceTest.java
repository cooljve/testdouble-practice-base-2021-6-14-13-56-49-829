package com.tw.comprehensive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradingServiceTest {

    @Test
    void should_invoke_logNewTrade_when_invoke_createTrade() {
        AuditService auditService = mock(AuditService.class);
        TradingService tradingService = new TradingService(new TradeRepository(), auditService);

        tradingService.createTrade(new Trade("", ""));

        verify(auditService).logNewTrade(any());
    }

    @Test
    void should_findTrade_return_same_value_as_findById() {
        AuditService auditService = mock(AuditService.class);
        TradeRepository tradeRepository = mock(TradeRepository.class);
        TradingService tradingService = new TradingService(tradeRepository, auditService);
        Trade expectValue = new Trade("hello", "world");
        when(tradeRepository.findById(anyLong())).thenReturn(expectValue);

        Trade trade = tradingService.findTrade(1L);

        assertEquals(expectValue, trade);
    }

    @Test
    void should_invoke_createTrade_of_TradeRepository_and_return_same_trade_id_when_invoke_TradeService_createTrade() {
        AuditService auditService = mock(AuditService.class);
        TradeRepository tradeRepository = mock(TradeRepository.class);
        TradingService tradingService = new TradingService(tradeRepository, auditService);
        Trade trade = new Trade("hello", "world");
        long expectId = 1L;
        when(tradeRepository.createTrade(trade)).thenReturn(expectId);

        Long resultId = tradingService.createTrade(trade);

        verify(tradeRepository).createTrade(any());
        assertEquals(expectId, resultId);
    }
}
