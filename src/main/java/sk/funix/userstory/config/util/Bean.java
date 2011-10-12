package sk.funix.userstory.config.util;


/**
 * @author http://community.jboss.org/wiki/AnnotationdrivenequalsandhashCode
 *
 */
public abstract class Bean {
    @Override
    public boolean equals(Object obj) {
        return BeanUtils.equals(this, obj);
    }
 
    @Override
    public int hashCode() {
        return BeanUtils.hashCode(this);
    }
 
    @Override
    public String toString() {
        return BeanUtils.toString(this);
    }
}
