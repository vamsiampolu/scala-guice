package example

import org.specs2.Specification

import javax.inject.{Inject, Named, Singleton}
import com.google.inject.{Guice, AbstractModule}
import com.google.inject.name.Names


trait Adder {
  def add(a: Int, b: Int): Int
}

class AddService extends Adder {
  override def add(a: Int, b: Int) = {
    a + b
  }
}

trait MyMathSingletonTrait {
  def addOneAndOne(): Int
}

object MyMathSingletonComponent extends MyMathSingletonTrait {
  @Inject() var service: Adder = null
  def addOneAndOne() = {
    service.add(1,1)
  }
}

class MyMathModule extends AbstractModule {
  def configure() = {
    bind(classOf[Adder]).to(classOf[AddService])
    bind(classOf[MyMathSingletonTrait]).toInstance(MyMathSingletonComponent)
  }
}

class MyMathComponent @Inject()(
    val addService: Adder
  ) {
   def addUsingService(a: Int, b:Int) = {
    addService.add(a, b)
   }
  }

trait MySillyService {
  def silly(name: String): String
}

class MySillyHalServiceImpl extends MySillyService {
  def silly(name: String) = {
    s"Hey silly $name this is Hal"
  }
}

class MySillySallyServiceImpl extends MySillyService {
  def silly(name: String) = {
    s"Hey silly $name this is Sally"
  }
}

class MySillyComponent @Inject() (
    @Named("hal") halService:MySillyService,
    @Named("sally") sallyService: MySillyService
  ) {
  def talkToHal(you: String) = {
    halService.silly(you)
  }

  def talkToSally(you: String) = {
    sallyService.silly(you)
  }
}

class MySillyModule extends AbstractModule {
  override def configure() = {
    bind(classOf[MySillyService])
      .annotatedWith(Names.named("hal"))
      .to(classOf[MySillyHalServiceImpl])

    bind(classOf[MySillyService])
      .annotatedWith(Names.named("sally"))
      .to(classOf[MySillySallyServiceImpl])
  }
}

trait MySingletonInterface1 {
  def sayHello(): String
}

trait MySingletonInterface2 {
  def sayHello(): String
}

object SingletonObject extends MySingletonInterface1 {
  override def sayHello() = {
    "Hello Object"
  }
}

@Singleton
class SingletonClass extends MySingletonInterface2 {
  override def sayHello() = {
    "Hello Singleton Class"
  }
}

class SingletonServiceModule extends AbstractModule {
  override def configure() = {
    bind(classOf[MySingletonInterface1]).toInstance(SingletonObject)
    bind(classOf[MySingletonInterface2]).to(classOf[SingletonClass])
  }
}

class GuiceInScalaSpecification extends Specification {
 def is = s2"""
 When working with Guice in Scala:

  The first step is to create a module
    a) which extends AbstractModule

    b) create a trait as your interface

    c) create a class which is the implementation,
      in the module bind the interface to the implementation

    To verify that a $basicServiceBinding works (and in real applications), create an injector using Guice and get an instance of your service.

    To inject a $serviceIntoAComponent, the Injection annotation needs to be added to the beginning of the class using constructor injection. Also it should be @Inject() instead of @Inject. Keep that in mind.

   To bind $multipleServicesToTheSameInterface with the same module, we can use `Names`  API from guice and annotate each implementation with a different name to annotate them.

  There are a $coupleOfWaysOfDefiningSingletons in Scala, one can use the language and leverage
  the `object` marker or just define a class and use jsr-330 Singleton annotation on it.

  Sometimes, despite the fact that singletons are considered Public Enemy #1, you will have to define
  $componentsThatAreSingleton in which case you need to perform
 """

  def basicServiceBinding = {
    val injector= Guice.createInjector(new MyMathModule)
    val adder = injector.getInstance(classOf[Adder])
    adder.add(1,1) must_== 2
  }

  def serviceIntoAComponent = {
    val injector = Guice.createInjector(new MyMathModule)
    val adder = injector.getInstance(classOf[MyMathComponent])
    adder.addUsingService(1,1) must_== 2
  }

  def multipleServicesToTheSameInterface = {
    val injector = Guice.createInjector(new MySillyModule)
    val comp = injector.getInstance(classOf[MySillyComponent])
    comp.talkToHal("Brianna") must_== "Hey silly Brianna this is Hal"
    comp.talkToSally("Brianna") must_== "Hey silly Brianna this is Sally"
  }

  def coupleOfWaysOfDefiningSingletons = {
    val injector = Guice.createInjector(new SingletonServiceModule)
    val objService = injector.getInstance(classOf[MySingletonInterface1])
    val clsService = injector.getInstance(classOf[MySingletonInterface2])
    objService.sayHello must_== "Hello Object"
    clsService.sayHello must_== "Hello Singleton Class"

    val objService2 = injector.getInstance(classOf[MySingletonInterface1])
    val clsService2 = injector.getInstance(classOf[MySingletonInterface2])

    objService must be(objService2)
    clsService must be(clsService2)
  }

  def componentsThatAreSingleton = {
    val injector = Guice.createInjector(new MyMathModule)
    val comp = injector.getInstance(classOf[MyMathSingletonTrait])
    comp.addOneAndOne() must_== 2
  }
}
