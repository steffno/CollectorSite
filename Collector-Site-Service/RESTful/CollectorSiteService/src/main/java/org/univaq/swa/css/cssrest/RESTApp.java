package org.univaq.swa.css.cssrest;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.univaq.swa.css.cssrest.jackson.ObjectMapperContextResolver;
import org.univaq.swa.css.cssrest.security.AppExceptionMapper;
import org.univaq.swa.css.cssrest.security.CORSFilter;
import org.univaq.swa.css.cssrest.security.AuthSystemResource;
import org.univaq.swa.css.cssrest.security.BearerAccessFilter;
import org.univaq.swa.css.cssrest.resources.AuthorsResource;
import org.univaq.swa.css.cssrest.resources.CollectionsResource;

/**
 *
 * @author didattica
 */
@ApplicationPath("rest")
public class RESTApp extends Application {

    private final Set<Class<?>> classes;

    public RESTApp() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(AuthSystemResource.class);
        c.add(BearerAccessFilter.class);
        c.add(AuthorsResource.class);
        c.add(CollectionsResource.class);
        c.add(JacksonJsonProvider.class);
        c.add(ObjectMapperContextResolver.class);
        c.add(CORSFilter.class);
        c.add(AppExceptionMapper.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
