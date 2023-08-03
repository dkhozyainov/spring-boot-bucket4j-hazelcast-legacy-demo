FROM hazelcast/hazelcast:3.12.6

ENV BUCKET4J_VERSION 7.6.0

ENV HZ_LIB $HZ_HOME/lib
RUN mkdir -p $HZ_LIB
WORKDIR $HZ_LIB

# Download jcache and bucket4j jars from maven repo.
ADD https://repo1.maven.org/maven2/com/github/vladimir-bukhtoyarov/bucket4j-core/$BUCKET4J_VERSION/bucket4j-core-$BUCKET4J_VERSION.jar ${HZ_HOME}
ADD https://repo1.maven.org/maven2/com/github/vladimir-bukhtoyarov/bucket4j-jcache/$BUCKET4J_VERSION/bucket4j-jcache-$BUCKET4J_VERSION.jar ${HZ_HOME}
ADD https://repo1.maven.org/maven2/com/github/vladimir-bukhtoyarov/bucket4j-hazelcast-3/$BUCKET4J_VERSION/bucket4j-hazelcast-3-$BUCKET4J_VERSION.jar ${HZ_HOME}

#Original server.sh expected all eternal libs to be in a folder set by the CLASSPATH env variable. This is misleading and captured here: https://github.com/hazelcast/hazelcast-docker/issues/45
ENV CLASSPATH $HZ_LIB

WORKDIR $HZ_HOME
RUN chmod a+r ${HZ_HOME}/bucket4j-*.jar