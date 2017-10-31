package com.koanruler.mp.entity;

import java.util.List;

/**
 * Created by chengyuan on 2017/10/19.
 */
public class ResultData<T> {
    private Long total;
    private Integer per_page;
    private Integer current_page;
    private Integer last_page;
    private String  next_page_url;
    private String  prev_page_url;
    private Integer from;
    private Integer to;
    private List<T> data;

    public ResultData(int page, int countPerPage, String prevPageUrl, String nextPageUrl, ResultList<T> resultList) {
        setCurrent_page(page);
        setPer_page(countPerPage);
        setData(resultList.getDataInfo());

        int lastPage = (int)resultList.getTotalCount()/countPerPage;
        lastPage += resultList.getTotalCount() % countPerPage != 0 ? 1 : 0;
        setLast_page(lastPage);
        setNext_page_url(nextPageUrl);
        setPrev_page_url(prevPageUrl);

        setFrom((page-1)*countPerPage+1);
        setTo((page-1)*countPerPage+resultList.getDataInfo().size());
        setTotal(resultList.getTotalCount());
    }

    public Long getTotal() {
        return total;
    }

    public ResultData setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public ResultData setPer_page(Integer per_page) {
        this.per_page = per_page;
        return this;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public ResultData setCurrent_page(Integer current_page) {
        this.current_page = current_page;
        return this;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public ResultData setLast_page(Integer last_page) {
        this.last_page = last_page;
        return this;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public ResultData setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
        return this;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public ResultData setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
        return this;
    }

    public Integer getFrom() {
        return from;
    }

    public ResultData setFrom(Integer from) {
        this.from = from;
        return this;
    }

    public Integer getTo() {
        return to;
    }

    public ResultData setTo(Integer to) {
        this.to = to;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public ResultData setData(List<T> data) {
        this.data = data;
        return this;
    }
}
