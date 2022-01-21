package com.github.androidpasswordstore.sublimefuzzy

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FuzzyTest {
  @Test
  fun fuzzy_match_simple_success() {
    val result = Fuzzy.fuzzyMatchSimple("test", "tests")
    assertTrue(result, "Each character in pattern should be found sequentially in str")
  }

  @Test
  fun fuzzy_match_simple_failure() {
    val result = Fuzzy.fuzzyMatchSimple("test", "tset")
    assertFalse(result, "Each character in pattern should be found sequentially in str")
  }

  @Test
  fun fuzzy_match_success() {
    val result = Fuzzy.fuzzyMatch("fot", "FbxImporter.h")
    assertTrue(result.first)
    assertEquals(105, result.second)
  }

  @Test
  fun fuzzy_match_failure() {
    val result = Fuzzy.fuzzyMatch("fot", "kot")
    assertFalse(result.first)
    assertEquals(0, result.second)
  }
}
