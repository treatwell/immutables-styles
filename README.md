# Treatwell's pre-made styles for [Immutables](https://immutables.github.io/)

[![Build Status](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/badge/icon)](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/)

Code quality thingy: <insert badge>

FOSS@Treatwell: <insert badge to some promotionnal job posting page because we might as well :)>

## Make your immutable classes _glow_ with some of our `@Style`s!

With Immutables, you can generate safe immutable implementations for your instances, rather than writing the POJO boilerplate yourself.

While it is a wonderful library and offers quite a lot of customization, sometimes the defaults are not exactly as we often might use them:
- Requires manual annotation to generate hinted subclasses for FasterXML's wildly popular [Jackson](https://github.com/FasterXML) serialization library (Json, XML, Yaml,...)
- Does offer a one-size-fits all sensible default that might not be suited to different specific object usages (DTOs, Events, Bean-conforming, and so on)

This is why we have come up with pre-made specialized styles for various use cases, so you do not have to :)

Following are sample common use-cases:

### [`@ValueObjectStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)

##### Sample annotated class:
```java
@Immutable
@ValueObjectStyle
public abstract class AbstractSomething {
    @Parameter
    public abstract String getValue();
}
```

##### Declaration:
```java
public class MyService {
    final Something thing = Something.of("Hello, World!"); // or long form with Something.builder().value(...).build();
}
```

##### Serialization (with Spring Web relying on Jackson):
```java
@RestConstroller
public class SomethingController {
    @GetMapping
    public Something getSomething(@RequestParameter("value") String value) {
        return Something.of(value);
    }
}
```

### [`@DefaultStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)

##### Sample annotated class:
```java
@Immutable
@DefaultStyle
public interface Something {
    @Parameter
    String getValue();
}
```

##### Sample usage:
```java
public class MyService {
    final Something thing = ImmutableSomething.of("Hello, World!"); // or long form with ImmutableSomething.builder().value(...).build();
}
```


