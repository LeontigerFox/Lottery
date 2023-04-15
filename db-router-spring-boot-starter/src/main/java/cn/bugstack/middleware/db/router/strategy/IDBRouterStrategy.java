package cn.bugstack.middleware.db.router.strategy;

/**
 * @description: 路由策略
 * @author: 小傅哥，微信：fustack
 * @date: 2021/10/1
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public interface IDBRouterStrategy {

    /**
     * 路由计算
     *
     * @param dbKeyAttr 路由字段
     */
    void doRouter(String dbKeyAttr);

    /**
     * 清除路由
     */
    void clear();

}
