package org.voidcounter.cse813_paas_lab.matrix_multiplication;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MatrixMultiplicationController {
    @PostMapping("/multiply-matrices")
    public String multiplyMatrices(@RequestParam String a, @RequestParam String b, Model model) {

        String[] aRows = a.trim().split("\\n");
        String[] bRows = b.trim().split("\\n");

        if (aRows.length == 0 || bRows.length == 0 || aRows[0].trim().isEmpty() || bRows[0].trim().isEmpty()) {
            model.addAttribute("error", "Matrices cannot be empty.");
            return "index";
        }

        int aRowsSize = aRows.length;
        int bRowsSize = bRows.length;
        int aColsSize = aRows[0].trim().split("\\s+").length;
        int bColsSize = bRows[0].trim().split("\\s+").length;

        if (aColsSize != bRowsSize) {
            model.addAttribute("error", "The number of columns in the first matrix must be equal to the number of rows in the second matrix.");
            return "index";
        }

        int[][] matrixA = new int[aRowsSize][aColsSize];
        int[][] matrixB = new int[bRowsSize][bColsSize];

        try {
            for (int i = 0; i < aRowsSize; i++) {
                String[] vals = aRows[i].trim().split("\\s+");
                if (vals.length != aColsSize) {
                    model.addAttribute("error", "Matrix A has inconsistent column numbers.");
                    return "index";
                }
                for (int j = 0; j < aColsSize; j++) {
                    matrixA[i][j] = Integer.parseInt(vals[j]);
                }
            }

            for (int i = 0; i < bRowsSize; i++) {
                String[] vals = bRows[i].trim().split("\\s+");
                if (vals.length != bColsSize) {
                    model.addAttribute("error", "Matrix B has inconsistent column numbers.");
                    return "index";
                }
                for (int j = 0; j < bColsSize; j++) {
                    matrixB[i][j] = Integer.parseInt(vals[j]);
                }
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Matrices must contain valid integers only.");
            return "index";
        }

        int[][] resultMatrix = new int[aRowsSize][bColsSize];

        for (int i = 0; i < aRowsSize; i++) {
            for (int k = 0; k < bColsSize; k++) {
                resultMatrix[i][k] = 0;
                for (int j = 0; j < aColsSize; j++) {
                    resultMatrix[i][k] += matrixA[i][j] * matrixB[j][k];
                }
            }
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < aRowsSize; i++) {
            for (int k = 0; k < bColsSize; k++) {
                resultBuilder.append(resultMatrix[i][k]).append(" ");
            }
            resultBuilder.append("\n");
        }
        model.addAttribute("result", resultBuilder.toString().trim());
        model.addAttribute("message", "Multiplication Successful:");
        return "index";
    }
}
