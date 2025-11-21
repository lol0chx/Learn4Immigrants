package classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void getInfo() {
        // arrange
        Provider provider = new Provider("Yearup United ", "hana@yearupUnited.org");

        // act
        String info = provider.getInfo();

        // assertions
         // check provider name
        assertEquals("Yearup United", provider.getName());
        //  check provider info
        assertEquals("hana@yearupUnited.org", provider.getInfo());

        // check full getInfo() output
        assertEquals(
                "Provider : Yearup United | Contact :  hana@yearupUnited.org", info );
    }
}