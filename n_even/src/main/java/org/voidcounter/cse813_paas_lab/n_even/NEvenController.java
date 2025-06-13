package org.voidcounter.cse813_paas_lab.n_even;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NEvenController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/generate")
    public String generateEvenNumbers(@RequestParam("n") int n, Model model) {
        if (n > 100000) {
            model.addAttribute("message", "The number is too large to generate");
            model.addAttribute("n", n);
        } else {
            List<Integer> evenNumbers = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                evenNumbers.add(i * 2);
            }
            model.addAttribute("n", n);
            model.addAttribute("evenNumbers", evenNumbers);
        }
        return "index";
    }
}

