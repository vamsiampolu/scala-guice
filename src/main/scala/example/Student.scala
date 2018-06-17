package example

case class Student(
  id: Option[Int] = None,
  name: String,
  credits: Int = 0,
  registered: Option[Boolean] = None,
  major: Option[String] = None,
  year: Option[Int] = None
)
