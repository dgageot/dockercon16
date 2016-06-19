import net.codestory.http.Context;
import net.codestory.http.filters.Filter;
import net.codestory.http.filters.PayloadSupplier;
import net.codestory.http.payload.Payload;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddHostName implements Filter {
    private final String hostname;

    public AddHostName() throws UnknownHostException {
        hostname = InetAddress.getLocalHost().getHostName();
    }

    @Override
    public Payload apply(String uri, Context context, PayloadSupplier nextFilter) throws Exception {
        Payload payload = nextFilter.get();

        if (payload.rawContent() instanceof WordResponse) {
            WordResponse response = (WordResponse) payload.rawContent();
            response.hostname = hostname;
        }

        return payload;
    }
}
