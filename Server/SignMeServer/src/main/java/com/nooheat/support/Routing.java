package com.nooheat.support;

import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;

import io.vertx.ext.web.RoutingContext;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by NooHeat on 04/07/2017.
 */
public class Routing {

    private static List<DocumentResource> documentResources = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void route(Router router, String... packages) {

        // 패키지 별 등록
        for (String _package : packages) {

            Reflections reflections = new Reflections(_package);

            // URIMapping 어노테이션을 달고있는 클래스를 가져옴
            Set<Class<?>> URIMappingAnnotatedClass = reflections.getTypesAnnotatedWith(URIMapping.class);


            // 가져온 클래스를 각각 처리
            for (Class<?> c : URIMappingAnnotatedClass) {
                // 어노테이션 정보를 가져옴
                URIMapping uriMapping = c.getAnnotation(URIMapping.class);
                try {
                    router.route(uriMapping.method(), uriMapping.uri()).handler((Handler<RoutingContext>) c.newInstance());

                    if (c.isAnnotationPresent(API.class)) {
                        // 두 어노테이션이 모두 있는 경우
                        API api = c.getAnnotation(API.class);

                        documentResources.add(new DocumentResource(api.category().toString(), api.summary(), uriMapping.uri(), uriMapping.method().toString(), api.params(), api.requestBody(), api.response(), Integer.toString(api.successCode()), Integer.toString(api.failureCode()), api.etc()));
                    }
                    else {
                        documentResources.add(new DocumentResource("??",  "??", uriMapping.uri(), uriMapping.method().toString(), "", "", "","", "", ""));
                    }

                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.sort(documentResources);

        Document.makeDocument(documentResources);

    }
}
