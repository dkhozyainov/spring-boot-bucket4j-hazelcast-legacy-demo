Before start app:
docker run -e JAVA_OPTS="-Dhazelcast.local.publicAddress=192.168.1.227:5701 -Dhazelcast.rest.enabled=true" -itd
-p 5701:5701 hazelcast/hazelcast:3.12.6
