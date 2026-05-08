package com.device.controller;

import com.device.model.Device;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class DeviceController {
    private List<Device> deviceList = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public DeviceController() {
        deviceList.add(new Device(idCounter.getAndIncrement(), "điện thoại Samsung", "samsung", 5000F, "URL hình ảnh"));
        deviceList.add(new Device(idCounter.getAndIncrement(), "điện thoại Oppo", "oppo", 5000F, "URL hình ảnh"));
        deviceList.add(new Device(idCounter.getAndIncrement(), "điện thoại Redmi", "redmi", 5000F, "URL hình ảnh"));
        deviceList.add(new Device(idCounter.getAndIncrement(), "điện thoại Sony", "sony", 5000F, "URL hình ảnh"));
    }

    @GetMapping("/deviceList")
    public String deviceList(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Device> filteredList = deviceList;
        if (search != null && !search.trim().isEmpty()) {
            String lowerSearch = search.toLowerCase();
            filteredList = deviceList.stream()
                    .filter(d -> d.getDeviceName().toLowerCase().contains(lowerSearch) || d.getBrand().toLowerCase().contains(lowerSearch))
                    .collect(Collectors.toList());
        }
        model.addAttribute("list", filteredList);
        model.addAttribute("search", search);
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("device", new Device());
        return "addForm";
    }

    @PostMapping("/add")
    public String addDevice(@Valid @ModelAttribute("device") Device device, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addForm";
        }
        device.setId(idCounter.getAndIncrement());
        deviceList.add(device);
        redirectAttributes.addFlashAttribute("message", "Thêm thiết bị thành công!");
        return "redirect:/deviceList";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Device> deviceOpt = deviceList.stream().filter(d -> d.getId().equals(id)).findFirst();
        if (deviceOpt.isPresent()) {
            model.addAttribute("device", deviceOpt.get());
            return "addForm";
        }
        return "redirect:/deviceList";
    }

    @PostMapping("/edit/{id}")
    public String updateDevice(@PathVariable("id") Long id, @Valid @ModelAttribute("device") Device device, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/addForm";
        }
        Optional<Device> deviceOpt = deviceList.stream().filter(d -> d.getId().equals(id)).findFirst();
        if (deviceOpt.isPresent()) {
            Device existing = deviceOpt.get();
            existing.setDeviceName(device.getDeviceName());
            existing.setBrand(device.getBrand());
            existing.setPrice(device.getPrice());
            existing.setUrl(device.getUrl());
            redirectAttributes.addFlashAttribute("message", "Cập nhật thiết bị thành công!");
        }
        return "redirect:/deviceList";
    }

    @PostMapping("/delete/{id}")
    public String deleteDevice(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        deviceList.removeIf(d -> d.getId().equals(id));
        redirectAttributes.addFlashAttribute("message", "Xóa thiết bị thành công!");
        return "redirect:/deviceList";
    }
}
