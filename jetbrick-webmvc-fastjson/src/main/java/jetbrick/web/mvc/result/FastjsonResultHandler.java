/**
 * Copyright 2013-2014 Guoqiang Chen, Shanghai, China. All rights reserved.
 *
 *   Author: Guoqiang Chen
 *    Email: subchen@gmail.com
 *   WebURL: https://github.com/subchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrick.web.mvc.result;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jetbrick.web.mvc.Managed;
import jetbrick.web.mvc.RequestContext;
import com.alibaba.fastjson.*;

@Managed({ JSONAware.class, JSONObject.class, JSONArray.class })
public final class FastjsonResultHandler implements ResultHandler<JSONAware> {

    @Override
    public void handle(RequestContext ctx, JSONAware result) throws IOException {
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        if (result == null) {
            JSONObject json = new JSONObject();
            Enumeration<String> e = request.getAttributeNames();

            while (e.hasMoreElements()) {
                String name = e.nextElement();
                json.put(name, request.getAttribute(name));
            }

            for (Map.Entry<String, Object> entry : ctx.getModel().entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }

            result = json;
        }

        String characterEncoding = request.getCharacterEncoding();
        response.setCharacterEncoding(characterEncoding);

        String mimetype = MimetypeUtils.getJSON(request);
        response.setContentType(mimetype + "; charset=" + characterEncoding);

        // jsonp callback
        String callback = ctx.getParameter("callback");

        PrintWriter out = response.getWriter();
        if (callback != null) {
            out.write(callback + '(' + result.toJSONString() + ')');
        } else {
            out.write(result.toJSONString());
        }
        out.flush();
    }
}
