package com.gpak.AccountManager.util;

import com.gpak.AccountManager.entity.Account;
import com.gpak.AccountManager.entity.Entry;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utility {
    public List<Account> getAllAccountsWithDate(LocalDate stDate, LocalDate endDate, List<Account> accountList) {
        List<Account> list = new ArrayList<>();
        for (Account account: accountList) {
            List<Entry> entryList = account.getEntryList();
            account.setEntryList(null);
            List<Entry> newEntryList = new ArrayList<>();
            for (Entry entry : entryList) {
                if ((entry.getDate().isAfter(stDate) && (entry.getDate().isBefore(endDate)))) {
                    newEntryList.add(entry);
                }
            }
            account.setEntryList(newEntryList);
        }
        return accountList;
    }
}
