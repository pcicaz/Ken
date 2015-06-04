package com.sc.test.bean;

import java.util.List;

/**
 * @author steven
 * @date 5/22/15
 */
public class PageResult {
    private long total;
    private List result;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
