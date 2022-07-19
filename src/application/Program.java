package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) throws FileNotFoundException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("entre com a localização do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<Product> list = new ArrayList<>();

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = bufferedReader.readLine();
            }

            double avarage = list.stream()
                    .map(Product::getPrice)
                    .reduce(0.0, Double::sum) / list.size();

            System.out.println("media de preço: " + String.format("%.2f", avarage));

            Comparator<String> comparator = Comparator.comparing(String::toUpperCase);

            List<String> names = list.stream()
                    .filter(price -> price.getPrice() < avarage)
                    .map(Product::getName)
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());

            names.forEach(System.out::println);
        }catch (IOException e){
            System.out.println("error: " + e.getMessage());
        }
        sc.close();
    }
}
