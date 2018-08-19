package study.provider;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ObjectProviderExample.class)
class ObjectProviderExampleTest {

    @Autowired
    private Foo foo;

    @Test
    void test() {

        final UUID fooId = foo.getId();
        assertNotNull(fooId);

        final UUID bar1Id1 = foo.getBar1Id();
        final UUID bar1Id2 = foo.getBar1Id();
        final UUID bar2Id1 = foo.getBar2Id();
        final UUID bar2Id2 = foo.getBar2Id();
        assertNotEquals(bar1Id1, bar1Id2);
        assertEquals(bar2Id1, bar2Id2);
        assertNotEquals(bar1Id1, bar2Id1);

        final UUID bar1BazId1 = foo.getBar1BazId();
        final UUID bar1BazId2 = foo.getBar1BazId();
        final UUID bar2BazId1 = foo.getBar2BazId();
        final UUID bar2BazId2 = foo.getBar2BazId();
        assertEquals(bar1BazId1, bar1BazId2);
        assertEquals(bar2BazId1, bar2BazId2);
        assertEquals(bar1BazId1, bar2BazId1);
    }
}
