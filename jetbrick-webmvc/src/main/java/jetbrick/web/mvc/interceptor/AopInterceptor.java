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
package jetbrick.web.mvc.interceptor;

import jetbrick.web.mvc.RequestContext;

/**
 * 实现类似于 AOP 风格的 Interceptor.
 *
 * @author Guoqiang Chen
 */
public abstract class AopInterceptor implements Interceptor {

    @Override
    public void initialize() {
    }

    public abstract void before(RequestContext ctx);

    public abstract void after(RequestContext ctx);

    @Override
    public void intercept(RequestContext ctx, InterceptorChain chain) throws Exception {
        before(ctx);
        chain.invoke();
        after(ctx);
    }

    @Override
    public void destory() {
    }
}
