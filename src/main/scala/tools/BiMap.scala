package tools

/**
 * Adapted from https://stackoverflow.com/a/9851520/9396808
 */
object BiMap {
  private[BiMap] trait MethodDistinctor
  implicit object MethodDistinctor extends MethodDistinctor
}

case class BiMap[X, Y](map: Map[X, Y]) {
  def this(tuples: (X,Y)*) = this(tuples.toMap)
  private val reverseMap = map map (_.swap)
  require(map.size == reverseMap.size, "no 1 to 1 relation")

  def apply(x: X): Option[Y] =
    try Option(map(x)) catch case _ => None

  def apply(y: Y)(implicit d: BiMap.MethodDistinctor): Option[X] =
    try Option(reverseMap(y)) catch case _ => None


  val domain = map.keys
  val codomain = reverseMap.keys
}