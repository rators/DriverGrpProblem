import utils.LCS._
import utils._
import utils.Link._
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

  val links = toEdges(nodes)

  connectLinks(links)
  println(links)
  val result = reduceLinks(links)

  val startIndices = sampleInput.map(result.indexOf)
  val isNotSuperSequence = startIndices.exists(_ < 0)

  // Test
  isNotSuperSequence match {
    case true => throw new AssertionError(s"Result: $result is not a super-sequence of $sampleInput")
    case false => println(s"The assembled string is: $result")
  }

}
