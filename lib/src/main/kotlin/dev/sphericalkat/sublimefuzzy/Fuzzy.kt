package dev.sphericalkat.sublimefuzzy

object Fuzzy {
    /**
     * Returns true if each character in pattern is found sequentially within str
     *
     * @param pattern the pattern to match
     * @param str the string to search
     */
    fun fuzzyMatchSimple(pattern: String, str: String): Boolean {
        var patternIdx = 0
        var strIdx = 0

        val patternLength = pattern.length
        val strLength = str.length

        while (patternIdx != patternLength && strIdx != strLength) {
            val patternChar = pattern.toCharArray()[patternIdx].toLowerCase()
            val strChar = str.toCharArray()[strIdx].toLowerCase()
            if (patternChar == strChar) ++patternIdx
            ++strIdx
        }

        return patternLength != 0 && strLength != 0 && patternIdx == patternLength
    }
}
