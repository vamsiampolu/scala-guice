This is a scala implementation of [the tutorial here](https://discursive.com/2011/01/26/creating-a-simple-google-guice-application/)

There are some obvious differences that I have listed down on paper that I will copy here:

1. ADTs and consequently scala case classes are stricter about

  a) omitting parameters
  b) immutability

To solve the first issue we mark any properties that we want to eventually specify as an `Option[T] where T stands for the type we are going to use and additionally provide a default value of `None`. For a property that is not Optional but might be provided later, I use a default value.

Here we play fast and loose with the rules instead of defining our domain model correctly.

To solve the second problem, we use the `copy` method on the case class instance and create a new case class instead because we shouldn't just use a setter and mutate the existing instance.

2. Singletons in Scala:

In the tutorial above, the author can just mark something as a Singletonby using a jsr-330 annotation left to be interpreted by the IoC container. However, singletons are built into scala and can be defined without the annotation.

As per the [blog post here](http://michaelpnash.github.io/guice-up-your-scala/) you can bind a trait (which acts as the interface) to an instance which is the `object` that was  defined in the module.

2) @Inject() and not an @Inject

Just syntax, nothing much to say here

3) Property injection instead of constructor injection

Also in the scala guice blog post I linked above, the author suggested using a mutable variable and providing an @Inject annotation to mark it as the point of injection for Guice 

This approach is a bit hard to digest but it is better than nothing.

**SUMMARY**

There are many differences between scala and java, some of the additional features in Scala might offer exciting possibilities, will look into them as well.

