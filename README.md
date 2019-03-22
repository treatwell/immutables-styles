# Treatwell's pre-made styles for [Immutables](https://immutables.github.io/)

[![Build Status](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/badge/icon)](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/)

Code quality thingy: <insert badge>

FOSS@Treatwell: <insert badge to some promotionnal job posting page because we might as well :)>

## Meet _Treatwell_'s pre-made [Immutables](https://immutables.github.io/) `@Style`s!

Data immutability inside code offers many a valuable advantage. Alas, sometimes it is still difficult to manage the jump from
theory to practical usage.

For that goal, at _Treatwell_, we are huge fans of the [Immutables](https://immutables.github.io/) library, 
which is why we built custom styl stylistic extensions to it so that we can mix it into our our existing 
codebase efficiently which has been a great success so far.

Because sharing is caring, we decided to share these with the wider audience, and hopefully you find as much enjoyment to
using these as we did and still do!

There are many combinations of non-default features that we do enable for better usage quality-of-life, here's a few:
- A wider variety of naming strategies to match the intended meaning of your data classes. Remembrance of the fact that
["There are only two hard things in Computer Science: cache invalidation and naming things."](https://martinfowler.com/bliki/TwoHardThings.html)
- Out-of-the-box support for FasterXML's wildly popular [Jackson](https://github.com/FasterXML) serialization library, 
which is also the de-facto standard Json/XML/Yaml seiralization library in the Java world
- Matching JavaBeans-like `get*` and `is*` accessor property names (instead of only `get*` by default)
- Full support for most proxying frameworks, like Hibernate, by forcing the presence of a private no argument 
constructor in generate classes

You will find [the styles that we came up with over the years here.](src/main/java/com/treatwell/immutables/styles)
Otherwise, here are details about the two most common (and recommended for general use) ones.

## [`@ValueObjectStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)
##### General charasteristics
- Name: `AbstractXyz -> Xyz`
- Accessor names: `get*, is*`

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
    final Something thing = Something.of("Hello, World!"); // or Something.builder().value(...).build();
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
    final Something thing = ImmutableSomething.of("Hello, World!"); // or ImmutableSomething.builder().value(...).build();
}
```


