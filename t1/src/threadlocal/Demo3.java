package threadlocal;

public class Demo3 {

    public static void main(String[] args) {
        new Service1().service1();
    }
}

class Service1 {
    public void service1() {
        User user = new User("ross");
        UserContext.context.set(user);
        new Service2().service2();
    }
}

class Service2 {
    public void service2() {
        User user = UserContext.context.get();
        System.out.println("service2 get name: " + user.name);
        new Service3().service3();
    }
}

class Service3 {
    public void service3() {
        User user = UserContext.context.get();
        System.out.println("service3 get name: " + user.name);
        UserContext.context.remove();
    }
}

class UserContext {
    public static ThreadLocal<User> context = new ThreadLocal<>();
}

class User {
    String name;

    public User (String name) {
        this.name = name;
    }
}
