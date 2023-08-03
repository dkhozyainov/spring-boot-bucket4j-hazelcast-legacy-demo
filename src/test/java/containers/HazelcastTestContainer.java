package containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class HazelcastTestContainer extends GenericContainer{

    private static HazelcastTestContainer container;
    private static final String JAVA_OPTS = "-Dhazelcast.local.publicAddress=192.168.1.227:5701 -Dhazelcast.rest.enabled=true";

    private HazelcastTestContainer() {
//        super(DockerImageName.parse("hazelcast/hazelcast:3.12.6"));
        super(DockerImageName.parse("dkhozyainov/hazelcast_uzum:latest"));
    }

    public static HazelcastTestContainer getInstance() {
        if (container == null) {
            container = new HazelcastTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        container.addFixedExposedPort(5701,5701);
        container.addEnv("JAVA_OPTS", JAVA_OPTS);
        super.start();
    }
}
