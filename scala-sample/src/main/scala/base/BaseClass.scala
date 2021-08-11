

package base

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import scala.annotation.meta.{getter, setter}
import scala.collection.mutable

/**
 * @param x
 * @param y
 */
@getter
@setter
class BaseClass(x: Int, y: Int) extends BaseExt with BaseExt2 with ExceptionExt {
  /**
   * 数据类型基本使用
   */
  def assignVal(): Unit = {
    /*
    * val常量, var变量
    */
    val byte: Byte = 127; //8位有符号补码整数。数值区间为 -128 到 127
    println(byte)
    val short: Short = Short.MaxValue; //16位有符号补码整数。数值区间为 -32768 到 32767
    println(short)

    var int: Int = Int.MaxValue; //	32位有符号补码整数。数值区间为 -2147483648 到 2147483647
    println(int)
    val long: Long = Long.MaxValue //	64位有符号补码整数。数值区间为 -9223372036854775808 到 9223372036854775807
    println(long)
    val float: Float = Float.MaxValue //	32 位, IEEE 754 标准的单精度浮点数
    println(float)
    val double: Double = Double.MaxValue //	64 位 IEEE 754 标准的双精度浮点数
    println(double)
    val char: Char = 'q'; //16位无符号Unicode字符, 区间值为 U+0000 到 U+FFFF
    println(char)
    val str: String = "hello" //字符序列
    println(str)
    val bool: Boolean = true //	true或false
    println(bool)
    val unit: Unit = (); //	表示无值，和其他语言中void等同。用作不返回任何结果的方法的结果类型。Unit只有一个实例值，写成()。
    println(unit)
    val null: Null = null //	null 或空引用
    // val nothing:Nothing	;//Nothing类型在Scala的类层级的最底端；它是任何其他类型的子类型。
    //Any	Any是所有其他类的超类
    //AnyRef	AnyRef类是Scala里所有引用类(reference class)的基类*/
  }

  /**
   * skip
   * 属性作用域
   * 运算符
   * if 表达式
   * while循环
   */

  /**
   * for循环使用
   */
  def forLoop(): Unit = {
    val a = 0;
    val b = 0;


    for (a <- 1 until (3)) {
      print(a + "\t")
    }

    // 多区间循环
    for (a <- 1 to 3; b <- 1 to 3) {
      println(a + "\t" + b);

    }
    println("yield using")
    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var ret = for {x <- numList} yield x;
    print(ret.toString())
  }

  /**
   * 方法与函数
   */
  val f1 = (x: Int) => x + 3;

  def f2(x: Int, y: Int): Int = {
    return Math.max(x, y);
  }

  /**
   * 数组声明
   */
  def assignArray(): Unit = {
    // 字符串数组
    val strArr: Array[String] = new Array[String](2);
    strArr(0) = "hello";
    strArr(1) = "word";
    println("strArr length:" + strArr.length)
    // val ie  =strArr.iterator;

  }

  /**
   * 集合处理
   */
  def handleSet(): Unit = {
    /**
     * list
     */
    println("hand list")
    val list: List[String] = List.empty;
    // TODO
    list.addString(StringBuilder.newBuilder.addAll("hello"))
    println("list size" + list.size)
    for (x <- list) {
      print(x + "\t")
    }
    println("---------------------------------------------------------")

    /**
     * set
     */
    println("hand set")
    val set: mutable.HashSet[String] = mutable.HashSet("1", "2", "3")
    set.add("4");
    println("set size:" + set.size)
    for (x <- set) {
      print(x + "\t")
    }
    println()
    println("---------------------------------------------------------")
    println("hand map")
    val map: mutable.HashMap[String, String] = mutable.HashMap()
    map.put("age", "24")
    map.put("name", "zhangsan")
    println(map.keySet.toString())
    println(map("age"))
    println("---------------------------------------------------------")
    println("hand tuple")
    val tuple1: Tuple1[String] = Tuple1("hello")
    println("tuple:" + tuple1._1)
  }

  /**
   * 增强型方法
   *
   * @return
   */
  override def advice(): Any = println(new Date())

  override def nowDate: String = {
    val fmt: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
    return fmt.format(new Date())
  }

  override def mockException(): Any = {
    throw  new IllegalArgumentException("非法参数异常")
  }

  /**
   * scala调用java
   * @param path
   * @return
   */
  def readFile(path:String):File = {
    new FileReader().readFile(path)
  }

}

/**
 * 类继承
 */
abstract class Human(val x: Int, val y: Int, val z: BaseClass) extends BaseClass(x, y) {
  val age: Int
  val name: String

  def invokeSuper: Unit = {
    z.handleSet()
  }
}

/**
 * 伴身对象
 */
object Human {

}

/**
 * 测试类
 */
object Test {
  def main(args: Array[String]): Unit = {
    val base = new BaseClass(1, 2);
    val human = new Human(1, 2, base) {
      override val age: Int = 24
      override val name: String = "zhangsan"
    };
    // human.handleSet()
    println("name:" + human.name)
    human.advice();
    val nowDate = human.nowDate();
    println(nowDate)
    println("---------invoke java-------------")
    val file= human.readFile("D:\\test");
    println("file name:"+file.getCanonicalFile)
    human.dividZero()
    human.mockException()

  }
}
