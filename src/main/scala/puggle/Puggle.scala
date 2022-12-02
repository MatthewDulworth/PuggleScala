package puggle

import scala.annotation.tailrec
import scala.io.Source
import scala.io.StdIn.readLine

/**
 * Interpreter for my Puggle mini-language.
 * Based on the implementation of Lox language given in https://craftinginterpreters.com
 */
object Puggle {

  val INVOKE_ERROR = 64
  val PUGGLE_ERROR = 65
  val EXIT_CMD = "exit"

  /**
   * Usage: `puggle [script]` to run a puggle script or `puggle` to run the puggle REPL.
   * @param args Path to script or nothing.
   */
  def main(args: Array[String]): Unit =
    if args.length > 1 then
      println("Usage: puggle [script]")
      System.exit(INVOKE_ERROR)
    else if args.length == 1 then
      println(s"Running File ${args(0)}...")
      runFile(args(0))
    else
      println("Starting REPL...")
      runREPL()

  /**
   * Runs a puggle script.
   * @param path Path to the script.
   */
  private def runFile(path: String): Unit =
    val file = Source.fromFile(path)
    try run(file.getLines.mkString("\n")) finally file.close()
    if !Error.noErrors then System.exit(PUGGLE_ERROR)


  /**
   * Runs the puggle REPL.
   */
  @tailrec private def runREPL(): Unit =
    print("> ")
    readLine() match
      case line: String if line != EXIT_CMD =>
        run(line)
        Error.clear()
        runREPL()
      case _ => println("Exiting REPL")


  /**
   * Runs a string of puggle source code.
   * Handles Errors
   * @param src Source code to run.
   */
  private def run(src: String): Unit =
    println("Scanning...")
    val tokens = Scanner(src).scan()
    println(tokens)
}
