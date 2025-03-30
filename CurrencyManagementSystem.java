import java.util.*;
import java.io.*;

public class CurrencyManagementSystem {
    private List<Currency> currencies;
    private Map<String, List<Currency>> continentMap;
    private Scanner scanner;
    
    public CurrencyManagementSystem() {
        currencies = new ArrayList<>();
        continentMap = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeData();
    }
    
    private void initializeData() {
        // Initialize with some sample data
        addCurrency(new Currency("USD", "US Dollar", 56.00, "United States", "North America"));
        addCurrency(new Currency("EUR", "Euro", 61.00, "European Union", "Europe"));
        addCurrency(new Currency("JPY", "Japanese Yen", 0.40, "Japan", "Asia"));
        addCurrency(new Currency("GBP", "British Pound", 70.00, "United Kingdom", "Europe"));
        addCurrency(new Currency("AUD", "Australian Dollar", 37.00, "Australia", "Oceania"));
        addCurrency(new Currency("KRW", "South Korean Won", 0.042, "South Korea", "Asia"));
        addCurrency(new Currency("CNY", "Chinese Yuan", 7.80, "China", "Asia"));
        addCurrency(new Currency("SGD", "Singapore Dollar", 41.50, "Singapore", "Asia"));
    }
    
    public void showMenu() {
        while (true) {
            try {
                System.out.println("\n=== Currency Management System ===");
                System.out.println("\nSupported Currency Codes:");
                System.out.println("USD - US Dollar (United States)");
                System.out.println("EUR - Euro (European Union)");
                System.out.println("JPY - Japanese Yen (Japan)");
                System.out.println("GBP - British Pound (United Kingdom)");
                System.out.println("AUD - Australian Dollar (Australia)");
                System.out.println("KRW - South Korean Won (South Korea)");
                System.out.println("CNY - Chinese Yuan (China)");
                System.out.println("SGD - Singapore Dollar (Singapore)");
                
                System.out.println("\nMenu Options:");
                System.out.println("1. Add New Currency");
                System.out.println("2. View All Currencies");
                System.out.println("3. Search Currency");
                System.out.println("4. Update Currency");
                System.out.println("5. Delete Currency");
                System.out.println("6. View Currencies by Continent");
                System.out.println("7. Generate Report");
                System.out.println("8. Convert Currency");
                System.out.println("9. Exit");
                System.out.print("\nEnter your choice: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: addNewCurrency(); break;
                    case 2: viewAllCurrencies(); break;
                    case 3: searchCurrency(); break;
                    case 4: updateCurrency(); break;
                    case 5: deleteCurrency(); break;
                    case 6: viewByContinent(); break;
                    case 7: generateReport(); break;
                    case 8: convertCurrency(); break;
                    case 9: 
                        System.out.println("Thank you for using Currency Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    
    private void addNewCurrency() {
        System.out.println("\n=== Add New Currency ===");
        System.out.print("Enter Currency Code: ");
        String code = scanner.nextLine().toUpperCase();
        
        if (currencies.stream().anyMatch(c -> c.getCurrencyCode().equals(code))) {
            System.out.println("Currency code already exists!");
            return;
        }
        
        System.out.print("Enter Currency Name: ");
        String name = scanner.nextLine();
        
        double rate = 0;
        while (true) {
            try {
                System.out.print("Enter Exchange Rate to PHP: ");
                rate = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        System.out.print("Enter Country: ");
        String country = scanner.nextLine();
        
        System.out.print("Enter Continent: ");
        String continent = scanner.nextLine();
        
        Currency currency = new Currency(code, name, rate, country, continent);
        addCurrency(currency);
        System.out.println("Currency added successfully!");
    }
    
    private void addCurrency(Currency currency) {
        currencies.add(currency);
        continentMap.computeIfAbsent(currency.getContinent(), k -> new ArrayList<>())
                   .add(currency);
    }
    
    private void viewAllCurrencies() {
        System.out.println("\n=== All Currencies ===");
        printHeader();
        currencies.stream()
                 .filter(Currency::isActive)
                 .forEach(System.out::println);
    }
    
    private void searchCurrency() {
        System.out.print("\nEnter currency code to search: ");
        String code = scanner.nextLine().toUpperCase();
        
        currencies.stream()
                 .filter(c -> c.getCurrencyCode().equals(code) && c.isActive())
                 .findFirst()
                 .ifPresentOrElse(
                     currency -> {
                         printHeader();
                         System.out.println(currency);
                     },
                     () -> System.out.println("Currency not found!")
                 );
    }
    
    private void updateCurrency() {
        System.out.print("\nEnter currency code to update: ");
        String code = scanner.nextLine().toUpperCase();
        
        Optional<Currency> currencyOpt = currencies.stream()
                                                 .filter(c -> c.getCurrencyCode().equals(code) && c.isActive())
                                                 .findFirst();
        
        if (currencyOpt.isEmpty()) {
            System.out.println("Currency not found!");
            return;
        }
        
        Currency currency = currencyOpt.get();
        System.out.println("Current details:");
        printHeader();
        System.out.println(currency);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("New Currency Name [" + currency.getCurrencyName() + "]: ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) currency.setCurrencyName(input);
        
        while (true) {
            try {
                System.out.print("New Exchange Rate [" + currency.getExchangeRate() + "]: ");
                input = scanner.nextLine();
                if (!input.isEmpty()) currency.setExchangeRate(Double.parseDouble(input));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        System.out.print("New Country [" + currency.getCountry() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) currency.setCountry(input);
        
        System.out.print("New Continent [" + currency.getContinent() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            String oldContinent = currency.getContinent();
            currency.setContinent(input);
            updateContinentMap(currency, oldContinent);
        }
        
        System.out.println("Currency updated successfully!");
    }
    
    private void deleteCurrency() {
        System.out.print("\nEnter currency code to delete: ");
        String code = scanner.nextLine().toUpperCase();
        
        currencies.stream()
                 .filter(c -> c.getCurrencyCode().equals(code) && c.isActive())
                 .findFirst()
                 .ifPresentOrElse(
                     currency -> {
                         currency.setActive(false);
                         System.out.println("Currency deleted successfully!");
                     },
                     () -> System.out.println("Currency not found!")
                 );
    }
    
    private void viewByContinent() {
        System.out.println("\n=== Currencies by Continent ===");
        continentMap.forEach((continent, currencyList) -> {
            System.out.println("\nContinent: " + continent);
            printHeader();
            currencyList.stream()
                       .filter(Currency::isActive)
                       .forEach(System.out::println);
        });
    }
    
    private void generateReport() {
        System.out.println("\n=== Currency Report ===");
        System.out.println("Total number of currencies: " + 
            currencies.stream().filter(Currency::isActive).count());
        
        System.out.println("\nCurrencies per continent:");
        continentMap.forEach((continent, currencyList) -> {
            long count = currencyList.stream().filter(Currency::isActive).count();
            System.out.printf("%s: %d currencies%n", continent, count);
        });
        
        System.out.println("\nHighest exchange rate: ");
        currencies.stream()
                 .filter(Currency::isActive)
                 .max(Comparator.comparingDouble(Currency::getExchangeRate))
                 .ifPresent(c -> System.out.printf("%s (%.2f PHP)%n", 
                     c.getCurrencyCode(), c.getExchangeRate()));
                     
        System.out.println("\nLowest exchange rate: ");
        currencies.stream()
                 .filter(Currency::isActive)
                 .min(Comparator.comparingDouble(Currency::getExchangeRate))
                 .ifPresent(c -> System.out.printf("%s (%.2f PHP)%n", 
                     c.getCurrencyCode(), c.getExchangeRate()));
    }
    
    private void updateContinentMap(Currency currency, String oldContinent) {
        continentMap.get(oldContinent).remove(currency);
        continentMap.computeIfAbsent(currency.getContinent(), k -> new ArrayList<>())
                   .add(currency);
    }
    
    private void printHeader() {
        System.out.println("CODE | NAME                 | RATE      | COUNTRY         | CONTINENT  | STATUS");
        System.out.println("-".repeat(80));
    }
    
    private void convertCurrency() {
        System.out.println("\n=== Currency Converter ===");
        
        // Get source currency
        System.out.print("Enter source currency code (e.g., USD): ");
        String sourceCurrency = scanner.nextLine().toUpperCase();
        
        // Get target currency
        System.out.print("Enter target currency code (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();
        
        // Get amount
        System.out.print("Enter amount to convert: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            // Find currencies
            Optional<Currency> sourceCurrencyObj = currencies.stream()
                .filter(c -> c.getCurrencyCode().equals(sourceCurrency) && c.isActive())
                .findFirst();
                
            Optional<Currency> targetCurrencyObj = currencies.stream()
                .filter(c -> c.getCurrencyCode().equals(targetCurrency) && c.isActive())
                .findFirst();
                
            if (sourceCurrencyObj.isEmpty() || targetCurrencyObj.isEmpty()) {
                System.out.println("Error: One or both currencies not found!");
                return;
            }
            
            // Convert through PHP (base currency)
            double phpAmount = amount * sourceCurrencyObj.get().getExchangeRate();
            double targetAmount = phpAmount / targetCurrencyObj.get().getExchangeRate();
            
            // Display result
            System.out.printf("\nConversion Result:\n");
            System.out.printf("%.2f %s = %.2f %s\n", 
                amount, sourceCurrency, 
                targetAmount, targetCurrency);
            
            // Show additional information
            System.out.printf("\nExchange Rates Used:\n");
            System.out.printf("1 %s = %.2f PHP\n", 
                sourceCurrency, sourceCurrencyObj.get().getExchangeRate());
            System.out.printf("1 %s = %.2f PHP\n", 
                targetCurrency, targetCurrencyObj.get().getExchangeRate());
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number for the amount.");
        } catch (Exception e) {
            System.out.println("Error during conversion: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        CurrencyManagementSystem cms = new CurrencyManagementSystem();
        cms.showMenu();
    }
} 