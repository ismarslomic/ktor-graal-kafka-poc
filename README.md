# Kafka Streams microservice using Ktor and GraalVM

> PoC of consuming and producing message to Kafka Broker with use of Apache Kafka Streams API, built and run with
> use of Ktor and GraalVM.

## Prerequisites

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

## Metrics
```bash
curl http://localhost:8080/metrics

```
