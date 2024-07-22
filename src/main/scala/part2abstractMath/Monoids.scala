package part2abstractMath

import cats.syntax.semigroup._ // |+| method
import cats.instances.int._

object Monoids extends App {
  val numbers = (1 to 1000).toList
  val sumLeft = numbers.foldLeft(0)(_ |+| _)
  val sumRight = numbers.foldRight(0)(_ |+| _)

  // define a general API
  //  def combineFold[T](list: List[T])(implicit semigroup: Semigroup[T]): T =
  //    list.foldLeft(/* ZERO */)(_ |+| _)

  // MONOIDS
  import cats.Monoid
  val combineInt = Monoid[Int].combine(1, 2) // 3
  val emptyInt = Monoid[Int].empty // 0

  val combineString = Monoid[String].combine("I understand ", "monoids")
  val emptyString = Monoid[String].empty // ""

  val emptyOption = Monoid[Option[Int]].empty // None
  val combineOption = Monoid[Option[Int]].combine(Option(1), Option.empty[Int]) // Some(1)
  val combineOption2 = Monoid[Option[Int]].combine(Option(2), Option(5)) // Some(7)

  // extension methods for Monoids - |+|
  // import cats.syntax.monoid._ // either this one or cats.syntax.semigroup._
  val combinedOptionFancy = Option(3) |+| Option(7)

  /**
   * Implement a combineFold
   */
  object Task1 {
    def combineFold[T](list: List[T])(implicit monoid: Monoid[T]): T =
      list.foldLeft(monoid.empty)(_ |+| _)
  }

  /**
   * Combine a list of phonebooks as Maps[String, Int]
   */
  object Task2 {
    val phonebooks = List(
      Map(
        "Alice" -> 235,
        "Bob" -> 647
      ),
      Map(
        "Charlie" -> 372,
        "Daniel" -> 889
      ),
      Map(
        "Tina" -> 123
      )
    )
    import cats.instances.map._
    val result = Task1.combineFold(phonebooks)
  }

  /**
   * Create shopping cart and online stores with Monoids
   * hint: define your monoid - Monoid.instance
   * hint #2: use combineByFold
   */
  object Task3 {
    case class ShoppingCart(items: List[String], total: Double)
    implicit val shoppingCartMonoid: Monoid[ShoppingCart] = Monoid.instance[ShoppingCart](
      emptyValue = ShoppingCart(List.empty, 0),
      cmb = (left, right) => ShoppingCart(left.items ++ right.items, left.total + right.total)
    )

    val sc1 = ShoppingCart(List("item1, item2"), 10.0)
    val sc2 = ShoppingCart(List("item3", "item4"), 32.0)
    val result = Task1.combineFold(List(sc1, sc2))
  }


  println(s"Task2 result: ${Task2.result}")
  println(s"Task3 result: ${Task3.result}")
}
