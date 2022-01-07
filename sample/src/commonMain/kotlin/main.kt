import com.github.androidpasswordstore.sublimefuzzy.Fuzzy

fun main() {
  val simpleMatchData = mapOf(
    ("test" to "tests") to true,
    ("test" to "tset") to false,
  )
  val matchData = mapOf(
    ("fot" to "FbxImporter.h") to (true to 105),
    ("fot" to "kot") to (false to 0),
  )
  simpleMatchData
    .forEach { (match, expected) ->
      val result = Fuzzy.fuzzyMatchSimple(match.first, match.second)
      require(result == expected) {
        "Simple match result for '${match.first}' and '${match.second}' was $result, should've been $expected"
      }
      println("[RESULT]: ${match.first} to ${match.second} = $result")
    }
  matchData
    .forEach { (match, expected) ->
      val (result, score) = Fuzzy.fuzzyMatch(match.first, match.second)
      require(result == expected.first && score == expected.second) {
        "Simple match result for '${match.first}' and '${match.second}' was ($result, $score), should've been (${expected.first}, ${expected.second})"
      }
      println("[RESULT]: ${match.first} to ${match.second} = $result, $score")
    }
}
