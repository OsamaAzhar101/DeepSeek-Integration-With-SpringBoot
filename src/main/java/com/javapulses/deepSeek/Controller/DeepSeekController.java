package com.javapulses.deepSeek.Controller;

import com.javapulses.deepSeek.service.DeepSeekService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deepseek")
public class DeepSeekController {

    private final DeepSeekService deepSeekService;

    public DeepSeekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String prompt) {
        return deepSeekService.getResponse(prompt);
    }
}