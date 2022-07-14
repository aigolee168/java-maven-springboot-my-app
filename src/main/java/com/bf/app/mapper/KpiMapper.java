package com.bf.app.mapper;

import java.util.List;

import com.bf.app.vo.DailyPayCount;
import com.bf.app.vo.RechargeLog;

public interface KpiMapper {

	int countUser();

	int countAuthorityByParentId(long parentId);
	
	List<RechargeLog> selectRechargeLog();
	
	List<DailyPayCount> getDailyPayCount();

}
