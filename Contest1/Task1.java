import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    private static final int SPLITTER_SIZE = 2;
    private static final int HUB_SIZE = 5;
    private static final int USED_PORT = 1;

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            long ports = Long.parseLong(reader.readLine());
            long gadgets = Long.parseLong(reader.readLine());
            int splitterPrice = Integer.parseInt(reader.readLine());
            int hubPrice = Integer.parseInt(reader.readLine());
            System.out.println(identifyMinPrice(gadgets - ports, splitterPrice, hubPrice));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long identifyMinPrice(long portsRequired, int splitterPrice, int hubPrice) {
        if (portsRequired <= 0) {
            return 0;
        }
        long minPrice;
        long onlySplitterPrice = identifyOnlySplitter(portsRequired, splitterPrice);
        long balancedPrice = identifyPortBalance(portsRequired, splitterPrice, hubPrice);

        minPrice = Math.min(onlySplitterPrice, balancedPrice);
        long onlyHubPrice = identifyOnlyHub(portsRequired, hubPrice);

        minPrice = Math.min(minPrice, onlyHubPrice);
        return minPrice;
    }

    private static long identifyOnlySplitter(long portsRequired, int splitterPrice) {
        return splitterPrice * portsRequired;
    }

    private static long identifyPortBalance(long portsRequired, int splitterPrice, int hubPrice) {
        long price = 0;
        while (portsRequired > 0) {
            if (portsRequired > HUB_SIZE - USED_PORT) {
                long count = portsRequired / (HUB_SIZE - USED_PORT);
                price += count * hubPrice;
                portsRequired = portsRequired % (HUB_SIZE - USED_PORT);
            } else {
                price += splitterPrice;
                portsRequired -= SPLITTER_SIZE - USED_PORT;
            }
        }
        return price;
    }

    private static long identifyOnlyHub(long portsRequired, int hubPrice) {
        long price = 0;
        long count = portsRequired / (HUB_SIZE - USED_PORT);
        price += count * hubPrice;

        return portsRequired > 0 ? price + hubPrice : price;
    }
}