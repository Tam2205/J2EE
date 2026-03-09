package com.example.controller;

import com.example.model.AdditionalService;
import com.example.service.AdditionalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private AdditionalServiceService serviceService;

    @GetMapping
    public String services(Model model) {
        model.addAttribute("services", serviceService.getAllServices());
        model.addAttribute("newService", new AdditionalService());
        return "services";
    }

    @PostMapping("/save")
    public String saveService(@ModelAttribute AdditionalService service, RedirectAttributes redirectAttributes) {
        service.setAvailable(true);
        serviceService.save(service);
        redirectAttributes.addFlashAttribute("success", "Luu dich vu thanh cong!");
        return "redirect:/services";
    }

    @PostMapping("/delete/{id}")
    public String deleteService(@PathVariable String id, RedirectAttributes redirectAttributes) {
        serviceService.delete(id);
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
