package cn.javabook.chapter14.association;

public class Computer {
    private CPU cpu;
    private Monitor monitor;
    public Computer(CPU cpu, Monitor monitor) {
        this.cpu = cpu;
        this.monitor = monitor;
    }
}
