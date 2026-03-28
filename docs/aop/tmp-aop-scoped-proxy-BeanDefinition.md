# scoped-proxy-BeanDefinition


```text
public abstract class ScopedProxyUtils {

    private static final String TARGET_NAME_PREFIX = "scopedTarget.";
    
	public static String getTargetBeanName(String originalBeanName) {
		return TARGET_NAME_PREFIX + originalBeanName;
	}
	
	
	// 假设了caller会自己将proxyDefinition注册到registry 🎯
	public static BeanDefinitionHolder createScopedProxy(BeanDefinitionHolder definition, BeanDefinitionRegistry registry, boolean proxyTargetClass) {

		String originalBeanName = definition.getBeanName();
		BeanDefinition targetDefinition = definition.getBeanDefinition();
		String targetBeanName = getTargetBeanName(originalBeanName);                                                       // 注意这里增加的前缀"scopedTarget." 🎯

		// Create a scoped proxy definition for the original bean name,
		// "hiding" the target bean in an internal target definition.
		RootBeanDefinition proxyDefinition = new RootBeanDefinition(ScopedProxyFactoryBean.class);
		proxyDefinition.setDecoratedDefinition(new BeanDefinitionHolder(targetDefinition, targetBeanName));                // proxyDefinition还是持有targetDefinition的信息,知道proxy要decorate谁🎯🎯🎯
		proxyDefinition.setOriginatingBeanDefinition(targetDefinition);
		proxyDefinition.setSource(definition.getSource());
		proxyDefinition.setRole(targetDefinition.getRole());

		proxyDefinition.getPropertyValues().add("targetBeanName", targetBeanName);
		if (proxyTargetClass) {
			targetDefinition.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
			// ScopedProxyFactoryBean's "proxyTargetClass" default is TRUE, so we don't need to set it explicitly here.
		}
		else {
			proxyDefinition.getPropertyValues().add("proxyTargetClass", Boolean.FALSE);
		}

		// Copy autowire settings from original bean definition.
		proxyDefinition.setAutowireCandidate(targetDefinition.isAutowireCandidate());
		proxyDefinition.setPrimary(targetDefinition.isPrimary());
		if (targetDefinition instanceof AbstractBeanDefinition) {
			proxyDefinition.copyQualifiersFrom((AbstractBeanDefinition) targetDefinition);
		}

		// The target bean should be ignored in favor of the scoped proxy.
		targetDefinition.setAutowireCandidate(false); // 🎯🎯🎯 Set whether this bean is a candidate for getting autowired into some other bean.Note that this flag is designed to only affect type-based autowiring
		targetDefinition.setPrimary(false);  //🎯
		// Register the target bean as separate bean in the factory.
		registry.registerBeanDefinition(targetBeanName, targetDefinition);
		// 调用本方法的caller会将proxyDefinition以originalBeanName注册到registry,且是primary,从而达到替代的目的.
		// 而targetDefinition则以notPrimary/notAutowireCandidate不参于type-based autowiring,同时"scopedTarget."+originalBeanName的名字存在与registry,又达到了不参于name-based autowiring  🎯🎯🎯💯💯💯💯

		// Return the scoped proxy definition as primary bean definition
		return new BeanDefinitionHolder(proxyDefinition, originalBeanName, definition.getAliases());
	}    
}
```