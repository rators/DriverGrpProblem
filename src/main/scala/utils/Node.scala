package utils

import scala.collection.mutable

case class Node(var strand: String, var parent: Option[Node]){
  var starterEdge: Option[Edge] = None
  override def toString = s"$strand"
}

object Node {
  /**
    * Transforms an iterable of Nodes into a mutable buffer of edges.
    * For each node N an edge is created with N as its start value.
    * @param nodes
    *              The nodes associated with the graph.
    * @return
    *         The edges.
    */
  implicit def toEdges(nodes: Iterable[Node]): mutable.Buffer[Edge] = nodes.map((n) => {
    val e = Edge(n, None)
    n.starterEdge = Some(e)
    e
  }).toBuffer
}
