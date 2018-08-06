package com.luckyaf.oneforall.kotlin.test

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/17
 *
 */

class LeanA {

    val a : Int = 1
    var b  = 2
    val s = "b is $b"

    val s2 = "${s.replace("is","was")}, but now is $b"

    val hello = "hello world"


    fun testHello():Int{
        println("hello world")
        return 1
    }



    fun sum(a:Int,b:Int):Int{
        return a + b
    }

    fun changeB(){
        b += 1
    }

    fun printS(){
        print(s)
        print(s2)
    }

}


