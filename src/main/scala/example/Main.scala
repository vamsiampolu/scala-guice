package example

import com.google.inject.{Injector, Guice}

object Main extends App {
  val injector:Injector = Guice.createInjector(new SimpleModule)
  val store:StudentStore = injector.getInstance(classOf[StudentStore])
  val registrar:Registrar = injector.getInstance(classOf[Registrar])

  val s:Student = Student(
    id = Some(1),
    name = "John Doe",
    credits = 12
  )
  store.save(s)

  val status = registrar.checkStudentStatus(s.id.getOrElse(0))


  if (status) {
    println("This student meets the criteria")
  } else {
    println("This student doesn't meet the criteria")
  }
}
