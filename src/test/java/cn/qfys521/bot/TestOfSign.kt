package cn.qfys521.bot


import cn.qfys521.util.RandomUtil
import kotlin.random.Random
import org.junit.jupiter.api.Test

class TestOfSign {

    @Test
    fun testSign(){
        var coin = 100;
        var count = 0;
        while (count >= 0){
            if (count >= 35 )count =0;

            val tmp  = if (RandomUtil.randomBoolean()){
                RandomUtil.randomInt(2*(0.35*coin+0.01*count).toInt())
            }else{
                -RandomUtil.randomInt((2.00000000000001*(0.25*coin+0.01*count).toInt()).toInt())
            }
            coin+=tmp
            if (coin <= 0){
                coin+=100
                count = 0
                System.err.println("added 100 coin")
            }
            count ++

            println("coin:$coin count:$count tmp:$tmp")
        }
    println("coin is 0")
    }


}