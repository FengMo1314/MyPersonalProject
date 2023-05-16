package com.example.springbootweb.service;


import com.example.springbootweb.pojo.Tokens;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TokensService {
    List<Tokens> getAllTokens();

    List<Tokens> getTokensByUsersId(Tokens tokens);

    List<Tokens> getTokensByUsersIds(Integer[] ids);

    Integer addTokens(Tokens tokens);

    Integer deleteTokensByUsersId(Tokens tokens);

    Tokens getTokensByToken(Tokens tokens);

    Integer updataTokensById(Tokens tokens);
}
