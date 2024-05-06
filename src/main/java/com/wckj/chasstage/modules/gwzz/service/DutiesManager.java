package com.wckj.chasstage.modules.gwzz.service;

public interface DutiesManager {

    boolean isHaveDutyByRoleCode(String roleCode);

    boolean isHaveDutyByDutyCode(String dutyCode);

    boolean isHaveDutyByDutyCodeAndRoleCode(String dutyCode,String roleCode);
}
