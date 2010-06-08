package org.ssh.app.util;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

public class MyContentNegotiatingViewResolver extends ContentNegotiatingViewResolver {

    protected List<MediaType> getMediaTypes(HttpServletRequest request) {
            List<MediaType> result = super.getMediaTypes(request);
            if (result.size() == 1)
                    result = Arrays.asList(result.get(0));
            return result;
    }


}
