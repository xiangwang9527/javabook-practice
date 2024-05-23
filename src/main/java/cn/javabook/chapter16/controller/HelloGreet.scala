package cn.javabook.chapter16.controller

class HelloGreet {
  def greet(name: String, age: Int): Unit = {
    println(s"Hello, my name is $name, I am $age years old.")
  }
}
