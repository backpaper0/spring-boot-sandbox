package study.beanname;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BeanNameExample2 {

	public interface Foobar {
		String name();
	}

	@Component("firstFoobar")
	public static class FirstFoobar implements Foobar {
		@Override
		public String name() {
			return "FirstFoobar";
		}
	}

	@Component("secondFoobar")
	public static class SecondFoobar implements Foobar {
		@Override
		public String name() {
			return "SecondFoobar";
		}
	}

	@Component
	@Qualifier("thisIdThirdFoobar")
	public static class ThirdFoobar implements Foobar {
		@Override
		public String name() {
			return "ThirdFoobar";
		}
	}

	@Component
	public static class FoobarContainer {

		//フィールド名をbean名に一致させる
		@Autowired
		private Foobar firstFoobar;
		@Autowired
		private Foobar secondFoobar;
		//Qualifierを使った例
		@Autowired
		@Qualifier("thisIdThirdFoobar")
		private Foobar thirdFoobar;

		public Foobar getFirstFoobar() {
			return firstFoobar;
		}

		public Foobar getSecondFoobar() {
			return secondFoobar;
		}

		public Foobar getThirdFoobar() {
			return thirdFoobar;
		}
	}
}
