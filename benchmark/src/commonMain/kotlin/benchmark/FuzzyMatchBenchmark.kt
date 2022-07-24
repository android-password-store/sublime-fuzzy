package benchmark

import com.github.androidpasswordstore.sublimefuzzy.Fuzzy
import kotlin.random.Random
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.BenchmarkTimeUnit
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State

@State(Scope.Benchmark)
@Measurement(iterations = 3, time = 1, timeUnit = BenchmarkTimeUnit.SECONDS)
@OutputTimeUnit(BenchmarkTimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
class FuzzyMatchBenchmark {
  private val dataset = arrayListOf<String>()
  private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
  private val random = Random(852369741)

  @Setup
  fun setup() {
    dataset.clear()
    repeat(2000) { n -> dataset.add(getRandomString(n)) }
  }

  private fun getRandomString(length: Int): String {
    return (1..length).map { allowedChars.random(random) }.joinToString("")
  }

  @Benchmark
  fun fuzzyMatch() {
    dataset.forEach { item -> Fuzzy.fuzzyMatchSimple("asdf", item) }
  }
}
