package example

import javax.inject.{Inject}

object LeniantRegistrar extends Registrar {
    @Inject() var studentStore: StudentStore = null
  override def checkStudentStatus(id: Int): Boolean = {
    studentStore
      .load(id)
      .map(_.credits >= 10.0)
      .fold(false)(x => x)

  }
  override def registerStudent(name: String, credits: Int): Student = {
    val s = Student(
      name  = name,
      credits = credits
    )
    studentStore.save(s)
  }
}
