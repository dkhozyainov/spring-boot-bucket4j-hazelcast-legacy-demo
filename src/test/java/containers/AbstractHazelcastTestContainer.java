package containers;

public abstract class AbstractHazelcastTestContainer {

    static final HazelcastTestContainer HAZELCAST_TEST_CONTAINER;

    static  {
        HAZELCAST_TEST_CONTAINER = HazelcastTestContainer.getInstance();
        HAZELCAST_TEST_CONTAINER.start();
    }
    protected static void containerStart() {
        HAZELCAST_TEST_CONTAINER.start();
    }

    protected void containerStop() {
        HAZELCAST_TEST_CONTAINER.stop();
    }

    protected static void containerClose() {
        HAZELCAST_TEST_CONTAINER.close();
    }
}
