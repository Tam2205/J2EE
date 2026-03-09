package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.ActivityLogService;

@Controller
@RequestMapping("/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public String activityLogs(@RequestParam(required = false) String username, Model model) {
        if (username != null && !username.isEmpty()) {
            model.addAttribute("logs", activityLogService.getLogsByUsername(username));
            model.addAttribute("filterUsername", username);
        } else {
            model.addAttribute("logs", activityLogService.getAllLogs());
        }
        return "activity-logs";
    }
}
