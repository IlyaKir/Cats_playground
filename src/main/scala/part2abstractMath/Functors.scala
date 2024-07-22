package part2abstractMath

import scala.util.Try

object Functors extends App {
  val aModifiedList = List(1,2,3).map(_ + 1) // List(2,3,4)
  val aModifiedOption = Option(2).map(_ + 1) // Some(3)
  val aModifiedTry = Try(42).map(_ + 1) // Success(43)

  // simplified definition
  trait MyFunctor[F[_]] {
    def map[A, B](initialValue: F[A])(f: A => B): F[B]
  }

  // Cats Functor
  import cats.Functor
  import cats.instances.list._ // includes Functor[List]
  import cats.instances.option._ // includes Functor[Option]
  import cats.instances.try_._ // includes Functor[Try]
  val incrementedList = Functor[List].map(List(1,2,3))(_ + 1) // List(2,3,4)
  val incrementedOption = Functor[Option].map(Option(2))(_ + 1) // Some(3)
  val incrementedTry = Functor[Try].map(Try(42))(_ + 1) // Success(43)

  // generalizing an API
  def do10xList(list: List[Int]): List[Int] = list.map(_ * 10)
  def do10xOption(option: Option[Int]): Option[Int] = option.map(_ * 10)
  def do10xTry(attempt: Try[Int]): Try[Int] = attempt.map(_ * 10)

  // generalize
  def do10x[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] =
    functor.map(container)(_ * 10)

  /**
   * Define your own functor for a binary tree
   * hint: define an object which extends Functor[Tree]
   */
  object Task1 {
    sealed trait Tree[+T]
    object Tree {
      // "smart" constructors
      def leaf[T](value: T): Tree[T] = Leaf(value)
      def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
    }
    case class Leaf[+T](value: T) extends Tree[T]
    case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

    implicit object TreeFunctor extends Functor[Tree] {
      override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = {
        fa match {
          case Leaf(value) => Leaf(f(value))
          case Branch(value, left, right) =>
            Branch(f(value), map(left)(f), map(right)(f))
        }
      }
    }

    val tree = Tree.branch[Int](1, Tree.branch(2, Tree.leaf(4), Tree.leaf(5)), Tree.branch(3, Tree.leaf(6), Tree.leaf(7)))
    //val result = TreeFunctor.map(tree)(_ * 10)
    import cats.syntax.functor._
    val result = tree.map(_ * 10)
  }

  /**
   * Write a shorted do10x method using extension methods
   */
  object Task2 {
    import cats.syntax.functor._
    def do10x[F[_]: Functor](container: F[Int])(): F[Int] = container.map(_ * 10)
  }

  println(Task1.result)
}
