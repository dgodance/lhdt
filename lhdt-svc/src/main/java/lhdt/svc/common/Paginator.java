package lhdt.svc.common;

import org.springframework.data.domain.Page;

/**
 * 페이징에 대한 기능을 제공합니다
 */
public final class Paginator
{
    /**
     * 페이지와 페이지 수 정보를 통해 해당 페이지네이터에 대한 정보를 추출하여 전송합니다
     * @param page
     * @param pageSize
     * @return
     */
    public static PaginatorInfo getPaginatorMap(Page<?> page, PageSize pageSize) {
        Integer pageNavCount = pageSize.getPageNavCount();
        Integer pageNumber = page.getNumber();

        Integer startPageNum = calcStartPageNum(pageNavCount, pageNumber);
        Integer lastPageNum = calcLastPageNum(page, pageNavCount, startPageNum);
        PaginatorInfo pageNav = new PaginatorInfo();
        pageNav.setStartPageNum(startPageNum);
        pageNav.setLastPageNum(lastPageNum);
        pageNav.setPreviousPaging(calcPreviousPaging(startPageNum));
        pageNav.setNextPaging(calcNextPaging(page, lastPageNum));
        return pageNav;
    }

    /**
     * 다음페이지에 대한 값을 구합니다
     * @param page
     * @param lastPageNum
     * @return
     */
    private static Integer calcNextPaging(Page<?> page, Integer lastPageNum) {
        return (lastPageNum + 1) <= (page.getTotalPages() - 1) ? (lastPageNum + 1) : null;
    }

    /**
     * 이전페이지에 대한 값을 구합니다
     * @param startPageNum
     * @return
     */
    private static Integer calcPreviousPaging(Integer startPageNum) {
        return (startPageNum - 1) >= 0 ? (startPageNum - 1) : null;
    }

    /**
     * 마지막 페이지에 대한 값을 구합니다
     * @param page
     * @param pageNavCount
     * @param startPageNum
     * @return
     */
    private static int calcLastPageNum(Page<?> page, Integer pageNavCount, Integer startPageNum) {
        return Math.min(startPageNum + pageNavCount - 1, page.getTotalPages() - 1);
    }

    /**
     * 시작 페이지에 대한 값을 구합니다
     * @param pageNavCount
     * @param pageNumber
     * @return
     */
    private static int calcStartPageNum(Integer pageNavCount, Integer pageNumber) {
        return (pageNumber / pageNavCount) * pageNavCount;
    }
}
