package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.AdditionalService;
import com.example.service.ActivityLogService;
import com.example.service.AdditionalServiceService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private AdditionalServiceService serviceService;

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public String services(Model model) {
        model.addAttribute("services", serviceService.getAllServices());
        model.addAttribute("newService", new AdditionalService());
        return "services";
    }

    @PostMapping("/save")
    public String saveService(@ModelAttribute AdditionalService service, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        service.setAvailable(true);
        serviceService.save(service);
        activityLogService.log("SERVICE_ADD", "Luu dich vu: " + service.getName(), request);
        redirectAttributes.addFlashAttribute("success", "Luu dich vu thanh cong!");
        return "redirect:/services";
    }

    @PostMapping("/delete/{id}")
    public String deleteService(@PathVariable String id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        serviceService.delete(id);
        activityLogService.log("SERVICE_DELETE", "Xoa dich vu id: " + id, request);
        redirectAttributes.addFlashAttribute("success", "Xoa dich vu thanh cong!");
        return "redirect:/services";
    }

    @PostMapping("/toggle/{id}")
    public String toggleService(@PathVariable String id, RedirectAttributes redirectAttributes) {
        AdditionalService service = serviceService.getServiceById(id);
        if (service != null) {
            service.setAvailable(!service.isAvailable());
            serviceService.save(service);
        }
        return "redirect:/services";
    }
}
