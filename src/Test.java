public class Test extends Base {

    public static void main(String[] args) {

        int x=Integer.parseInt("123");
        System.out.println(x);

        Base b=new Test();
        b.method();

        Test t=new Test();
        t.method();

        Base base=new Base();
        base.method();
    }

    @Override
    public void method() {
        System.out.println("Test");
    }
}
