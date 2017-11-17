package com.xxg.jcatch.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageUtil {
	
	public static long getPageCount(long dataCount, long pageSize) {
		long pageCount = 1;
		if(dataCount > 0) {
			if(dataCount % pageSize == 0) {
				pageCount = dataCount / pageSize;
			} else {
				pageCount = dataCount / pageSize + 1;
			}
		}
		return pageCount;
	}
	
	public static List<Long> generatePageList(long p, long pageCount) {
		List<Long> pageList = new ArrayList<>();
		if(p <= 5) {
			for(long i = 1; i < 10 && i <= pageCount; i++) {
				pageList.add(i);
			}
		} else if(p >= pageCount - 4) {
			for(long i = pageCount; i > 0 && i > pageCount - 9; i--) {
				pageList.add(i);
			}
			Collections.reverse(pageList);
		} else {
			for(long i = p - 4; i <= p + 4; i++) {
				pageList.add(i);
			}
		}
		return pageList;
	}

}
