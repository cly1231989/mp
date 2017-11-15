package koanruler.entity;

import java.util.List;

/**
 * Created by chengyuan on 2017/9/15.
 */
public class ResultList<E> {
    private long totalCount;
    private List<E> dataInfo;

    public ResultList(long totalCount, List<E> dataInfo) {
        this.totalCount = totalCount;
        this.dataInfo = dataInfo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public ResultList setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public List<E> getDataInfo() {
        return dataInfo;
    }

    public ResultList setDataInfo(List<E> dataInfo) {
        this.dataInfo = dataInfo;
        return this;
    }
}
