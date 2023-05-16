package com.example.springbootweb.dao;
import com.example.springbootweb.pojo.Tokens;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokensDao {
    List<Tokens> getAllTokens();

    List<Tokens> getTokensByUsersId(@Param("tokens") Tokens tokens);

    Tokens getTokensByToken(@Param("tokens") Tokens tokens);

    List<Tokens> getTokensByUsersIds(@Param("ids") Integer[] ids);

    Integer addTokens(@Param("tokens") Tokens tokens);

    Integer deleteTokensByUsersId(@Param("tokens") Tokens tokens);

    Integer updataTokensById(@Param("tokens") Tokens tokens);

}
