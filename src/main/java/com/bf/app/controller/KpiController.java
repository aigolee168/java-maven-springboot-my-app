package com.bf.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bf.app.service.KpiService;
import com.bf.app.vo.DailyPayCount;

@RestController
@RequestMapping("kpi")
public class KpiController {
    
    private KpiService kpiService;

    @Autowired
    public void setKpiService(KpiService kpiService) {
        this.kpiService = kpiService;
    }
    
    @GetMapping("countUser")
    public int countUser() {
        return kpiService.countUser();
    }
    
    @GetMapping("countAuthorityByParentId")
    public int countAuthorityByParentId(long parentId) {
        return kpiService.countAuthorityByParentId(parentId);
    }
    
    @GetMapping("getDailyRevenue")
    public Map<String, Integer> getDailyRevenue() {
        return kpiService.getDailyRevenue();
    }
    
    @GetMapping("getDailyPayCount")
    public Map<String, Integer> getDailyPayCount() {
        return kpiService.getDailyPayCount();
    }
    
    @GetMapping("getDailyPayCount2")
    public Map<String, Integer> getDailyPayCount2() {
        return kpiService.getDailyPayCount2();
    }
    
    @GetMapping("getDailyPayCount3")
    public List<DailyPayCount> getDailyPayCount3() {
        return kpiService.getDailyPayCount3();
    }

}
