package com.banana69.lottery.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/17:59
 * @description: 分页类
 */
@Data
@NoArgsConstructor
public class PageRequest {
    /**
     * 开始 limit 第1个入参
     */
    private int pageBegin = 0;

    /**
     * 开始 limit 第2个入参
     */
    private int pageEnd = 0;

    /**
     * 页数
     */
    private int page;

    /**
     * 行数
     */
    private int rows;

    public PageRequest(String page, String rows) {
        this.setPage(page, rows);
    }

    public PageRequest(int page, int rows) {
        this.setPage(page, rows);
    }

    public void setPage(String page, String rows) {
        this.page = null == page ? 1 : Integer.parseInt(page);
        this.rows = null == page ? 10 : Integer.parseInt(rows);
        if (0 == this.page) {
            this.page = 1;
        }
        this.pageBegin = (this.page - 1) * this.rows;
        this.pageEnd = this.rows;
    }

    public void setPage(int page, int rows) {
        this.page = page;
        this.rows = rows;

        if (0 == this.page) {
            this.page = 1;
        }
        this.pageBegin = (this.page - 1) * this.rows;
        this.pageEnd = this.rows;
    }


}
