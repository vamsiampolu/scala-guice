package example

trait Registrar {
  def checkStudentStatus(id: Int): Boolean
  def registerStudent(name: String, credits: Int): Student
}
