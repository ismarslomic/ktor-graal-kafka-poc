# Kafka Streams microservice using Ktor and GraalVM

> PoC of consuming and producing message to Kafka Broker with use of Apache Kafka Streams API, built and run with
> use of Ktor and GraalVM.

## Limitations & Known issues

- Currently, Ktor Server applications that want to leverage GraalVM have to use CIO (Coroutine-based I/O engine) as
  the [application engine](https://ktor.io/docs/server-engines.html).
- The automatic loading of configuration file (`application.yaml`), for configuring the server, does not work
  with GraalVM native image. See [KTOR-3453](https://youtrack.jetbrains.com/issue/KTOR-3453)
  and [KTOR-6069](https://youtrack.jetbrains.com/issue/KTOR-6069).
- Micrometer Prometheus (v1.13.0) generates configuration log warning at startup,
  see [KTOR-7035](https://youtrack.jetbrains.com/issue/KTOR-7035).

## Build

### Install GraalVM (for Mac)

You can follow [official installation](https://www.graalvm.org/latest/docs/getting-started/macos/) steps or by using
Homebrew and [jenv](https://www.jenv.be) bellow.

```bash
$ brew install --cask graalvm-jdk
$ jenv add 22.0.2-graal /Library/Java/JavaVirtualMachines/graalvm-22.jdk/Contents/Home
```

To check whether the installation was successful, run

```bash
$ java -version
java version "22.0.2" 2024-07-16
Java(TM) SE Runtime Environment Oracle GraalVM 22.0.2+9.1 (build 22.0.2+9-jvmci-b01)
Java HotSpot(TM) 64-Bit Server VM Oracle GraalVM 22.0.2+9.1 (build 22.0.2+9-jvmci-b01, mixed mode, sharing)
```

Note! jenv will report a deprecation warning, but you can ignore it.

### Build and run native GraalVM image

```bash
$ ./gradlew nativeCompile
```

Run the executable file produced by the build step

```bash
$ ./build/native/nativeCompile/graalvm-server
```

### Build and run tests

To build an executable file for tests:

```bash
$ ./gradlew nativeTestCompile

```

Run the executable file produced by the build step

```bash
$ ./build/native/nativeTestCompile/graalvm-test-server
```

## Metrics

```bash
curl http://localhost:8080/metrics

```
