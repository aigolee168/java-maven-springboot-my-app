package com.bf.app.service;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bf.app.mapper.KpiMapper;
import com.bf.app.vo.DailyPayCount;
import com.bf.app.vo.RechargeLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KpiService {
    
    private KpiMapper kpiMapper;

    @Autowired
    public void setKpiMapper(KpiMapper kpiMapper) {
        this.kpiMapper = kpiMapper;
    }
    
    public int countUser() {
        return kpiMapper.countUser();
    }
    
    public int countAuthorityByParentId(long parentId) {
        int count = kpiMapper.countAuthorityByParentId(parentId);
        log.debug("countAuthorityByParentId: {}", parentId);
        return count;
    }
    
    public Map<String, Integer> getDailyRevenue() {
        List<RechargeLog> rechargeLogs = kpiMapper.selectRechargeLog();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Integer> map = rechargeLogs.stream()
            .collect(groupingBy(log -> format.format(log.getDate()), summingInt(RechargeLog::getRmb)));
        return new TreeMap<>(map);
    }
    
    public Map<String, Integer> getDailyPayCount() {
        List<RechargeLog> rechargeLogs = kpiMapper.selectRechargeLog();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return rechargeLogs.stream()
            .collect(collectingAndThen(groupingBy(
                    log -> format.format(log.getDate()), 
                    collectingAndThen(groupingBy(RechargeLog::getPid), Map::size)), 
                    TreeMap::new));
    }
    
    public Map<String, Integer> getDailyPayCount2() {
        List<RechargeLog> logs = kpiMapper.selectRechargeLog();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, List<RechargeLog>> map = new HashMap<>();
        for (RechargeLog log : logs) {
            String day = format.format(log.getDate());
            if (map.containsKey(day)) {
                map.get(day).add(log);
            } else {
                List<RechargeLog> group = new ArrayList<>();
                group.add(log);
                map.put(day, group);
            }
        }
        Map<String, Integer> resultMap = new TreeMap<>();
        for (Entry<String, List<RechargeLog>> entry : map.entrySet()) {
            Set<Long> pids = new HashSet<>();
            for (RechargeLog log : entry.getValue()) {
                pids.add(log.getPid());
            }
            resultMap.put(entry.getKey(), pids.size());
        }
        return resultMap;
    }
    
    public List<DailyPayCount> getDailyPayCount3() {
        return kpiMapper.getDailyPayCount();
    }

}
