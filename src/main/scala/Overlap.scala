import utils.LCS._
import utils._
import utils.Edge._
import utils.Node.toEdges

import scala.collection.mutable

object Overlap extends App {

  val sampleInput = List(
    "GCCGGAATAC",
    "CCTGCCGGAA",
    "ATTAGACCTG",
    "AGACCTGCCG"
  )

  val nodes: mutable.Buffer[Node] = sampleInput.map((inputFrag) => Node(inputFrag, None)).toBuffer

  val edges = toEdges(nodes)

  linkEdges(edges)
  println(edges)
  val result = reduceEdges(edges)

  val startIndices = sampleInput.map(result.indexOf)
  val isNotSuperSequence = startIndices.exists(_ < 0)

  // Test
  isNotSuperSequence match {
    case true => throw new AssertionError(s"Result: $result is not a super-sequence of $sampleInput")
    case false => println(s"The assembled string is: $result")
  }

}
