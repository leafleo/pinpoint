/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context;

import com.navercorp.pinpoint.bootstrap.context.AsyncTraceId;
import com.navercorp.pinpoint.bootstrap.context.Trace;
import com.navercorp.pinpoint.bootstrap.context.TraceId;
import com.navercorp.pinpoint.bootstrap.context.TraceType;

/**
 * @author Taejin Koo
 */
public class ActiveTraceFactory implements TraceFactory {

    private final TraceFactory traceFactory;
    private final ActiveTraceLifeCycleEventListener activeTraceEventListener;

    public ActiveTraceFactory(TraceFactory traceFactory, ActiveTraceLifeCycleEventListener activeTraceEventListener) {
        this.traceFactory = traceFactory;
        this.activeTraceEventListener = activeTraceEventListener;
    }

    @Override
    public Trace createDefaultTrace(long transactionId, TraceType traceType, boolean sampling) {
        Trace trace = traceFactory.createDefaultTrace(transactionId, traceType, sampling);
        return new ActiveTrace(trace, activeTraceEventListener);
    }

    @Override
    public Trace createDefaultTrace(TraceId continueTraceId, boolean sampling) {
        Trace trace = traceFactory.createDefaultTrace(continueTraceId, sampling);
        return new ActiveTrace(trace, activeTraceEventListener);
    }

    @Override
    public Trace createAsyncTrace(AsyncTraceId traceId, int asyncId, long startTime, boolean sampling) {
        Trace trace = traceFactory.createAsyncTrace(traceId, asyncId, startTime, sampling);
        return new ActiveTrace(trace, activeTraceEventListener);
    }

    @Override
    public Trace createMetricTrace() {
        Trace trace = traceFactory.createMetricTrace();
        return new ActiveTrace(trace, activeTraceEventListener);
    }

}