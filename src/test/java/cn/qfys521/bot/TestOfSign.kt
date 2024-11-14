package cn.qfys521.bot


import cn.qfys521.util.RandomUtil
import kotlin.math.abs
import kotlin.random.Random
import org.junit.jupiter.api.Test

class TestOfSign {

    @Test
    fun testSign(){
        testByCount(1000, 0)
    }


    private fun testByCount(coin: Int, cishu: Int) {
        var count = cishu
        if (cishu == 0) count = 65536
        var cc = coin
        var ccc = coin
        var f = 0
        var t = 0
        for (i in 1..count) {
            if (cc <=0)
            {
                println("输光了")
                println("共计次数${i-1} , 正共计 $t 次 ， 负共计 $f 次")
                break
            }
            val b = RandomUtil.randomInt()%2==0
            if(b) t++ else f++
            val c = if (b) {
                abs (java.util.Random().nextInt ((2.00000000000001 * 2 * (0.35 * cc)*1.5).toInt()))
            } else {
                -abs (java.util.Random().nextInt ((2.00000000000001 * 2 * (0.35 * cc)*1).toInt()))
            }
            cc += c
            println("|原金币数: $ccc |次数: $i |是否为正: $b , |获得到的: $c |现金币数: $cc")
            ccc = cc
        }
    }

}