package com.example.springbootweb.service.imp;
import com.example.springbootweb.dao.AvatarDao;
import com.example.springbootweb.pojo.Avatar;
import com.example.springbootweb.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvatarServiceImp implements AvatarService {
    @Autowired
    private AvatarDao avatarDao;
    private Avatar avatar;
    @Override
    public Avatar getById(Integer id) {
        avatar=new Avatar();
        avatar.setId(id);
        return avatarDao.getById(avatar);
    }

    @Override
    public Avatar gettByUId(Integer uId) {
        avatar=new Avatar();
        avatar.setUId(uId);
        return avatarDao.gettByUId(avatar);
    }

    @Override
    public Avatar gettByUName(String uName) {
        avatar=new Avatar();
        avatar.setUName(uName);
        return avatarDao.gettByUName(avatar);
    }

    @Override
    public Avatar gettByUrl(String url) {
        avatar=new Avatar();
        avatar.setImgUrl(url);
        return avatarDao.gettByUrl(avatar);
    }

    @Override
    public Integer addAvatar(Avatar avatars) {
        avatar=avatars;
        return avatarDao.addAvatar(avatars);
    }

    @Override
    public Integer deleteAvatarById(Integer id) {
        avatar=new Avatar();
        avatar.setId(id);
        return avatarDao.deleteAvatarById(avatar);
    }

    @Override
    public Integer deleteAvatarByUId(Integer uId) {
        avatar=new Avatar();
        avatar.setUId(uId);
        return avatarDao.deleteAvatarByUId(avatar);
    }

    @Override
    public Integer deleteAvatarByUname(String uName) {
        avatar=new Avatar();
        avatar.setUName(uName);
        return avatarDao.deleteAvatarByUname(avatar);
    }

    @Override
    public Integer deleteAvatarByUrl(String url) {
        avatar=new Avatar();
        avatar.setImgUrl(url);
        return avatarDao.deleteAvatarByUrl(avatar);
    }
}
