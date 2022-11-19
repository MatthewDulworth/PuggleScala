package puggle

/**
 * Author: Mathias Braun and Peter Schmidt
 * Source: https://stackoverflow.com/a/9851520/9396808
 */
object BiMap {
  private[BiMap] trait MethodDistinctor
  implicit object MethodDistinctor extends MethodDistinctor
}

case class BiMap[X, Y](map: Map[X, Y]) {
  def this(tuples: (X,Y)*) = this(tuples.toMap)
  private val reverseMap = map map (_.swap)
  require(map.size == reverseMap.size, "no 1 to 1 relation")
  def apply(x: X): Y = map(x)
  def apply(y: Y)(implicit d: BiMap.MethodDistinctor): X = reverseMap(y)
  val domain = map.keys
  val codomain = reverseMap.keys
}