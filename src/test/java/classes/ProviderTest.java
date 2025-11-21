package classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void getInfo() {

        // arrange
        Provider provider = new Provider("Yearup United", "hana@yearupUnited.org");

        // act
        String info = provider.getInfo();

        // assert provider name
        assertEquals("Yearup United", provider.getName());

        // assert contact info
        assertEquals("hana@yearupUnited.org", provider.getContactInfo());

        // assert full info output
        assertEquals(
                "Provider: Yearup United |Contact: hana@yearupUnited.org",
                info
        );



    }
}