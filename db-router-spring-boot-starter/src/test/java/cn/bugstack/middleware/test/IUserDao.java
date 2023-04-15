package cn.bugstack.middleware.test;

import cn.bugstack.middleware.db.router.annotation.DBRouter;

/**
 * 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 公众号：bugstack虫洞栈
 * Create by 小傅哥(fustack)
 */

public interface IUserDao {

    @DBRouter(key = "userId")
    void insertUser(String req);

}
