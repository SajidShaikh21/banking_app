package net.javaguide.banking.impl;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.mapper.Accountmapper;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= Accountmapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return Accountmapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        return Accountmapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id,  double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
         double total=account.getBalance()+amount;
         account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return Accountmapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient Amount");
        }
        double total=account.getBalance()-amount;
        account.setBalance(total);
  Account savedAccount=  accountRepository.save(account);

        return Accountmapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return (List<AccountDto>) accounts.stream().map((account) -> Accountmapper.mapToAccountDto(account)).collect(Collectors.toUnmodifiableList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);
    }

}
