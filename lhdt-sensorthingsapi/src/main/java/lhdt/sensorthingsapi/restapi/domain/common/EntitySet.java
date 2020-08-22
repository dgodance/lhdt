package lhdt.sensorthingsapi.restapi.domain.common;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EntitySet<T extends Entity<T>> extends Collection<T>, NavigableElement<EntitySet<T>> {

    public List<T> asList();

    public long getCount();

    public void setCount(long count);

    public String getNextLink();

    public void setNextLink(String nextLink);

    @JsonIgnore
    public EntityType getEntityType();
}