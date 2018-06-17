package example

trait StudentStore {
  def exists(id: Int): Boolean
  def load(id: Int): Option[Student]
  def save(p:Student): Student
}
