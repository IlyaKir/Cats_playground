object CatsEq extends App {
  // part1 - TC (type class) import
  import cats.Eq
  // part2 - import TC instance for specific type
  import cats.instances.int._
  // part3 - use TC API
  val intEquality = Eq[Int]
  val aTypeSafeComparison = intEquality.eqv(2, 3)
  //val invalidComparison = intEquality.eqv(2, "two") // compile error

  // part4 - use extension methods
  import cats.syntax.eq._
  val anotherTypeSafeComparison = 2 === 3 // false
  val neqComparison = 2 =!= 3 // true
  //val invalidComparison = 2 === "two" // compile error
  val aListComparison = List(2) === List(3)

  // part5 - create a TC instance for a custom type
  case class ToyCar(model: String, price: Double)
  implicit val toyCar: Eq[ToyCar] = Eq.instance[ToyCar] { (car1, car2) =>
    car1.price == car2.price
  }
  val compareTwoToyCars = ToyCar("CarA", 9.99) === ToyCar("CarB", 9.99)
}
