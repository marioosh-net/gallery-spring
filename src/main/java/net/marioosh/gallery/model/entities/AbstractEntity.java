package net.marioosh.gallery.model.entities;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import org.springframework.beans.BeanUtils;

public abstract class AbstractEntity {
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		/*sb.append(" ID: " + this.getId() + "\n");
		sb.append(" version: " + this.getVersion() + "\n");*/
		for(PropertyDescriptor d: BeanUtils.getPropertyDescriptors(getClass())) {
			Method getter = d.getReadMethod();
			if(!Collection.class.isAssignableFrom(getter.getReturnType()) && !AbstractEntity.class.isAssignableFrom(getter.getReturnType())) {
				try {
					try {
						sb.append(" " + d.getName() + ": " + getter.invoke(this, null) + "\n");
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return super.toString() + " {\n" + sb.toString() + "}";
	}
}
