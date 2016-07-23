package utils

import scala.util.Random
import utils.LCS._
import utils.Position.{getOverlap, maxString, minString}
import scala.collection.mutable

/**
  * An implementation of a link.
  *
  * @param source
  * The source node.
  * @param destination
  * The destination node if any.
  * @param value
  * The amount of overlap between the strand string values of the start and end nodes.
  * @param id
  * An ID for debugging purposes.
  */
case class Link(source: Node, var destination: Option[Node], var value: Int = -1, id: Int = Random.nextInt(9999)) {
  override def toString = s"[$id] ${source.strand} --> ${destination.getOrElse("_")} By: $value"
}


object Link {
  /**
    * Obtains the end node if any for a buffer of links.
    *
    * @param links
    * An unlinked list of links.
    */
  def connectLinks(links: mutable.Buffer[Link]): Unit = {
    println(s"The links: $links")

    val atLeastHalf = (targetStr: String, overlap: Int) =>
      overlap >= targetStr.length / 2d

    val isHalfChild = (parent: Link, candidate: Link) => {
      val overlapResult = getOverlap(parent.source.strand, candidate.source.strand)
      overlapResult match {
        case overlapData: LCSData =>
          val isHalfOverlap = atLeastHalf(parent.source.strand, overlapData.length)
          val isChild = overlapData.direction == Prefix
          val result = isChild && isHalfOverlap
          if (result) Some(overlapData.length) else None
        case s: StrResult =>

          val parentLarger = maxString(
            parent.source.strand,
            candidate.source.strand
          ) == parent.source.strand
          parentLarger match {
            case true =>
              links -= parent
              None
            case false =>
              links -= candidate
              None
          }
      }

    }

    links.foreach((e) => {
      val matchingNodeOpt = links
        .filterNot(_ == e)
        .collectFirst {
          case r if isHalfChild(e, r).isDefined => (r.source, isHalfChild(e, r).get)
        }

      matchingNodeOpt match {
        case Some(nodeTuple) =>
          e.destination = Some(nodeTuple._1)
          e.value = nodeTuple._2
        case None => //throw new AssertionError(s"No matching node found for: ${e.start}")
      }
    })
  }
}
