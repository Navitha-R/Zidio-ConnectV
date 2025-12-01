package com.zidio.jobportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidio.jobportal.DTO.AdminActionLogDTO;
import com.zidio.jobportal.service.AdminActionLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
public class AdminActionLogController {

    private final AdminActionLogService logService;

    // ✅ Create a log
    @PostMapping("/create")
    public ResponseEntity<AdminActionLogDTO> createLog(@RequestBody AdminActionLogDTO dto) {
        return ResponseEntity.ok(logService.createLog(dto));
    }

    // ✅ Get all logs
    @GetMapping
    public ResponseEntity<List<AdminActionLogDTO>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    // ✅ Get logs by admin ID
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AdminActionLogDTO>> getLogsByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(logService.getLogsByAdmin(adminId));
    }
}

	


