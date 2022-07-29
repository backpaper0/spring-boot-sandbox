package com.example.mock.bean;

import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.example.mock.annotation.MyComponent;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

	private Environment environment;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 何もしない
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry) {
			@Override
			protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
				Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
				for (BeanDefinitionHolder holder : holders) {
					AbstractBeanDefinition definition = (AbstractBeanDefinition) holder.getBeanDefinition();
					String beanClassName = definition.getBeanClassName();
					// MyFactoryBeanのコンストラクター引数を設定する。
					// ※引数はClass<?>で定義しているのに、渡しているのはString。なぜこれで動作するのかはまだ追いかけていない
					definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
					// beanClassをFactoryBeanへ変更する。
					definition.setBeanClass(MyFactoryBean.class);
				}
				return holders;
			}

			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				return beanDefinition.getMetadata().isInterface()
						&& beanDefinition.getMetadata().isIndependent()
				// プロパティが設定されると具象コンポーネント(あるいはモック)が登録されるため、
				// プロパティが設定されていない場合を条件とする。
						&& environment.getProperty(beanDefinition.getBeanClassName()) == null;
			}
		};
		scanner.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));
		scanner.scan("com.example.mock");
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
