package zb.springframework.core;

import org.springframework.core.AliasRegistry;

public class SimpleAliasRegistry implements AliasRegistry {

	@Override
	public void registerAlias(String name, String alias) {

	}

	@Override
	public void removeAlias(String alias) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAlias(String beanName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAliases(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
