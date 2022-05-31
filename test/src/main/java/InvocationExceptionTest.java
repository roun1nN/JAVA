import cn.hutool.core.date.DateUtil;

public class InvocationExceptionTest {

    public void throwExceptionTest() throws Exception {
        try {
            DateUtil.parse("asdsafsada");
        } catch (Exception e) {
            throw new Exception("日期转换错误");
        }
    }
}
