package com.br.VotaCoop.core.modelmapper;

import jakarta.persistence.Tuple;
import org.modelmapper.spi.ValueReader;

import java.util.Collections;
import java.util.Set;

class TupleValueReader implements ValueReader<Tuple> {

    @Override
    public Object get(Tuple source, String memberName) {
        return source.get(memberName);
    }

    @Override
    public Member<Tuple> getMember(Tuple source, String memberName) {
        return null;
    }

    public boolean accept(Class<?> type, String memberName) {
        return Tuple.class.isAssignableFrom(type);
    }

    @Override
    public Set<String> memberNames(Tuple source) {
        return Collections.emptySet();
    }
}
