package mo.wall.org.kotlin

import java.util.*

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019-11-18 17:53
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class KtLearn {

    public fun test1() {
        var books = arrayOf("swift", "kotlin", "C", "C++")
        for (index in 0 until books.size) {
            println("${index + 1} : ${books[index]}")
        }
    }

    public fun test2() {
        var range = 6 downTo 0
    }

    public fun test3(): Date {
        return Date()
    }


//    operator fun test3(target: KtLearn): Date {
//        return Date()
//    }


    fun testWhen() {
        var score = 'b'
        when (score) {
            'b' -> {
                print("BBB")
            }
            else -> {
                print("error")
            }
        }

        when {
            1 === 1 -> {
                print("a")
            }
            else -> print("cc")
        }
    }

    fun testWhile() {
        while (1 > 0) {

        }
    }
}