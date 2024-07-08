import cats.Semigroup
import cats.instances.int._
import cats.instances.string._
import cats.instances.option._

// COMBINE elements of the same type
object Semigroups extends App {
  val naturalIntSemigroup = Semigroup[Int]
  val intCombination = naturalIntSemigroup.combine(2, 46) // addition

  val naturalStringSemigroup = Semigroup[String]
  val stringCombination = naturalStringSemigroup.combine("I love ", "Cats") // concatenation

  def reduceInts(list: List[Int]): Int = list.reduce(naturalIntSemigroup.combine)
  def reduceStrings(list: List[String]): String = list.reduce(naturalStringSemigroup.combine)

  def reduce[T](list: List[T])(implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)





  val numbers: List[Int] = (1 to 10).toList
  println(reduceInts(numbers))

  val strings: List[String] = List("I'm ", "starting ", "to ", "like ", "semigroups")
  println(reduceStrings(strings))

  val options: List[Option[Int]] = numbers.map(Option.apply)
  println(reduce(options))
  println(reduce(strings.map(Option.apply)))



  /**
   * Add support a new type
   */
  private object Task1 {
    case class Expense(id: Long, amount: Double)
    implicit val expenseSemigroup: Semigroup[Expense] = Semigroup.instance {
      (l, r) => Expense(Math.max(l.id, r.id), l.amount + r.amount)
    }
    val exp0 = Expense(0, 2)
    val exp1 = Expense(1, 40)
    lazy val result: Expense = reduce(List(exp0, exp1))
  }
  println(Task1.result)

  // Extension methods for semigroups |+|
  import cats.syntax.semigroup._
  val intSum = 2 |+| 3
  val expenseSum = Task1.exp0 |+| Task1.exp1



  /**
   * implement `reduce` using |+|
   */
  private object Task2 {
    def reduce[T: Semigroup](list: List[T]): T =
      list.reduce(_ |+| _)
  }
}
