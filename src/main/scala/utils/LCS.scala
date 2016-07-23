package utils

import scala.collection.mutable

/**
  * Longest common substring methods.
  */
object LCS extends App {
  trait UnionRes
  /**
    * LCS Data class for read-ability.
    *
    * @param length
    * The length of the overlap.
    * @param direction
    * The direction of the overlap relative to the first string.
    */
  case class LCSData(length: Int, direction: Position) extends UnionRes
  case class StrResult(s: String) extends UnionRes
  /**
    * A naive solution to the longest common overlapping substring problem.
    * NOTE: both are assumed to be of the same length
    *
    * @param strOne
    * The string assumed to contain an overlapping suffix with strTwo
    * @param strTwo
    * The string assumed to contain an overlapping prefix with strOne
    * @return
    */
  def getLCS(strOne: String, strTwo: String): String = {
    var cutOver = strOne
    var cutUnder = strTwo
    var done = false

    for (i <- 0 until strOne.length if !done) {
      cutOver = cutOver.tail
      cutUnder = cutUnder.dropRight(1)
      done = cutOver == cutUnder
    }

    cutOver
  }



  /**
    * The final portion of the solution. Reduces the edges to the final assembled string value.
    *
    * @param edges
    * The edges associated with the graph.
    * @return
    * The assembled string AKA the shortest common super-sequence.
    */
  def reduceLinks(edges: mutable.Buffer[Link]): String = {

    while (edges.length > 1) {
      val left = edges.head
      left.destination match {
        case Some(endNode) =>
          collapseEdges(left, endNode, edges)
        case None =>
          edges -= left
          edges += left
      }
    }

    val result = edges.head.source.strand
    result
  }

  /**
    * Handles the reduction of one edge.
    *
    * @param currentEdge
    * The edge during the reduce iteration.
    * @param end
    * The destination node for the current edge.
    * @param aggregate
    * The edges associated with the graph.
    */
  def collapseEdges(currentEdge: Link, end: Node, aggregate: mutable.Buffer[Link]) = {
    val strOne = currentEdge.source.strand // the start node (prefix)
    val strTwo = end.strand // the end node (suffix)

    val foldInto = currentEdge
      .destination.get
      .starterEdge.get // get the child node's edge

    aggregate -= currentEdge // remove this edge from the list

    val mergeString = merge(strOne, strTwo, currentEdge.value) // create the shortest common super-string

    currentEdge.source.strand = mergeString // change the string values for the parent edge's end and the child's node's start

    foldInto.source.strand = mergeString
    currentEdge.source.starterEdge = Some(foldInto)
  }


  /**
    * Merges the two strings together into the resulting shortest common super-sequence.
    *
    * @param prefix
    * The string containing the prefix characters.
    * @param suffix
    * The strings containing the suffix characters.
    * @param slice
    * The amount of characters from the right to drop from the prefix string.
    * @return
    * The resulting shortest common super-sequence.
    */
  def merge(prefix: String, suffix: String, slice: Int): String = {
    val leftSlice = prefix.dropRight(slice)
    leftSlice + suffix
  }

}
