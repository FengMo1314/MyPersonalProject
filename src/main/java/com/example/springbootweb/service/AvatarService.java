package com.example.springbootweb.service;
import com.example.springbootweb.pojo.Avatar;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AvatarService {
    Avatar getById(Integer id);
    Avatar gettByUId(Integer uId);
    Avatar gettByUName(String uName);
    Avatar gettByUrl(String url);
    Integer addAvatar(Avatar avatar);
    Integer deleteAvatarById(Integer id);
    Integer deleteAvatarByUId(Integer uId);
    Integer deleteAvatarByUname(String uName);
    Integer deleteAvatarByUrl(String url);
}
