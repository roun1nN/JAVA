import cn.hutool.core.date.DateUtil;

public class InvocationExceptionTest {

    public void throwExceptionTest() throws Exception {
        try {
            DateUtil.parse("asdsafsada");
        } catch (Exception e) {
            // 测试超类的fillInStackTrace()方法是否会自动为抛出的新异常补全异常堆栈信息
            throw new Exception("日期转换错误");
        }
    }
}
