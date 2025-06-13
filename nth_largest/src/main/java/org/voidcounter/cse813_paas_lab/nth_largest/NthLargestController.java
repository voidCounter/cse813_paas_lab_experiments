package org.voidcounter.cse813_paas_lab.nth_largest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NthLargestController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/find-largest")
    public String findLargest(@RequestParam String numbers, @RequestParam(required = false) Integer n, Model model) {
        model.addAttribute("numbers", numbers); // Keep numbers in the input field
        if (n == null) { // Default to finding the largest if n is not provided
            n = 1;
        }
        model.addAttribute("n", n);


        if (numbers == null || numbers.trim().isEmpty()) {
            model.addAttribute("error", "Please enter a list of numbers.");
            return "index";
        }

        List<Integer> numList;
        try {
            numList = Arrays.stream(numbers.trim().split("\\s+"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid input: Please enter space-separated integers only.");
            return "index";
        }

        if (numList.isEmpty()) {
            model.addAttribute("error", "Please enter at least one number.");
            return "index";
        }

        if (n <= 0 || n > numList.size()) {
            model.addAttribute("error", "N must be between 1 and the count of numbers entered (" + numList.size() + ").");
            return "index";
        }

        Collections.sort(numList, Collections.reverseOrder());
        Integer nthLargest = numList.get(n - 1);

        model.addAttribute("largest", nthLargest);
        String ordinalSuffix;
        if (n % 10 == 1 && n % 100 != 11) {
            ordinalSuffix = "st";
        } else if (n % 10 == 2 && n % 100 != 12) {
            ordinalSuffix = "nd";
        } else if (n % 10 == 3 && n % 100 != 13) {
            ordinalSuffix = "rd";
        } else {
            ordinalSuffix = "th";
        }
        model.addAttribute("message", "The " + n + ordinalSuffix + " largest number is:");
        return "index";
    }
}

