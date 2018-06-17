package example

import com.google.inject.AbstractModule

class SimpleModule extends AbstractModule {
  override def configure() = {
    bind(classOf[StudentStore]).toInstance(DummyStudentStore)

    bind(classOf[Registrar]).toInstance(LeniantRegistrar)
  }
}

