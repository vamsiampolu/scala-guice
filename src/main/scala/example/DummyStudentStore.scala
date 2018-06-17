package example


object DummyStudentStore extends StudentStore {
  var students = Map[Int, Student]()

  override def load(id:Int): Option[Student] = {
    students get id
  }

  override def exists(id: Int): Boolean = {
    students contains id
  }

  override def save(p: Student): Student = {
    p.id match {
      case Some(id) =>
        students = students + (id -> p)
        p
      case None =>
        val id2 = students.size + 1
         val p2= p.copy(id = Some(id2))
        students = students + (id2 -> p2)
        p2
    }
  }
}
