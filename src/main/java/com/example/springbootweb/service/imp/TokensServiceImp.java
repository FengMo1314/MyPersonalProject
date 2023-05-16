package com.example.springbootweb.service.imp;
import com.example.springbootweb.dao.TokensDao;
import com.example.springbootweb.pojo.Tokens;
import com.example.springbootweb.service.TokensService;
import com.example.springbootweb.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TokensServiceImp implements TokensService {
    @Autowired
    private TokensDao tokensDao;
    private Date date;
    @Override
    public List<Tokens> getAllTokens() {
        return tokensDao.getAllTokens();
    }

    @Override
    public List<Tokens> getTokensByUsersId(Tokens tokens) {
        return tokensDao.getTokensByUsersId(tokens);
    }

    @Override
    public List<Tokens> getTokensByUsersIds(Integer[] ids) {
        return tokensDao.getTokensByUsersIds(ids);
    }

    @Override
    public Integer addTokens(Tokens tokens) {
        date=new Date();
        tokens.setCreation_time(date);
        tokens.setEnd_time(JwtUtil.expireDate);
        return tokensDao.addTokens(tokens);
    }

    @Override
    public Integer deleteTokensByUsersId(Tokens tokens) {
        return tokensDao.deleteTokensByUsersId(tokens);
    }

    @Override
    public Tokens getTokensByToken(Tokens tokens) {
        return tokensDao.getTokensByToken(tokens);
    }

    @Override
    public Integer updataTokensById(Tokens tokens) {
        return tokensDao.updataTokensById(tokens);
    }
}
