object TypeClasses extends App {
  case class Person(name: String, age: Int)

  // part1 - type class definition
  trait JSONSerializer[T] {
    def toJson(value: T): String
  }

  // part2 - creating implicit type class instances
  implicit object StringSerializer extends JSONSerializer[String] {
    override def toJson(value: String): String = "\"" + value + "\""
  }
  implicit object IntSerializer extends JSONSerializer[Int] {
    override def toJson(value: Int): String = value.toString
  }
  implicit object PersonSerializer extends JSONSerializer[Person] {
    override def toJson(value: Person): String =
      s"""
         |{ "name": ${value.name}, "age": ${value.age} }
         |""".stripMargin.trim
  }

  // part3 - offer some sort of API
  def convertListToJson[T](list: List[T])(implicit serializer: JSONSerializer[T]) =
    list.map(serializer.toJson).mkString(start = "[", sep = ",", end = "]")


  println(convertListToJson(List(
    Person("Alice", 23),
    Person("Xavier", 45)
  )))

  // part4 - extending the existing types via extension methods
  object JSONSyntax {
    implicit class JSONSerializable[T](value: T)(implicit serializer: JSONSerializer[T]) {
      def toJson: String = serializer.toJson(value)
    }
  }

  val bob = Person("Bob", 35)
  import JSONSyntax._
  println(bob.toJson)
}
