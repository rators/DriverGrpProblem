package utils

import scala.util.Random
import utils.LCS._
import utils.Position.{getOverlap, maxString, minString}
import scala.collection.mutable

/**
  * An implementation of a graph edge.
  *
  * @param start
  * The source node.
  * @param end
  * The destination node if any.
  * @param weight
  * The amount of overlap between the strand string values of the start and end nodes.
  * @param id
  * An ID for debugging purposes.
  */
case class Edge(start: Node, var end: Option[Node], var weight: Int = -1, id: Int = Random.nextInt(9999)) {
  override def toString = s"[$id] ${start.strand} --> ${end.getOrElse("_")} By: $weight"
}


object Edge {
  /**
    * Obtains the end node if any for a buffer of edges.
    *
    * @param edges
    * An unlinked list of edges.
    */
  def linkEdges(edges: mutable.Buffer[Edge]): Unit = {
    println(s"The edges: $edges")

    val atLeastHalf = (targetStr: String, overlap: Int) =>
      overlap >= targetStr.length / 2d

    val isHalfChild = (parent: Edge, candidate: Edge) => {
      val overlapResult = getOverlap(parent.start.strand, candidate.start.strand)
      overlapResult match {
        case overlapData: LCSData =>
          val isHalfOverlap = atLeastHalf(parent.start.strand, overlapData.length)
          val isChild = overlapData.direction == Prefix
          val result = isChild && isHalfOverlap
          if (result) Some(overlapData.length) else None
        case s: StrResult =>

          val parentLarger = maxString(
            parent.start.strand,
            candidate.start.strand
          ) == parent.start.strand
          parentLarger match {
            case true =>
              edges -= parent
              None
            case false =>
              edges -= candidate
              None
          }
      }

    }

    edges.foreach((e) => {
      val matchingNodeOpt = edges
        .filterNot(_ == e)
        .collectFirst {
          case r if isHalfChild(e, r).isDefined => (r.start, isHalfChild(e, r).get)
        }

      matchingNodeOpt match {
        case Some(nodeTuple) =>
          e.end = Some(nodeTuple._1)
          e.weight = nodeTuple._2
        case None => //throw new AssertionError(s"No matching node found for: ${e.start}")
      }
    })
  }
}
