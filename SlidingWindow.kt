package com.zee.kotlinplayground

import kotlin.math.max
import kotlin.math.min

fun main() {
    // find maximum sum subArray of size 3
    // [3,8,5,4,6,2,1,3,9]
    val inputs = intArrayOf(3, 8, 5, 4, 6, 2, 1, 3, 9)
    val windowSize = 3
    val result = findMaxSumSubArrayOfSizeKLengths(inputs, windowSize = windowSize)
    println("findMaxSumSubArrayLength $result of window size $windowSize")

    // smallest subArray with given sum
    val inputs2 = intArrayOf(1, 2, 2, 1, 2, 1, 0, 3, 2)
    val sum = 7
    val result2 = findSmallestSubArrayWithGivenSum(inputs2, sum = sum)
    println("findSmallestSubArray ${result2.toList()} of sum $sum")


    val input3 = "aaahhibc"
    // longest substring with k distinct characters
    val result3 = findLongestSubstringWithKDistinctCharacters(input3, k = 3)
    println("longestSubStringWithKDistinctChars result = $result3 of input $input3")
}

fun findLongestSubstringWithKDistinctCharacters(input: String, k: Int): String {
    var longestSubString = ""
    var startIndex = 0

    val map = HashMap<Char, Int>()
    /**
     * a,a,a,h,h,i,b,c
     * ans 3
     * */
    for (endIndex in input.indices) {
        val char = input[endIndex]
        map[char] = (map[char] ?: 0) + 1

        while (map.size > k) {
            val charAtStart = input[startIndex]
            val occurrence = (map[charAtStart]) ?: 0
            if (occurrence > 1) {
                map[charAtStart] = occurrence - 1
            } else {
                map.remove(charAtStart)
            }
            startIndex++
        }

        if (map.size == k) {
            val oldSize = longestSubString.length
            val newSize = endIndex - startIndex + 1
            if (oldSize < newSize) {
                longestSubString = input.substring(startIndex, endIndex + 1)
            }
        }
    }
    return longestSubString
}


fun findSmallestSubArraySizeWithGivenSum(input: IntArray, sum: Int): Int {
    var smallestArraySize = Int.MAX_VALUE
    var runningSum = 0
    var startIndex = 0
    var endIndex = 0

    while (endIndex < input.size) {
        runningSum += input[endIndex]

        while (runningSum > sum) {
            runningSum -= input[startIndex]
            startIndex++
        }

        if (sum == runningSum) {
            smallestArraySize = min(smallestArraySize, (endIndex - startIndex + 1))
        }

        if (smallestArraySize == 1) {
            break
        }
        endIndex++
    }
    return smallestArraySize
}

fun findSmallestSubArrayWithGivenSum(input: IntArray, sum: Int): IntArray {
    var smallestArray = input
    var runningSum = 0
    var startIndex = 0
    var endIndex = 0

    // (1, 2, 2, 1, 2, 1, 0, 3, 2)
    // e 2
    // s 2
    // sum 7
    while (endIndex < input.size) {
        runningSum += input[endIndex]

        while (runningSum > sum) {
            runningSum -= input[startIndex]
            startIndex++
        }

        if (sum == runningSum) {
            val oldSize = smallestArray.size
            val newSize = endIndex - startIndex + 1
            if (newSize < oldSize) {
                smallestArray = input.toList().subList(startIndex, endIndex + 1).toIntArray()
            }
        }

        if (smallestArray.size == 1) {
            break
        }
        endIndex++
    }
    return smallestArray
}

fun findMaxSumSubArrayOfSizeKLengths(input: IntArray, windowSize: Int): Int {

    var currentRunningSum = 0
    var maxSum = Int.MIN_VALUE

    for (index in input.indices) {
        val item = input[index]
        currentRunningSum += item

        if (index >= (windowSize - 1)) {
            maxSum = max(currentRunningSum, maxSum)
            currentRunningSum -= input[index - windowSize + 1]
        }
    }

    return maxSum
}