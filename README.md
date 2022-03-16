# [Origins](https://github.com/MoodMinds/origins)' reactive interfaces to [Project Reactor](https://projectreactor.io) adapters

Wrappers around the [Reactor](https://projectreactor.io)'s `Publisher` and `Subscriber` adapting to
[Origins](https://github.com/MoodMinds/origins)' `Publishable` and `Subscriber` and vice versa.

## Usage

```java
import org.moodminds.lang.Publishable;
import org.moodminds.reactor.adapter.PublishableAdapter;
import org.moodminds.reactor.adapter.ReactorPublisherAdapter;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;

import static org.moodminds.lang.Publishable.publishable;

class Sample {

    void sample() {
        Publishable<String, ?> originsPublishable;
        CorePublisher<String> reactorPublisher = Flux.just("a");

        originsPublishable = PublishableAdapter.fromReactive(reactorPublisher);
        reactorPublisher = ReactorPublisherAdapter.toReactor(originsPublishable);
    }
}
```

## Maven configuration

Artifacts can be found on [Maven Central](https://search.maven.org/) after publication.

```xml
<dependency>
    <groupId>org.moodminds</groupId>
    <artifactId>origins-reactor</artifactId>
    <version>${version}</version>
</dependency>
```

## Building from Source

You may need to build from source to use **Origins Reactor** (until it is in Maven Central) with Maven and JDK 9 at least.

## License
This project is going to be released under version 2.0 of the [Apache License][l].

[l]: https://www.apache.org/licenses/LICENSE-2.0