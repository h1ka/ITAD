package com.intertad.task;

import com.intertad.task.models.Sum;
import com.intertad.task.service.impl.CSVProductService;
import com.intertad.task.service.CountService;
import com.intertad.task.service.impl.DefaultCountService;
import com.intertad.task.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StdIn {
    private final String FILE_NAME = "~/file.csv";

    private final ProductService productService;
    private final CountService countService;

    public StdIn() {
        this.productService = new CSVProductService(FILE_NAME);
        this.countService = new DefaultCountService();
    }

    public static void main(String[] args) {
        new StdIn().run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<String> codes = new ArrayList<>();
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("ok")) {
                    break;
                }
                if (line.length() > 12) {
                    System.out.println("retype");
                } else {
                    List<String> productsOfCode = productService.getAllByIdentifier(line);
                    if (productsOfCode.size() <= 0) {
                        System.out.println("retype");
                    } else if (productsOfCode.size() == 1) {
                        codes.add(productsOfCode.get(0));
                    } else productsOfCode.forEach(code -> System.out.println(productService.getByIdentifier(code)));
                }
            }

            Sum sum = countService.count(productService.getAllByIds(codes));
            BigDecimal clientSum, changeDue = null;
            while (true) {
                System.out.println("Total: " + sum.printSum());
                System.out.println("Enter client sum: ");
                clientSum = scanner.nextBigDecimal();
                codes.stream().map(productService::getByIdentifier).forEach(System.out::println);
                try {
                    changeDue = countService.getChangeDue(sum, clientSum);
                } catch (RuntimeException e) {
                    System.err.println("re-enter the amount");
                }
                if (Objects.nonNull(changeDue)) {
                    break;
                }
            }
            System.out.println(sum.printSumWithDetailTax());
            System.out.print("CLIENT SUM:" + clientSum);
            System.out.println(" CHANGE DUE " + changeDue);


        }


    }
}
