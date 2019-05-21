# _Treatwell_'s [Immutables](https://immutables.github.io/) styles

![Maven Central](https://img.shields.io/maven-central/v/com.treatwell/immutables-styles.svg?style=for-the-badge)
![License](https://img.shields.io/github/license/treatwell/immutables-styles.svg?style=for-the-badge)

[![Open source at Treatwell](https://cdn1.treatwell.net/images/view/v2.i1756348.w200.h50.x4965194E.png)](https://treatwell.com/tech)

## Meet _Treatwell_'s pre-made [Immutables](https://immutables.github.io/) `@Style`s!

Usage of immutable data structures for simple objects is generally not a controversial topic. Unfortunately, implementation of this idea in real life code 
often proves to be quite difficult to properly manage, especially considering the tradeoff between boilerplate, maintainability and usability.

This is why, at _Treatwell_, we have become huge fans of the [Immutables](https://immutables.github.io/) library over the years. 
This brought us to building our own custom `@Style` extensions on top of it, so that it would feel just right to blend it into our our existing 
codebase with as little effort as possible while still covering a broad amount of use cases. This has been a great success so far, and while
not everything is perfect yet, we decided we would share these with a wider audience to make it easier for other to benefit from the lessons we 
learned on our way to generalizing its usage in-house.

Hopefully you find as much enjoyment to using these as we did and still do!

There are many combinations of non-standard configurations that we do use for better usage and QoL. Here's a few to give you an idea:
- A wider variety of naming strategies to match the intended meaning of your data classes. Never forget the fact that
["There are only two hard things in Computer Science: cache invalidation and naming things."](https://martinfowler.com/bliki/TwoHardThings.html)
- Out-of-the-box support for FasterXML's wildly popular [Jackson](https://github.com/FasterXML) serialization library, 
which is also the _de facto_ standard Json/XML/Yaml serialization library in the Java world, especially for Spring (Boot) using companies like us
- Matching JavaBeans-like `get*` and `is*` accessor property names (instead of only `get*` by default) when you need them
- Full support for most proxying/ORM frameworks (think Hibernate & friends), by forcing the presence of a private no argument 
constructor in generate classes
- And a couple other more specific little tunings (protected definition yet public generated class, safe builders...)

You will find [all of the styles that we came up with over the years here.](src/main/java/com/treatwell/immutables/styles)

But we understand that this would be a lot to take in randomly, so **here are the two most important** (and recommended for general use) ones to start with:

## [`@ValueObjectStyle`](src/main/java/com/treatwell/immutables/styles/ValueObjectStyle.java)

##### General characteristics
- Client API:
  - **Naming strategy:** `AbstractXyz -> Xyz`
  - **Visibility:** The generated class is always *`public`*, which allows for keeping the abstract one package-private
- Internal implementation:
  - **Strict builders:** Copy and pasting is a dangerous habit, and it is always better to be safe than sorry. Which is why you
  cannot set a builder's value multiple times in a row (for this niche use case, there is [`@NonStrictValueObjectStyle`](src/main/java/com/treatwell/immutables/styles/NonStrictValueObjectStyle.java))
  - **`Optional` and `null`:** Setting `null` as value for an `Optional` field will map it to `Optional#empty`
  (basically like `Optional#ofNullable`) does, as while 
  - **Proxying frameworks compatibility** (Hibernate etc.): Supported via generated private no-arg constructor
- **Serializable:** out of the box with Jackson
  - Leaves serialized field name inference to Jackson rather than using Immutables' inference system

##### Sample abstract annotated class:
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

##### Example of simplified usage in a Spring Web REST controller:
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

### [`@DefaultStyle`](src/main/java/com/treatwell/immutables/styles/DefaultStyle.java)

##### General characteristics
- Client API:
  - **(default) naming strategy:** `Xyz -> ImmutableXyz`
  - **Visibility:** The generated class has the same visibility as the abstract one
- Internal implementation:
  - **Strict builders:** Copy and pasting is a dangerous habit, and it is always better to be safe than sorry. Which is why you
  cannot set a builder's value multiple times in a row
  - **`Optional` and `null`:** Setting `null` as value for an `Optional` field will map it to `Optional#empty`
  (basically like `Optional#ofNullable`) does, as while 
  - **Proxying frameworks compatibility** (Hibernate etc.): Supported via generated private no-arg constructor
- **Serializable:** out of the box with Jackson
  - Leaves serialized field name inference to Jackson rather than using Immutables' inference system

##### Sample abstract annotated class:
```java
@Immutable
@DefaultStyle
@JsonSerialize(as = ImmutableCount.class)   // because Jackson will only see the abstract type instead of the generated one,
@JsonDeserialize(as = ImmutableCount.class) // it needs a little bit of extra help when handling the abstract type directly
public interface Count {                    // N.B.: If you always use only the generated type, this is unnecessary, but then
                                            // @ValueObjectStyle seems more appropriate
    @Parameter
    int getCount();
    
    @Parameter
    Instant getLastIncrementTime();
    
}
```

##### Example of simplified usage in a Spring Web REST controller:
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

This is a design choice to be made so there's no silver bullet answer to begin with, but a couple of things will often motivate 
the choice to fall upon one or the other:

The main difference between these two relates to whether you want to manipulate:
- the concrete (generated) class (handled better with `@ValueObjectStyle`) and be mostly blind to the abstract (annotated) one, 
- or the contrary (and handled better with `@DefaultStyle`).

Now the choice between these two approaches also relates to your concerns balance between *serialization*, *visibility* and *inheritance*

If serialization is the major concern, `@ValueObjectStyle` will be the most convenient:
1. The naming strategy for it (`AbstractXyz -> Xyz`) makes it much cleaner to use the generated class
2. This generated class is directly (de)serializable without supplementary code/effort as it is the generated one
  - on the other hand, (de)serializing the abstract class, which common with `@DefaultStyle` requires adding `@Json{S, Des}erialize(as = ...)` to it

If on the other hand, you have a deep hierarchy, it is much easier to manage it with `@DefaultStyle` and its
interface-based usage (not to mention using interfaces is always a pleasant advantage as composition is far easier to manage than inheritance):
1. The naming strategy (`Xyz -> ImmutableXyz`) is unwieldy, but does not matter a lot as we will mostly be
using the annotated one (i.e. larger instance consumer-to-producer ratio).
2. Despite the annotated class not being serializable out of the box, you can work around it by using the 
generated one (which *is* (de)serializable in this case) as parameters and return types in controllers.

A last point is that `@ValueObjectStyle` allows keeping the abstract class package-private which could be useful to
make sure to avoid involuntary usage/inheritance of the abstract one.

To wrap this up, `@ValueObjectStyle` should be mostly sufficient in almost all cases that do not require support for
complex class hierarchies.
