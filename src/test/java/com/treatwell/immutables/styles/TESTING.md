# Immutable-styles testing strategy

Since annotation processing is already hard to do, and even harder to do when you are writing
an extension to an already-complex 3rd-party processor, we have accepted the impossibility of
perfect coverage.

That said, with the goal of providing a best-effort alternative, here is the way we have been
dealing with this so far, to avoid unexpected breaking changes.

# Style feature
A style feature is a feature of a given `@Style` that is part of its contract in the sense that
changing its tuning would break clients.

Those are all extensions of [StyleFeature](features/StyleFeature.java).

One such feature is crafted to behave nicely with the Parameterized JUnit Runner so as to:
- be ran individually 
- using a sample class generated with a given `@Style`
- and checking one specific assumed feature of this style

In cases where fully `class` definition-based testing is not possible and we are testing some
implementation details, we declare the feature as an [ImplementationBehaviorStyleFeature](features/ImplementationBehaviorStyleFeature.java).
- For a textbook example, see [StrictBuilder](features/StrictBuilder.java).

# Style feature test
Once you have your `@Style`'s features implemented as mentionned above, you can simply extend
[StyleFeaturesTest](StyleFeaturesTest.java).

Given a set of features to verify as enabled/working, you just provide instances of these (defined earlier)
and a sample abstract class and its concrete generated class.

The test will then check for the presence of all of these and their coherence given the two classes.

## Miscellaneous
This is not the ideal testing setup but we feel it is a compromise we are happy working with right now, 
and are confident that we can improve it further in the future.

We also are open to any suggestions you might have on improving it.
