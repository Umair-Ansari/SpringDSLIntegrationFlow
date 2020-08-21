/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.wrappers;

import java.util.HashSet;
import java.util.Set;

/**
 * to be added in framework
 *
 * @author m.umair
 * @param <T>
 */
public class HashSetWrapper<T> extends HashSet<T> {

    private final Set<T> update = new HashSet<>();
    private final Set<T> add = new HashSet<>();

    private final Set<T> current = new HashSet<>();

    private Set<T> delete = new HashSet<>();

    public HashSetWrapper(Set<T> current) {
        super();
        this.delete = current;
    }

    @Override
    public boolean add(T t) {

        if (!this.contains(t)) {
            this.add.add(t);
        }
        this.current.add(t);
        return true;

    }

    public Set<T> getDelete() {
        return delete;
    }

    public Set<T> getUpdate() {
        return update;
    }

    public Set<T> getAdd() {
        return add;
    }

    public Set<T> getCurrent() {
        return current;
    }

    @Override
    public boolean contains(Object o) {
        for (Object p : this.delete) {
            if (o.equals(p)) {
                Configrurable other = (Configrurable) o;
                if (!other.isIdentical(p)) {
                    this.update.add((T) o);
                }
                this.delete.remove((T) p);
                return true;
            }
        }
        return false;
    }

}
