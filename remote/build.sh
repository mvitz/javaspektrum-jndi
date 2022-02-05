rm -rf ./build
mkdir -p ./build/classes
javac -d ./build/classes de/mvitz/jndi/remote/Hack.java
jar cf ./build/hack.jar -C ./build/classes de/mvitz/jndi/remote/Hack.class
