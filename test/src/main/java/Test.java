public class Test {
    public static void main(String[] args) {
        String test = null;
        /*if (!("q".equalsIgnoreCase(test))){
            System.out.println("m");
        }*/
        TestObject obj = new TestObject();
        MemberObject memObj = new MemberObject();
        obj.setMember(memObj);
        memObj.setS("abc");
        // test = "abc";
        // 函数和C语言中一样，实参赋值给形参，函数栈结束后形参从内存中消失
        change(test);
        System.out.println(obj.getMember().getS());
        System.out.println(test);
    }

    public static void change(String s){
        s = "abc";
    }
}
