# Treatwell's pre-made styles for [Immutables](https://immutables.github.io/)

[![Build Status](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/badge/icon)](http://jenkins.twtools.io/job/Open-source/job/Immutables%20Styles%20-%20OSS/6/)

Code quality thingy: <insert badge>

FOSS@Treatwell: <insert badge to some promotionnal job posting page because we might as well :)>

## Make your immutable classes _glow_ with some of our `@Style`s!

With Immutables, you can generate safe immutable implementations for your instances, rather than writing the POJO boilerplate yourself.

While it is a wonderful library and offers quite a lot of customization, sometimes the defaults are not exactly as we often might use them:
- Requires manual annotation to generate hinted subclasses for FasterXML's wildly popular [Jackson](https://github.com/FasterXML) serialization library (Json, XML, Yaml,...)
- Does offer a one-size-fits all sensible default that might not be suited to different specific object usages (DTOs, Events, Bean-conforming, and so on)

This is why we have come up with pre-made specialized styles usable in various more or less specific cases depending on what you want to achieve.

### `@ValueObjectStyle`

The simplest type of POJOs. From an abstract class named `AbstractXYZ` it will generate and immutable instance named `XYZ` (like the default immutable setup),
but will also automatically make it so that `XYZ` subclass is ready for serialization (and deserialization) using _Jackson_.
