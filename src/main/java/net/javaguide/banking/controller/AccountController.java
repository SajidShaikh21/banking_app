package net.javaguide.banking.controller;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // add Account REST API

    @PostMapping()
    public ResponseEntity<AccountDto>addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);

    }

    // get Account Rest API
      @GetMapping("/{id}")
    public ResponseEntity<AccountDto>getAccountById( @PathVariable Long id){
        AccountDto accountDto=accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //deposit rest api
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto>deposit( @PathVariable Long id, @RequestBody Map<String,Double> request){

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, request.get("amount"));
         return ResponseEntity.ok(accountDto);
    }

    //withdraw rest api
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto>withdraw( @PathVariable Long id, @RequestBody Map<String,Double>request){
        double amount= request.get("amount");
        AccountDto accountDto=accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    // get all account rest api
    @GetMapping()
    public ResponseEntity<List<AccountDto>>getAllAccount(){
        List<AccountDto> accounts = accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }


    //delete Account Rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteAccount( @PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted Successfully");
    }




}
