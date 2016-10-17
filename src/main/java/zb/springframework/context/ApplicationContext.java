package zb.springframework.context;

import zb.springframework.beans.factory.HierarchicalBeanFactory;
import zb.springframework.beans.factory.ListableBeanFactory;
import zb.springframework.core.io.support.ResourcePatternResolver;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, MessageSource,
		ApplicationEventPublisher, ResourcePatternResolver {

}
