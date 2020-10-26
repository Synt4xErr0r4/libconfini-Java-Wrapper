build: wrapper.cpp confini.c # gcc -c -I"/usr/lib/jvm/java-14-openjdk/include" -I"/usr/lib/jvm/java-14-openjdk/include/linux" -fpic confini.c
	g++ -c -I"/usr/lib/jvm/java-14-openjdk/include" -I"/usr/lib/jvm/java-14-openjdk/include/linux" -fpic wrapper.cpp
	g++ -shared -o libini.so wrapper.o confini.o
	cp /run/media/thomas/Shared/Git/libconfini-Java-Wrapper/jni/libini.so /run/media/thomas/Shared/Git/libconfini-Java-Wrapper/src/main/resources/libini.so