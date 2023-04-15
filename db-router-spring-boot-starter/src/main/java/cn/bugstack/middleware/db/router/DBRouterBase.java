package cn.bugstack.middleware.db.router;

/**
 * @description: 数据源基础配置
 * @author: 小傅哥，微信：fustack
 * @date: 2021/9/22
 * @github: https://github.com/fuzhengwei
 * @Copyright: 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public class DBRouterBase {

    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }

}
