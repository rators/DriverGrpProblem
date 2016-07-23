package utils

import utils.LCS.{LCSData, getLCS, StrResult, UnionRes}

trait Position
case object Prefix extends Position
case object Suffix extends Position

object Position extends App {
  /**
    * Get the overlap of information of two strings.
    * @param strOne
    *               The first string.
    * @param strTwo
    *               The second string.
    * @return
    *         Overlap information of two strings.
    */
  def getOverlap(strOne: String, strTwo: String): UnionRes = {
    //assume they are of the same length
    val areEqual = strOne == strTwo

    areEqual match {
      case true => LCSData(strOne.length, Prefix)
      case false =>
        val equalSize = strOne.length == strTwo.length
        equalSize match {
          case true => handleEqualSize(strOne, strTwo)
          case false => handleSizeDiff(strOne, strTwo)
        }
    }
  }

  /**
    * Condition for two strings with different lengths.
    * @param strOne
    *               The first string.
    * @param strTwo
    *               The second string.
    * @return
    *         The overlapping string information.
    */
  def handleSizeDiff(strOne: String, strTwo: String): UnionRes = {
    val bigStr = maxString(strOne, strTwo)
    val minStr = minString(strOne, strTwo)

    val isConsumed = bigStr.indexOf(minStr) >= 0

    isConsumed match {
      case true => StrResult(bigStr)
      case false =>
        val firstAsPrefix = getLCSDiff(strOne, strTwo, Prefix)
        val secondAsPrefix = getLCSDiff(strOne, strTwo, Suffix)
        (firstAsPrefix, secondAsPrefix) match {
          case (Some(firstStr), Some(secondStr)) =>
            val firstLarger = firstStr.length > secondStr.length
            firstLarger match {
              case true => LCSData(firstStr.length, Prefix)
              case false => LCSData(secondStr.length, Suffix)
            }
          case (Some(firstStr), None) => LCSData(firstStr.length, Prefix)
          case (None, Some(secondStr)) => LCSData(secondStr.length, Suffix)
        }
    }
  }

  /**
    * Get the shortest common substring of two strings with strTop as the prefix or suffix of the resulting string.
    * Use this function if the strings are of a different length.
    * @param strTop
    *               The top string.
    * @param strBottom
    *                  The bottom string.
    * @param topPosition
    *                    The position in the resulting string strTop will be at.
    * @return
    *         An optional of the longest common substring of strTop and strBottom.
    *
    */
  def getLCSDiff(strTop: String, strBottom: String, topPosition: utils.Position): Option[String] = {
    val rightIndices = strBottom.length to 1 by -1

    val topDrop = topPosition match {
      case Prefix => strTop.drop(_)
      case Suffix => strTop.dropRight(_)
    }

    val bottomDrop = topPosition match {
      case Prefix => strBottom.dropRight(_)
      case Suffix => strBottom.drop(_)
    }

    val sliceCheck = (ind: Int) => {
      val topSlice = topDrop(strTop.length - ind)
      val bottomSlice = bottomDrop(strBottom.length - ind)
      topSlice == bottomSlice
    }

    val sliceAt = (ind: Int) => {
      val topSlice = topDrop(strTop.length - ind)
      topSlice
    }

    findThenApply(rightIndices, sliceCheck, sliceAt)
  }

  def findThenApply[T, B](v: Iterable[T], pred: T => Boolean, apply: T => B): Option[B] ={
    val opt = v.find(pred)

    opt match {
      case Some(res) => Some(apply(res))
      case None => None
    }
  }

  def minString(strOne: String, strTwo: String): String = {
    if(strOne.length < strTwo.length) strOne else strTwo
  }

  def maxString(strOne: String, strTwo: String): String = {
    if(strOne.length > strTwo.length) strOne else strTwo
  }

  def handleEqualSize(strOne: String, strTwo: String): LCSData = {
    val oneAsPrefixStr: String = getLCS(strOne, strTwo)
    val twoAsPrefixStr: String = getLCS(strTwo, strOne)

    val oneIsPrefix = oneAsPrefixStr.length > twoAsPrefixStr.length

    val overlap = if (oneIsPrefix) oneAsPrefixStr else twoAsPrefixStr
    val overlapLength = overlap.length

    val direction = if (oneIsPrefix) Prefix else Suffix

    LCSData(overlapLength, direction)
  }

  println(getLCSDiff("ACGTA", "CACG", Suffix))
}