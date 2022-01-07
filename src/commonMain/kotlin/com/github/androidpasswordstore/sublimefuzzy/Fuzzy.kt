package com.github.androidpasswordstore.sublimefuzzy

import kotlin.jvm.JvmStatic

public object Fuzzy {
  /**
   * Returns true if each character in pattern is found sequentially within str
   *
   * @param pattern the pattern to match
   * @param str the string to search
   * @return A boolean representing the match status
   */
  @JvmStatic
  public fun fuzzyMatchSimple(pattern: String, str: String): Boolean {
    var patternIdx = 0
    var strIdx = 0

    val patternLength = pattern.length
    val strLength = str.length

    while (patternIdx != patternLength && strIdx != strLength) {
      val patternChar = pattern.toCharArray()[patternIdx].lowercaseChar()
      val strChar = str.toCharArray()[strIdx].lowercaseChar()
      if (patternChar == strChar) ++patternIdx
      ++strIdx
    }

    return patternLength != 0 && strLength != 0 && patternIdx == patternLength
  }

  private fun fuzzyMatchRecursive(
    pattern: String,
    str: String,
    patternCurIndexOut: Int,
    strCurIndexOut: Int,
    srcMatches: MutableList<Int>,
    matches: MutableList<Int>,
    maxMatches: Int,
    nextMatchOut: Int,
    recursionCountOut: Int,
    recursionLimit: Int
  ): Pair<Boolean, Int> {
    var outScore = 0
    var strCurIndex = strCurIndexOut
    var patternCurIndex = patternCurIndexOut
    var nextMatch = nextMatchOut
    var recursionCount = recursionCountOut

    // return if recursion limit is reached
    if (++recursionCount >= recursionLimit) {
      return Pair(false, outScore)
    }

    // return if we reached end of strings
    if (patternCurIndex == pattern.length || strCurIndex == str.length) {
      return Pair(false, outScore)
    }

    // recursion params
    var recursiveMatch = false
    val bestRecursiveMatches = mutableListOf<Int>()
    var bestRecursiveScore = 0

    // loop through pattern and str looking for a match
    var firstMatch = true
    while (patternCurIndex < pattern.length && strCurIndex < str.length) {
      // match found
      if (pattern[patternCurIndex].equals(str[strCurIndex], ignoreCase = true)) {
        if (nextMatch >= maxMatches) {
          return Pair(false, outScore)
        }

        if (firstMatch && srcMatches.isNotEmpty()) {
          matches.clear()
          matches.addAll(srcMatches)
          firstMatch = false
        }

        val recursiveMatches = mutableListOf<Int>()
        val (matched, recursiveScore) =
          fuzzyMatchRecursive(
            pattern,
            str,
            patternCurIndex,
            strCurIndex + 1,
            matches,
            recursiveMatches,
            maxMatches,
            nextMatch,
            recursionCount,
            recursionLimit
          )

        if (matched) {
          // pick best recursive score
          if (!recursiveMatch || recursiveScore > bestRecursiveScore) {
            bestRecursiveMatches.clear()
            bestRecursiveMatches.addAll(recursiveMatches)
            bestRecursiveScore = recursiveScore
          }
          recursiveMatch = true
        }

        matches.add(nextMatch++, strCurIndex)
        ++patternCurIndex
      }
      ++strCurIndex
    }

    val matched = patternCurIndex == pattern.length

    if (matched) {
      outScore = 100

      // apply leading letter penalty
      val penalty =
        (Constants.LEADING_LETTER_PENALTY * matches[0]).coerceAtLeast(
          Constants.MAX_LEADING_LETTER_PENALTY
        )
      outScore += penalty

      // apply unmatched penalty
      val unmatched = str.length - nextMatch
      outScore += Constants.UNMATCHED_LETTER_PENALTY * unmatched

      // apply ordering bonuses
      for (i in 0 until nextMatch) {
        val currIdx = matches[i]

        if (i > 0) {
          val prevIdx = matches[i - 1]
          if (currIdx == prevIdx + 1) {
            outScore += Constants.SEQUENTIAL_BONUS
          }
        }

        // check for bonuses based on neighbour character value
        if (currIdx > 0) {
          // camelcase
          val neighbour = str[currIdx - 1]
          val curr = str[currIdx]
          if (neighbour != neighbour.uppercaseChar() && curr != curr.lowercaseChar()) {
            outScore += Constants.CAMEL_BONUS
          }
          val isNeighbourSeparator = neighbour == '_' || neighbour == ' '
          if (isNeighbourSeparator) {
            outScore += Constants.SEPARATOR_BONUS
          }
        } else {
          // first letter
          outScore += Constants.FIRST_LETTER_BONUS
        }
      }

      // return best result
      return if (recursiveMatch && (!matched || bestRecursiveScore > outScore)) {
        // recursive score is better than "this"
        matches.clear()
        matches.addAll(bestRecursiveMatches)
        outScore = bestRecursiveScore
        Pair(true, outScore)
      } else if (matched) {
        // "this" score is better than recursive
        Pair(true, outScore)
      } else {
        Pair(false, outScore)
      }
    }

    return Pair(false, outScore)
  }

  /**
   * Performs a fuzzy search to find pattern inside a string
   * @param pattern the the pattern to match
   * @param str the string to search
   * @return a [Pair] containing the match status as a [Boolean] and match score as an [Int]
   */
  @JvmStatic
  public fun fuzzyMatch(pattern: String, str: String): Pair<Boolean, Int> {
    val recursionCount = 0
    val recursionLimit = 10
    val matches = mutableListOf<Int>()
    val maxMatches = 256

    return fuzzyMatchRecursive(
      pattern,
      str,
      0,
      0,
      mutableListOf(),
      matches,
      maxMatches,
      0,
      recursionCount,
      recursionLimit
    )
  }
}
