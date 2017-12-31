package StreamQ;

import StreamQ.*;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice{
    public static void main(String ...args){    
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300), 
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),	
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        List<Transaction> a1 =
                transactions
                        .stream()
                        .filter(y -> y.getYear() == 2011)
                        .sorted(comparing(Transaction::getValue))
                        .collect(toList());

        List<String> city =
                transactions.stream().map(s -> s.getTrader().getCity()).distinct().collect(toList());

        List<String> a3 =
                transactions
                    .stream()
                    .filter(t -> t.getTrader().getCity() == "Cambridge")
                    .map(Transaction::getTrader)
                    .distinct()
                    .sorted(comparing(Trader::getName))
                    .map(Trader::getName)
                    .collect(toList());

        boolean a =
                transactions
                .stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milano"));

        System.out.println(a1);
        System.out.println(city);
        System.out.println(a3);
        System.out.println(a);
    }

}