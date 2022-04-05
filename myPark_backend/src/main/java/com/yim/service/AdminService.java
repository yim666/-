package com.yim.service;

import com.yim.pojo.Advice;
import com.yim.pojo.Notice;
import com.yim.pojo.User;
import com.yim.util.PageRes;

import java.util.List;

public interface AdminService {
    List<User> selectUserList();

    int updateUserList(User user);

    PageRes selectNotice(PageRes pageRes);

    int createNotice(Notice notice);

    List<Advice> selectAdviceList();
}
