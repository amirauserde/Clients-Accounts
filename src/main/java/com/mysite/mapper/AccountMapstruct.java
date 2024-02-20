package com.mysite.mapper;

import com.mysite.Model.bankAccounts.Account;
import com.mysite.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper
public interface AccountMapstruct {
    @Mapping(source = "accountOpenDate", target = "accountOpenDate")
    AccountDto mapToAccountDto(Account account);

    @Mapping(source = "accountOpenDate", target = "accountOpenDate")
    List<AccountDto> mapToAccountDtoList(List<Account> accountList);

    @Mapping(ignore = true, target = "accountID")
    Account mapToAccount(AccountDto accountDto, @MappingTarget Account account);

    @Mapping(ignore = true, target = "accountID")
    Account mapToAccount(AccountDto accountDto);
}
