package org.zx.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhouxin
 * @since 2019/6/5
 */
@Getter
@Setter
@AllArgsConstructor
public class ZXPage<T> {
    // 当前页索引
    private Integer pageIndex;
    // 每一页有多少数据
    private Integer pageSize;
    // 一共有多少页
    private Integer totalPages;
    // 一共有多少数据
    private Long totalElements;

    private List<T> data;

    private ZXPage(){}

    public static <T> ZXPage<T> of(Page<T> page){
        ZXPage<T> ZXPage = new ZXPage();

        int pageIndex = page.getNumber() + 1;
        ZXPage.setPageIndex(pageIndex);

        int pageSize = page.getSize();
        ZXPage.setPageSize(pageSize);

        int totalPages = page.getTotalPages();
        ZXPage.setTotalPages(totalPages);

        long totalElements = page.getTotalElements();
        ZXPage.setTotalElements(totalElements);

        List<T> data = page.getContent();
        ZXPage.setData(data);

        return ZXPage;
    }
}
