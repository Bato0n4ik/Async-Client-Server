package multithreading.lock;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private Integer money;
    ReentrantLock lock = new ReentrantLock();

    public Account(Integer money) {
        this.money = money;
    }

    public Integer getMoney() {
        return money;
    }

    public void add(Integer money) {
        this.money += money;
    }

    public boolean tackOff(Integer sendMoney) {
        if(this.money > sendMoney) {
            this.money -= sendMoney;
            return true;
        }
        return false;
    }
}
