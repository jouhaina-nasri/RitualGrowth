package com.yoterra.hippo.res;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GetPage {

    public static Page<?> getPage(List<?> mylist, Pageable pageable){
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), mylist.size());
        final Page<?> page = new PageImpl<>(mylist.subList(start, end), pageable, mylist.size());
        return page;
    }
}
