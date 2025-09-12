package com.casino.service;

import com.casino.model.Bet;
import com.casino.model.BetType;
import com.casino.model.Round;
import com.casino.model.User;
import com.casino.repository.BetRepository;
import com.casino.repository.RoundRepository;
import com.casino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BetService {
    
    @Autowired
    private BetRepository betRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoundRepository roundRepository;
    
    @Autowired
    private BetHistoryService betHistoryService;
    
    public Bet placeNumberBet(User user, int number, double amount) {
        validateUserBalance(user, amount);
        
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setAmount(amount);
        bet.setType(BetType.NUMBER);
        bet.setNumber(number);
        
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        
        Bet savedBet = betRepository.save(bet);
        betHistoryService.addBet(savedBet);
        return savedBet;
    }
    
    public Bet placeColorBet(User user, String color, double amount) {
        validateUserBalance(user, amount);
        
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setAmount(amount);
        bet.setType(BetType.COLOR);
        bet.setColor(color.toUpperCase());
        
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        
        Bet savedBet = betRepository.save(bet);
        betHistoryService.addBet(savedBet);
        return savedBet;
    }
    
    public Bet placeTercioBet(User user, int tercio, double amount) {
        validateUserBalance(user, amount);
        
        Bet bet = new Bet();
        bet.setUser(user);
        bet.setAmount(amount);
        bet.setType(BetType.TERCIO);
        bet.setTercio(tercio);
        
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        
        Bet savedBet = betRepository.save(bet);
        betHistoryService.addBet(savedBet);
        return savedBet;
    }
    
    public void resolveBets(Round round) {
        for (Bet bet : round.getBets()) {
            if (bet.isWinner(round.getWinningNumber())) {
                double payout = bet.calculatePayout(round.getWinningNumber());
                User user = bet.getUser();
                user.setBalance(user.getBalance() + payout);
                userRepository.save(user);
                
                bet.setWon(true);
                bet.setPayout(payout);
            }
            bet.setResolved(true);
            betRepository.save(bet);
        }
        round.setCompleted(true);
        roundRepository.save(round);
    }
    
    private void validateUserBalance(User user, double amount) {
        if (amount > user.getBalance()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }
}