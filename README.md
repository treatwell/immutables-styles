# _Treatwell_'s [Immutables](https://immutables.github.io/) style extensions

[![Open source at Treatwell](https://img.shields.io/badge/open%20source%20%40-treatwell-3ba150.svg)](https://treatwell.com/tech)

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
which is also the _de facto_ standard Json/XML/Yaml serialization library in the Java world
- Matching JavaBeans-like `get*` and `is*` accessor property names (instead of only `get*` by default)
- Full support for most proxying frameworks, like Hibernate, by forcing the presence of a private no argument 
constructor in generate classes

You will find [the styles that we came up with over the years here.](src/main/java/com/treatwell/immutables/styles)
Otherwise, here are details about the two most common (and recommended for general use) ones.

## [`@ValueObjectStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)

##### General characteristics
- Client API:
  - **Naming strategy:** `AbstractXyz -> Xyz`
  - **Visibility:** The generated class is always *`public`*, which allows for keeping the abstract one package-private
- Internal implementation:
  - **Strict builders:** Copy and pasting is a dangerous habit, but we are always better safe than sorry. Which is why you
  cannot set a builder's value multiple times in a row
  - **`Optional` and `null`:** Setting `null` as value for an `Optional` field will alias it to `Optional#empty`
  (like `Optional#ofNullable`) does
  - **Proxying frameworks compatibility** (Hibernate etc.): Supported via generated private no-arg constructor
- **Serializable:** out of the box with Jackson
  - Leaves serialized field name inference to Jackson rather than using Immutables' inference system

##### Sample annotated class:
```java
@Immutable
@ValueObjectStyle
/* package-private */ abstract class AbstractPerson {
    @Parameter
    public abstract String getName();
    
    @Parameter
    public abstract Instant creationTime();
}
```

##### Example of simplified usage in a controller managed with Spring Web:
```java
@RestConstroller
public class PersonController {
    
    private final PersonDao personDao;
    
    @PostMapping
    public Person createPersonWithName(@RequestParameter("name") String name) {
        Person newPerson = Person.of(name, Instant.now());
                     // or Person.builder().name(name).creationTime(Instant.now()).build();
        
        personDao.savePerson(newPerson);
        return newPerson; // automatically serialized by Jackson
    }
}
```

### [`@DefaultStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)

##### Sample annotated class:
```java
@Immutable
@DefaultStyle
@JsonSerialize(as = ImmutableCount.class)   // because jackson will only see the abstract type
@JsonDeserialize(as = ImmutableCount.class) // instead of the generated one, it needs extra help
public interface Count {                    // N.B.: not needed if exclusively using ImmutableCount
    
    @Parameter
    int getCount();
    
    @Parameter
    Instant getLastIncrementTime();
    
}
```

##### Example of simplified usage in a rest controller:
```java
@RestController
public class MyCountService {

    private final AtomicReference<Count> currentCount = new AtomicReference<>(ImmutableCount.of(0, LocalDateTime.now()));

    @PostMapping
    public void incrementCount(Count count) {
        Count oldCount = currentCount.get();
        currentCount.set(ImmutableCount.of(count.getCount() + oldCount.getCount(), count.getLastIncrementTime()));
    }

    @GetMapping
    public Count getCurrent() {
        return currentCount.get();
    }

}
```

## When to use which?
The main difference is relating to whether you want to manipulate the concrete (generated) class 
(i.e. `@ValueObjectStyle`) and be mostly blind to the abstract (annotated) one, or the contrary (i.e. `@DefaultStyle`).

There are various reason for choosing either, but it will mostly boil down to which of *serialization* and/or *inheritance*
is the bigger concern for your specific case.

If serialization is the major concern, `@ValueObjectStyle` will be the most convenient:
1. The naming strategy for it (`AbstractXyz -> Xyz`) makes it much cleaner to use the generated class
2. This generated class is directly (de)serializable without supplementary code/effort as it is the generated one
  - on the other hand, (de)serializing the abstract class requires adding `@Json{S, Des}erialize(as = ...)` to it

If on the other hand, you have a deep hierarchy, it is much easier to manage it with `@DefaultStyle` and its
interface-based annotated classes:
1. The naming strategy (`Xyz -> ImmutableXyz`) is unwieldy, but does not matter a lot as we will mostly be
using the annotated one.
2. Despite the annotated class not being serializable out of the box, you can work around it by using the 
generated one (which *is* (de)serializable in this case) as parameters and return types in controllers.

A last point is that `@ValueObjectStyle` allows keeping the abstract class package-private which could be useful to
make sure to avoid involuntary usage/inheritance of the abstract one.

To wrap this up, `@ValueObjectStyle` should be mostly sufficient in almost all cases that do not require support for
complex class hierarchies.

