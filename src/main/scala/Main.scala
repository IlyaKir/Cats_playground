import cats.Eval

object Main extends App {
  private val meaningOfLife = Eval.later {
    println("Hello, world!")
    42
  }

  println(meaningOfLife.value)
}