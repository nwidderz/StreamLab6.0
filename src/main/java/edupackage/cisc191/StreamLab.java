package edupackage.cisc191;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * StreamLab compiles the net balance of each account
 * using both Stream API as well as for loops,
 * comparing the time it takes to process
 */

public class StreamLab {
    public static void main(String[] args) {

        // List
        List<AccountInfo> stats = new CsvToBeanBuilder<AccountInfo>(
                new InputStreamReader(Objects.requireNonNull(StreamLab.class.getResourceAsStream("/accounts-aggregated.csv"))))
                .withType(AccountInfo.class)
                .build()
                .parse();

        // Run Stream and loop methods
        long loopTime = computeNetBalanceLoop(stats);
        long streamTime = computeNetBalanceStream(stats);

        System.out.println("\n------RESULTS------");
        System.out.println("Loop took " + loopTime + "ms to complete");
        System.out.println("Stream took " + streamTime + "ms to complete");
    }

    /**
     * Computes the net balance that each account owns (balance - debt) using streams
     * @param stats List of AccountStats to compute net balance
     * @return Time to process in ms
     */
    public static long computeNetBalanceStream(List<AccountInfo> stats) {
        System.out.println("\n------Stream------");
        long startTime = System.currentTimeMillis();

        // Stream here
        stats.stream()
                .collect(Collectors.groupingBy(AccountInfo::getCardNumber,(Collectors.summingDouble(e -> e.getBalance() - e.getDebt()))))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, TreeMap::new))
                .forEach((cardNumber, balance) -> System.out.printf("Account: %d || net balance: %.2f\n", cardNumber, balance));
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Computes the net balance that each account owns (balance - debt) using a loop
     * @param stats List of AccountStats to compute net balance
     * @return Time to process in ms
     */
    public static long computeNetBalanceLoop(List<AccountInfo> stats) {
        System.out.println("\n------Loop------");
        long startTime = System.currentTimeMillis();

        Map<Long, Double> accountToNetBalance = new TreeMap<>();
        for (AccountInfo stat: stats) {
            double netBalance = stat.getBalance() - stat.getDebt();
            accountToNetBalance.put(stat.getCardNumber(), netBalance);
        }

        for (Map.Entry<Long, Double> entry: accountToNetBalance.entrySet()) {
            System.out.printf("Account: %d || Net Balance: %.2f\n", entry.getKey(), entry.getValue());
        }

        return System.currentTimeMillis() - startTime;
    }
}